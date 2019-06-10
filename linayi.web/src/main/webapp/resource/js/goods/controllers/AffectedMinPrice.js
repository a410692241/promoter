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
         list();
        $scope.search = {
            goodsSkuId:'',
            fullName:'',
            name:'',
            createTime:'',
            actualStartTime:'',
            createTimeStart:'',
            createTimeEnd:''
        };
    }

    /**列表查询*/
    function list() {
        var $grid = $("#affectedMinPirceList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search
            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url: urls.ms + "/correct/correct/getaffectedminprice.do",
            postData: $scope.search,
            pager: "#affectedMinPircePager",
            multiselect: true,
            rownumWidth: 100,
            colModel: [
                {name: 'goodsSkuId', label: '商品ID', sortable: false, hidden: false},
                {name: 'fullName', label: '商品名称', sortable: false},
                {name: 'name', label: '超市名称', sortable: false, hidden: false},
                {name: 'price', label: '最低价(元)', sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue / 100;
                    },},
                {
                    name: 'spreadRate',
                    label: '价差率(%)',
                    width: 150,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return   cellvalue>100? "<a style='color: red;text-decoration:none;font-weight: bold;' href='javascript:void(0);' ng-click='otherPrice( " + rowObject.goodsSkuId + " )' class='btn-sm td-compile'>"+cellvalue +"%"+"</a>":"<a style='font-weight: bold;' href='javascript:void(0);' ng-click='otherPrice( " + rowObject.goodsSkuId + " )' class='btn-sm td-compile'>"+cellvalue+"%"+"</a>";
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
                    name: 'createTime',
                    label: '创建时间',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                // {
                //     label: "操作",
                //     name: "opt",
                //     width: 260,
                //     sortable: false,
                //     formatter: function (cellvalue, options, rowObject) {
                //         var opts = "";
                //
                //         opts = opts + "<a href='javascript:void(0);' ng-click='edit( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>改价</a> ";
                //         /*opts = opts + "<a href='javascript:void(0);' ng-click='cancel( "+rowObject.correctId+" )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>撤销</a> ";*/
                //
                //         return opts;
                //     }
                // }
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
                    toaster.success("", data.msg, 3000);
                } else {
                    toaster.error("", data.msg, 3000);
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
        var url = urls.ms + "/correct/correct/getSupermarketPrice.do?";
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
        var goodsSkuId = $scope.search.goodsSkuId;
        var fullName = $scope.search.fullName;
        var name = $scope.search.name;
        var createTime = $scope.search.createTime;
        var actualStartTime = $scope.search.actualStartTime;
        var createTimeStart = $scope.search.createTimeStart;
        var createTimeEnd = $scope.search.createTimeEnd;
        var data = '';

        if (goodsSkuId === undefined || goodsSkuId == '') {
            goodsSkuId = null;

        } else {
            data += '&goodsSkuId=' + goodsSkuId;
        }
        if (fullName === undefined || fullName == '') {
            fullName = null;

        } else {
            data += '&fullName=' + fullName;
        }
        if (name === undefined || name == '') {
            name = null;

        } else {
            data += '&name=' + name;
        }
        if (createTimeStart === undefined || createTimeStart == '') {
            createTimeStart = null;

        } else {
            data += '&createTimeStart=' + createTimeStart;
        }
        if (createTimeEnd === undefined || createTimeEnd == '') {
            createTimeEnd = null;

        } else {
            data += '&createTimeEnd=' + createTimeEnd;
        }

        // if (!(createTime === null || createTime == '')) {
        //     createTime = new Date($scope.search.createTime).format("yyyy-MM-dd") + " 00:00:00";
        //     data += '&createTime=' + createTime;
        // }
        // if (!(actualStartTime === null || actualStartTime == '')) {
        //     actualStartTime = new Date($scope.search.actualStartTime).format("yyyy-MM-dd") + " 00:00:00";
        //     data += '&actualStartTime=' + actualStartTime;
        // }

        window.location.href = urls.ms + "/goods/goods/exportAffectedPriceData.do?" + data.replace("&", "");
        toaster.success("", "导出成功!", 1000);

    }

    init();
});