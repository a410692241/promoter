'use strict';

app.controller('correctCtrl', function ($http, $scope, toaster, correctService, messager, templateform) {
    var item_selected = new Array();

    function init() {
        $scope.list = list;
        $scope.show = show;
        $scope.edit = edit;
        $scope.audit = audit;
        $scope.batchAudit = batchAudit;
        $scope.cancel = cancel;
        $scope.save = save;
        list();
        $scope.search = {
            status: "",
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
            colModel: [
                {name: 'correctId', label: '主键', sortable: false, hidden: true},
                {name: 'user.realName', label: '真实姓名', sortable: false},
                {name: 'user.mobile', label: '手机号', sortable: false},
                {
                    name: 'status', label: '状态', sortable: false, formatter: function (cellvalue, options, rowObject) {
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
                {name: 'goodsSku.name', label: '商品', sortable: false},
                {name: 'supermarketId', label: '超市id', sortable: false, hidden: true},
                {name: 'supermarket.name', label: '超市', sortable: false},
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
                    label: '价格(元)',
                    width: 100,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue / 100;
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
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                    }
                },
                {
                    name: 'endTime',
                    label: '有效结束时间',
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
                    width: 300,
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
            loadComplete : function() {
                var dataCorrectArr = new Array();
                var dataArr = $("#correctList").jqGrid('getRowData');
                for (var i = 0; i < dataArr.length; i++) {
                    dataCorrectArr.push(dataArr[i].correctId);
                }
                for (var j = 0; j < item_selected.length; j++) {
                    for (var i = 0; i < dataCorrectArr.length; i++) {
                        if (dataCorrectArr[i] == item_selected[j]) {
                            $(this).jqGrid('setSelection',i,false);
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
                if (data.success) {
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
        /*var status = rowData.status;
        var goodsName = rowData.goodsSku.name;
        var nickname = rowData.user.nickname;*/
        var correctId = rowData.correctId;
        var goodsSkuId = rowData.goodsSkuId;
        var supermarketId = rowData.supermarketId;
        var userId = rowData.userId;
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
                var goodsSku_name = rowData["goodsSku.name"];
                var user_nickname = rowData["user.nickname"];
                $scope.correct = {
                    goodsSku_name: goodsSku_name,
                    user_nickname: user_nickname,
                    user: rowData.user,
                    price: rowData.price + "元",
                    priceType: rowData.priceType,
                    startTime: rowData.startTime,
                    endTime: rowData.endTime
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
                    "startTime": new Date($scope.correct.startTime).format("yyyy-MM-dd") + " 00:00:00",
                    "endTime": new Date($scope.correct.endTime).format("yyyy-MM-dd") + " 00:00:00",
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

    function batchAudit() {
        var correctIdList = [];
        for (var i = 0; i <item_selected.length ; i++) {
            correctIdList[i] = parseInt(item_selected[i]);
        }

        var params = {correctIdList: correctIdList};
        $.ajax({
            url:urls.ms + "/correct/correct/batchAudit.do",
            data:params,
            dataType : 'json',
            type: "POST",
            success:function(data){
                if (data.respCode==="S"){
                    var page = $('#correctList').getGridParam('page')
                    toaster.success("", "操作成功", 3000);
                }else{
                    toaster.error("", "操作失败", 3000);
                }
                list();
                item_selected = new Array();
                $('#correctList').jqGrid('setGridParam',{
                    url: urls.ms + "/correct/correct/list.do",
                    postData: $scope.search,
                    datatype:'json',
                    page:page,
                }).trigger("reloadGrid");
            }
        });
    }

    init();
});