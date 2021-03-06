'use strict';

app.controller('correctCtrl', function ($http, $scope, toaster, correctService, messager, templateform) {
    var item_selected = new Array();

    function init() {
        $scope.list = list;
        $scope.show = show;
        $scope.otherPrice = otherPrice;
        $scope.edit = edit;
        $scope.audit = audit;
        $scope.batchAudit = batchAudit;
        $scope.cancel = cancel;
        $scope.exportData = exportData;
        $scope.save = save;
        // list();
        $scope.search = {
            status: "",
            correctId:'',
            mobile:'',
            realName:'',
            name:'',
            fullName:'',
            createTimeStart:'',
            createTimeEnd:'',
            priceType: ""
        };
    }

    /**列表查询*/
    function list() {
        var $grid = $("#correctList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search
            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url: urls.ms + "/correct/correct/list.do",
            postData: $scope.search,
            pager: "#correctPager",
            multiselect: true,
            rownumWidth: 80,
            colModel: [
                {name: 'correctId', label: '主键', sortable: false, hidden: true},
                {name: 'realName', label: '真实姓名', sortable: false},
                {name: 'mobile', label: '手机号', width: 200, sortable: false},
                {
                    name: 'status', label: '状态',width: 100, sortable: false, formatter: function (cellvalue, options, rowObject) {
                        switch (cellvalue) {
                            case "WAIT_AUDIT":
                                return "待审核";
                                break;
                            case "AUDIT_SUCCESS":
                                return "审核通过";
                                break;
                            case "RECALL":
                                return "撤回";
                                break;
                            case "AUDIT_FAIL":
                                return "审核不通过";
                                break;
                            case "AFFECTED":
                                return "已生效";
                                break;
                            case "EXPIRED":
                                return "已过期";
                                break;
                        }
                    }
                },
                {name: 'userId', label: '用户id', sortable: false, hidden: true},
                {name: 'goodsSkuId', label: '商品id', sortable: false, hidden: true},
                {name: 'fullName', label: '商品', sortable: false},
                {name: 'barcode', label: '条形码', sortable: false,width: 240},
                {name: 'supermarketId', label: '超市id', sortable: false, hidden: true},
                {name: 'name', label: '超市', sortable: false},
                {
                    name: 'type',
                    label: '类型',
                    width: 100,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue == "SHARE" ? "分享" : "纠错";
                    }
                },
                {
                    name: 'price',
                    label: '分享价格(元)',
                    width: 160,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue / 100;
                    }
                },
                {
                    name: 'spreadRate',
                    label: '价差率',
                    width: 150,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return   cellvalue>100? "<a style='color: red;text-decoration:none;font-weight: bold;' href='javascript:void(0);' ng-click='otherPrice( " + rowObject.goodsSkuId + " )' class='btn-sm td-compile'>"+cellvalue +"%"+"</a>":"<a style='font-weight: bold;' href='javascript:void(0);' ng-click='otherPrice( " + rowObject.goodsSkuId + " )' class='btn-sm td-compile'>"+cellvalue+"%"+"</a>";
                    }
                },
                {
                    name: 'priceType',
                    label: '价格类型',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        switch (cellvalue) {
                            case "NORMAL":
                                return "正常价";
                                break;
                            case "PROMOTION":
                                return "促销价";
                                break;
                            case "DEAL":
                                return "处理价";
                                break;
                            case "MEMBER":
                                return "会员价";
                                break;
                        }
                    }
                },
                {
                    name: 'startTime',
                    label: '有效开始时间',
                    width: 166,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                {
                    name: 'endTime',
                    label: '有效结束时间',
                    width: 166,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                {
                    name: 'actualStartTime',
                    label: '实际开始时间',
                    sortable: false,
                    width: 166,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                {
                    name: 'actualEndTime',
                    label: '实际结束时间',
                    width: 166,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                {
                    name: 'createTime',
                    label: '创建时间',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                {
                    label: "操作",
                    name: "opt",
                    width: 260,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var opts = "";

                        opts = opts + "<a href='javascript:void(0);' ng-click='show( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-eye btn-sm td-compile'>详情</a> ";
                        opts = opts + "<a href='javascript:void(0);' ng-click='edit( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>编辑</a> ";
                        /*opts = opts + "<a href='javascript:void(0);' ng-click='cancel( "+rowObject.correctId+" )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>撤销</a> ";*/
                        var status = rowObject.status;
                        ;
                        if (status === "WAIT_AUDIT" || status === "AUDIT_SUCCESS") {
                            opts = opts + "<a href='javascript:void(0);' ng-click='cancel( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>撤销</a> ";
                        }
                        //待纠错提供审核按钮
                        if (status === "WAIT_AUDIT") {
                            opts = opts + "<a href='javascript:void(0);' ng-click='audit( " + rowObject._rowId + ',' + 70 + " )' class='btn btn-darkorange fa fa-edit btn-sm td-compile'>审核</a> ";
                        }
                        return opts;
                    }
                }
            ],
            //设置页面
            loadComplete: function () {
                var dataCorrectArr = new Array();
                var dataArr = $("#correctList").jqGrid('getRowData');
                for (var i = 0; i < dataArr.length; i++) {
                    dataCorrectArr.push(dataArr[i].correctId);
                }
                for (var j = 0; j < item_selected.length; j++) {
                    for (var i = 0; i < dataCorrectArr.length; i++) {
                        if (dataCorrectArr[i] == item_selected[j]) {
                            $(this).jqGrid('setSelection', i, false);
                        }

                    }
                }
            },
            onSelectAll: function (aRowids, status) {
                for (var index = 0; index < aRowids.length; index++) {
                    var str = $("#correctList").jqGrid('getRowData', aRowids[index]).correctId;
                    if (status) {
                        if (item_selected.toString().indexOf(str) < 0) {
                            item_selected.push(str);
                        }
                    } else {
                        for (var i = 0; i < item_selected.length; i++) {
                            if (item_selected[i] == str) {
                                item_selected.splice(i, 1);
                            }
                        }
                    }
                }
            },
            onSelectRow: function (aRowids, status) {
                var str = $("#correctList").jqGrid('getRowData', aRowids).correctId;
                if (status) {
                    item_selected.push(str);
                } else {
                    for (var i = 0; i < item_selected.length; i++) {
                        if (item_selected[i] == str) {
                            item_selected.splice(i, 1);
                        }
                    }
                }

            },
        });
    }

    /**编辑*/
    function edit(id) {
        var url = urls.ms + "/correct/correct/getInfo.do?correctId=" + id;
        templateform.open({
            title: "编辑",
            url: url,
            scope: $scope,
            onOpen: function ($modalInstance, data, $scope) {
            }
        }, function ($modalInstance, data, $scope) {
            save($modalInstance, $scope.correct, $scope);
        });


    }

    function cancel(correctId) {

        messager.confirm("确认撤销?", function (modalInstance) {
            $http({
                method: 'POST',
                url: urls.ms + "/correct/correct/recall.do",
                data: {
                    correctId: correctId,
                },
                headers: {'Content-Type': "application/x-www-form-urlencoded"},
                transformRequest: function (data) {
                    return $.param(data);
                }
            }).success(function (data) {
                list();
                modalInstance.close();
                if (data.search("成功")!=-1) {
                    toaster.success("", "操作成功", 3000);
                } else {
                    toaster.error("", "操作失败", 3000);
                }
            });
        });
    }


    /**审核*/
    function audit(rowId) {
        var rowData = $("#correctList").jqGrid("getRowData", rowId);
        /*var priceType = rowData.priceType;*/
        /*var status = rowData.status;*/
        /* var goodsName = rowData.name;
         var nickname = rowData.nickname;*/
        var correctId = rowData.correctId;
        var goodsSkuId = rowData.goodsSkuId;
        var supermarketId = rowData.supermarketId;
        var userId = rowData.userId;
        var startTime = rowData.startTime;
        var endTime = rowData.endTime;
        /*if(type == 2 && status != 50){
            toaster.info("纠错的商品无需审核！")
            return;
        }*/
        var url = urls.ms + "/jsp/goods/CorrectAudit.jsp";
        templateform.open({
            title: "分享审核",
            url: url,
            scope: $scope,
            buttons: [{
                text: "审核通过",
                iconCls: 'fa fa-check',
                handle: function ($modalInstance, data, $scope) {
                    $.ajax({
                        url: urls.ms + "/correct/correct/audit.do",
                        async: false,
                        type: "post",
                        data: {
                            "userId": userId,
                            "supermarketId": supermarketId,
                            "correctId": correctId,
                            "goodsSkuId": goodsSkuId,
                            "status": "AUDIT_SUCCESS",
                            "communityId": $scope.search.communityId
                        },
                        dataType: "JSON",
                        success: function (data) {
                            $("#correctList").trigger("reloadGrid");
                            $modalInstance.close();
                            toaster.success("", "操作成功", 3000);
                        }
                    })
                }
            }, {
                text: "不予通过",
                iconCls: 'fa fa-times',
                handle: function ($modalInstance, data, $scope) {
                    $.ajax({
                        url: urls.ms + "/correct/correct/audit.do",
                        async: false,
                        type: "post",
                        data: {
                            "userId": userId,
                            "supermarketId": supermarketId,
                            "correctId": correctId,
                            "status": "AUDIT_FAIL",
                            "goodsSkuId": goodsSkuId,
                            "communityId": $scope.search.communityId
                        },
                        dataType: "JSON",
                        success: function (data) {
                            $("#correctList").trigger("reloadGrid");
                            $modalInstance.close();
                            toaster.success("", "操作成功", 3000);
                        }
                    })
                }
            }],
            onOpen: function ($modalInstance, data, $scope) {
                //获取数据
                var fullName = rowData["fullName"];
                var realName = rowData["realName"];
                $scope.correct = {
                    fullName: fullName,
                    realName: realName,
                    user: rowData.user,
                    price: rowData.price + "元",
                    priceType: rowData.priceType,
                    startTime: startTime,
                    endTime: endTime
                }

            }
        }, function ($modalInstance, data, $scope) {
        });

    }

    /**保存*/
    function save($modalInstance, data, $scope) {
        try {
            $.ajax({
                url: urls.ms + "/correct/correct/save.do",
                type: "post",
                data: {
                    "correctId": $scope.correct.correctId,
                    "communityId": $scope.search.communityId,
                    "price": $scope.correct.price /** 100*/,
                    "priceType": $scope.correct.priceType,
                    "startTime": new Date($scope.correct.startTime).format("yyyy-MM-dd HH:mm"),
                    "endTime": new Date($scope.correct.endTime).format("yyyy-MM-dd HH:mm"),
                },
                dataType: "JSON",
                success: function (data) {
                    if (data.respCode === "S") {
                        $("#correctList").trigger("reloadGrid");
                        $modalInstance.close();
                        toaster.success("", "操作成功", 3000);
                    }
                }

            })
        } catch (e) {
            toaster.error("", typeof e == "string" ? //
                e : e.msg ? //
                    e.msg : "出错了", 3000);
        }
    }

    //查看
    function show(id) {
        var url = urls.ms + "/correct/correct/get.do?";
        if (id) {
            url = url + $.param({correctId: id});
        }
        templateform.open({
            title: "",
            url: url,
            scope: $scope,
            buttons: [],
            onOpen: function ($modalInstance, data, $scope) {

            }
        }, function (modalInstance, data) {
            ;
            $scope.correct = data.data;
        });
    }


    //查看其它价格
    function otherPrice(goodsSkuId) {
        var url = urls.ms + "/correct/correct/getOtherPrice.do?";
        if (goodsSkuId) {
            url = url + $.param({goodsSkuId: goodsSkuId});
        }
        templateform.open({
            title: "其它价格",
            url: url,
            scope: $scope,
            buttons: [],
            onOpen: function ($modalInstance, data, $scope) {

            }
        }, function (modalInstance, data) {
            $scope.correct = data.data;
        });
    }

    function batchAudit() {
        var correctIdList = [];
        for (var i = 0; i < item_selected.length; i++) {
            correctIdList[i] = parseInt(item_selected[i]);
        }

        var params = {correctIdList: correctIdList};
        $.ajax({
            url: urls.ms + "/correct/correct/batchAudit.do",
            data: params,
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data.respCode === "S") {
                    var page = $('#correctList').getGridParam('page')
                    toaster.success("", "操作成功", 3000);
                } else {
                    toaster.error("", "操作失败", 3000);
                }
                list();
                item_selected = new Array();
                $('#correctList').jqGrid('setGridParam', {
                    url: urls.ms + "/correct/correct/list.do",
                    postData: $scope.search,
                    datatype: 'json',
                    page: page,
                }).trigger("reloadGrid");
            }
        });
    }

    function exportData() {
        var realName = $scope.search.realName;
        var mobile = $scope.search.mobile;
        var status = $scope.search.status;
        var priceType = $scope.search.priceType;
        var name = $scope.search.name;
        var createTimeStart = $scope.search.createTimeStart;
        var createTimeEnd = $scope.search.createTimeEnd;
        var startTime = $scope.search.startTime;
        var endTime = $scope.search.endTime;
        var data = '';
        var sum = 0;
        if (realName === undefined || realName == '') {
            realName = null;
            sum++;
        } else {
            data += '&realName=' + realName;
        }
        if (priceType === undefined || priceType == '') {
            priceType = null;
            sum++;
        } else {
            data += '&priceType=' + priceType;
        }
        if (mobile === undefined || mobile == '') {
            mobile = null;
            sum++;
        } else {
            data += '&mobile=' + mobile;
        }
        if (status === undefined || status == '') {
            status = null;
            sum++;
        } else {
            data += '&status=' + status;
        }
        if (name === undefined || name == '') {
            name = null;
            sum++;

        } else {
            data += '&name=' + name;
        }
        if (createTimeStart === undefined || createTimeStart == '') {
            createTimeStart = null;
            sum++;
        } else {
            data += '&createTimeStart=' + createTimeStart;
        }
        if (createTimeEnd === undefined || createTimeEnd == '') {
            createTimeEnd = null;
            sum++;
        } else {
            data += '&createTimeEnd=' + createTimeEnd;
        }
        if (startTime === undefined || startTime == '') {
            startTime = null;
            sum++;
        }
        if (endTime === undefined || endTime == '') {
            endTime = null;
            sum++;
        }

        if (sum == 9) {
            toaster.error("", "请输入搜索条件后导出!", 3000);
            return;
        }
        if (!(startTime === null || startTime == '')) {
            startTime = new Date($scope.search.startTime).format("yyyy-MM-dd") + " 00:00:00";
            data += '&startTime=' + startTime;
        }
        if (!(endTime === null || endTime == '')) {
            endTime = new Date($scope.search.endTime).format("yyyy-MM-dd") + " 00:00:00";
            data += '&endTime=' + endTime;
        }
        window.location.href = urls.ms + "/correct/correct/exportShareRecord.do?" + data.replace("&", "");
        toaster.success("", "导出成功!", 1000);

    }

    init();
});