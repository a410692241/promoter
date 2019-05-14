'use strict';

app.controller('orderCtrl', function ($scope, toaster, orderService, messager, templateform, $timeout) {

    function init() {
        // $scope.show = show;
        // $scope.edit = edit;
        // $scope.remove = remove;
        $scope.list = list;
        $scope.editSelfOrder = editSelfOrder;
        $scope.showSelfOrderMessage = showSelfOrderMessage;
        $scope.sharePrice = sharePrice;
        $scope.turnToOrder = turnToOrder;
        $scope.contactUser = contactUser;
        $scope.search = {
            communityId: "",
            ordersId: "",
            mobile: "",
            userStatus: "",
            communityStatus: "",
            createTimeStart: "",
            createTimeEnd: ""
        };
        $scope.list();
    }

    function priceAndNumFormatter(cellvalue, options, rowObject) {
        switch (cellvalue) {
            case (-1):
                return '待确认';
                break;
            default:
                return (cellvalue / 100).toFixed(2);
                break;
        }
    }

    function reserveTwoAfterPoint(value, options, rowObject) {
        if (value === -1) {
            return "暂无价格";
        }
        value = Math.round(parseFloat(value)) / 100;
        var xsd = value.toString().split(".");
        if (xsd.length == 1) {
            value = value.toString() + ".00";
            return value;
        }
        if (xsd.length > 1) {
            if (xsd[1].length < 2) {
                value = value.toString() + "0";
            }
            return value;
        }
    }

    /** 列表查询 */
    function list() {
        var $grid = $("#selfOrderList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search,

            }).trigger("reloadGrid");
            return;
        }

        $grid.jqGrid({
            url: urls.ms + "/selfOrder/selfOrderlist.do",
            postData: $scope.search,
            pager: "#selfOrderPager",
            colModel: [
                {name: "userId", label: "用户id", hidden: true, sortable: false},
                {name: "sharers", label: "分享", hidden: true, sortable: false},
                {name: 'selfOrderId', label: '编号', sortable: false},
                {name: 'goodsName', label: '商品名称', sortable: false},
                {name: 'brandName', label: '品牌名称', sortable: false},
                {name: 'attrValue', label: '商品属性值', sortable: false},
                {name: 'maxPrice', label: '最高价', sortable: false, formatter: priceAndNumFormatter},
                {name: 'minPrice', label: '最低价', sortable: false, formatter: priceAndNumFormatter},
                {name: 'num', label: '采购数量', sortable: false, formatter: priceAndNumFormatter},
                {
                    name: 'status', label: '询价状态', sortable: false, formatter: function (value, row, rowObject) {
                        switch (value) {
                            case 'WAIT_DEAL':
                                return "等待处理";
                                break;
                            case 'PROCESSING':
                                return '采价中';
                            case 'SUCCESS':
                                return '处理成功';
                                break;
                            case 'FAIL':
                                return '处理失败';
                                break;
                            default:
                                break;
                        }
                    }
                },
                {
                    name: 'isOrderSuccess', label: '转订单', sortable: false, formatter: function (value, row, rowObject) {
                        switch (value) {
                            case 'WAIT_DEAL':
                                return '待处理';
                                break;
                            case 'SUCCESS':
                                return '下订单成功';
                                break;
                            case 'FAIL':
                                return '下订单失败';
                                break;
                            default:
                                break;
                        }
                    }
                },
                {
                    name: 'createTime',
                    label: '创建时间',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd hh:mm:ss") : "";
                    }
                },
                {
                    name: 'updateTime',
                    label: '修改时间',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd hh:mm:ss") : "";
                    }
                },
                {
                    label: "操作",
                    name: "opt",
                    width: 170,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var opts = "";
                        opts = opts + "<a href='javascript:void(0);' ng-click='editSelfOrder(" + rowObject.selfOrderId + ")' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
                        opts = opts + "<a href='javascript:void(0);' ng-click='contactUser(" + rowObject.userId + ")' class='btn btn-primary fa fa-edit btn-sm td-compile'>联系用户</a> ";
                        opts = opts + "<a href='javascript:void(0);' ng-click='showSelfOrderMessage(" + rowObject.selfOrderId + ")' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看采价</a> ";
                        if (rowObject.status == "WAIT_DEAL") {
                            var param_string = angular.toJson({
                                selfOrderId: rowObject.selfOrderId,
                                goodsName: rowObject.goodsName,
                                brandName: rowObject.brandName,
                                attrValue: rowObject.attrValue,
                                userId: rowObject.userId
                            });
                            opts = opts + "<a href='javascript:void(0);' ng-click='sharePrice(" + param_string + ")' class='btn btn-primary fa fa-share btn-sm td-compile'>通知分享员</a> ";
                        }
                        return opts;
                    }
                }
            ],
            subGrid: true,
            subGridRowExpanded: showChildGrid,
            subGridOptions: {
                reloadOnExpand: false,
                selectOnExpand: true
            }
        });
    }

    function editSelfOrder(selfOrderId) {
        var url = urls.ms + "/selfOrder/edit.do?";
        if (selfOrderId) {
            url = url + $.param({selfOrderId: selfOrderId});
        }
        templateform.open({
            title: "自定义订单编辑",
            url: url,
            scope: $scope,
            onOpen: function ($modalInstance, data, $scope) {

            }
        }, function ($modalInstance, data, $scope) {
            saveSelfOrder($modalInstance, data, $scope);
        });
    };

    function saveSelfOrder($modalInstance, data, $scope) {
        try {
            data = JSON.parse(JSON.stringify($scope.selfOrder));
            data.minPrice = Math.round(100 * data.minPrice);
            data.maxPrice = Math.round(100 * data.maxPrice);
            orderService.saveSelfOrder({
                data: data,
                success: function (data) {
                    if (data.respCode == "S") {
                        $("#selfOrderList").trigger("reloadGrid");
                        $modalInstance.close();
                    } else {
                        $scope.$apply(function () {
                            toaster.error("", data.errorType, 1000);
                        });
                    }
                }
            })
        } catch (e) {
            console.error(e);
            toaster.error("", typeof e == "string" ? //
                e : e.msg ? //
                    e.msg : "出错了", 3000);
        }
    };

    function showSelfOrderMessage(selfOrderId) {
        var url = urls.ms + "/selfOrder/shareTaskView.do?";
        if (selfOrderId) {
            url = url + $.param({selfOrderId: selfOrderId});
        }
        templateform.open({
            title: "查看采价",
            url: url,
            scope: $scope,
            onOpen: function ($modalInstance, data, $scope) {

            }
        }, function ($modalInstance, data, $scope) {

        });
    };

    function sharePrice(param_string) {
        var params = param_string;
        console.log(params);
        orderService.share({
            data: params,
            success: function (data) {
                if (data.respCode == "S") {
                    $("#selfOrderList").trigger("reloadGrid");
                    // $modalInstance.close();
                } else {
                    $scope.$apply(function () {
                        toaster.error("", data.errorType, 1000);
                    });
                }
            }
        })
    };

    function turnToOrder(params) {
        params.num = parseInt(params.num);
        if (isNaN(params.num)){
            alert("请向客户确认数量后下单!")
            throw BusinessException("请向客户确认数量后下单!");
        }
        var confirm_string = "您要购买的商品是: " + params.fullName + "\n";
        confirm_string += "最低价：" + reserveTwoAfterPoint(params.minPrice) + "\n";

        var amount = params.minPrice * params.num;
        confirm_string += "总价: " + reserveTwoAfterPoint(amount) + "\n";
        var saveAmount = (params.maxPrice - params.minPrice) * params.num;
        confirm_string += "比价优惠: " + reserveTwoAfterPoint(saveAmount) + "\n";
        confirm_string += "服务费：" + "10" + "\n";
        confirm_string += "额外服务费: " + "0";
        if (window.confirm(confirm_string)) {
            orderService.turnToOrder({
                data: {
                    selfOrderId: parseInt(params.selfOrderId),
                    userId: parseInt(params.userId),
                    goodsSkuId: parseInt(params.goodsSkuId),
                    num: params.num,
                    amount: amount,
                    saveAmount: saveAmount,
                    serviceFee: 1000,
                    extraFee: 0
                },
                success: function (data) {
                    if (data.respCode == "S"){
                        $("#selfOrderList").trigger("reloadGrid");
                    }else {
                        $scope.$apply(function () {
                            toaster.error("", data.errorType, 1000);
                        });
                    }
                }
            })
        }
    };

    function showChildGrid(parentRowId, parentRowKey) {
        var selfOrder = $(this).jqGrid("getRowData", parentRowKey);
        var childGridId = parentRowId + "_table";
        var childGridPagerId = parentRowId + "_pager";
        $('#' + parentRowId).append('<table id=' + childGridId + '></table><div id=' + childGridPagerId + ' class=scroll></div>');
        var $skuList = $(this).find("#" + childGridId);
        $scope.goodsListSearch = {
            brandName: selfOrder.brandName,
            fullName: selfOrder.goodsName,
            valueName: selfOrder.attrValue,
            userId: selfOrder.userId
        };
        $skuList.jqGrid({
            url: urls.ms + "/selfOrder/searchGoods.do",
            async: false,
            postData: $scope.goodsListSearch,
            pager: "#" + childGridPagerId,
            colModel: [
                {name: "goodsSkuId", label: "sku-id", hidden: true, sortable: false},
                {name: "fullName", label: "商品全称", sortable: false},
                {name: 'minSupermarket', label: '最低价超市', sortable: false},
                {name: 'minPrice', label: '最低价', sortable: false, formatter: reserveTwoAfterPoint},
                {name: 'maxPrice', label: '最高价', sortable: false, formatter: reserveTwoAfterPoint},
                {
                    name: 'spreadRate',
                    label: '价差率',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return (Math.round(cellvalue * 10000) / 100.00 + "%");
                    }
                },
                {
                    name: "opts",
                    label: "操作",
                    width: 170,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        if (selfOrder.isOrderSuccess === '待处理'){
                            var orderParams = {
                                selfOrderId: selfOrder.selfOrderId,
                                userId: selfOrder.userId,
                                goodsSkuId: rowObject.goodsSkuId,
                                num: selfOrder.num,
                                minPrice: rowObject.minPrice,
                                maxPrice: rowObject.maxPrice,
                                serviceFee: 10,
                                extraFee: 0,
                                fullName: rowObject.fullName
                            };
                            return "<a href='javascript:void(0);' ng-click='turnToOrder(" + angular.toJson(orderParams) + ")' class='btn btn-primary fa fa-check btn-sm td-compile'>下订单</a>";
                        }
                        else {
                            return ""
                        }
                    }
                }
            ],
            loadonce: true,
            width: 500,
            rownumbers: false,
            height: '100%',
            subGrid: true,
            subGridRowExpanded: showChildPriceGrid,
            subGridOptions: {
                reloadOnExpand: false,
                selectOnExpand: true
            }
        });
    }

    function showChildPriceGrid(parentRowId, parentRowKey) {
        var skuData = $(this).jqGrid("getRowData", parentRowKey);
        var childGridId = parentRowId + "_sub_table";
        $('#' + parentRowId).append("<table id=" + childGridId + "></table>");
        var $supermarketPriceList = $(this).find("#" + childGridId);
        $supermarketPriceList.jqGrid({
            url: urls.ms + "/selfOrder/searchSupermarketPrice.do",
            async: false,
            postData: {
                userId: parseInt($scope.goodsListSearch.userId),
                goodsSkuId: parseInt(skuData.goodsSkuId)
            },
            colModel: [
                {name: 'supermarketName', label: '超市名', sortable: false},
                {name: 'price', label: '价格', sortable: false, formatter: reserveTwoAfterPoint}
            ],
            loadonce: true,
            width: 300,
            rownumbers: false,
            height: '100%'
        })
    }

    function contactUser(userId) {
        var url = urls.ms + "/selfOrder/contactUser.do?";
        if (userId) {
            url = url + $.param({userId: userId});
        }
        templateform.open({
            title: "联系用户",
            url: url,
            scope: $scope,
            onOpen: function ($modalInstance, data, $scope) {

            }
        }, function ($modalInstance, data, $scope) {

        });
    }

    // 初始化
    init();
});