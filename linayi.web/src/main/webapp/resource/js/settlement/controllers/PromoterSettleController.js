'use strict';

app.controller('promoterCtrl', function ($http, $scope, toaster, promoterService, messager, templateform) {
    var item_selected = new Array();

    function init() {
        debugger;
        $scope.list = list;
        $scope.show = show;
        list();
        $scope.search = {
            status: "",
            priceType: ""
        };
    }

    /**列表查询*/
    function list() {
        debugger;
        var $grid = $("#promoterList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search
            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url: urls.ms + "/finace/getAllPromoter.do",
            postData: $scope.search,
            pager: "#promoterPager",
            multiselect: true,
            colModel: [
                {name: 'promoterId', label: '推广商ID', sortable: false, hidden: true},
                {name: 'name', label: '推广商名称', sortable: false},
                {
                    name: 'promoterLevel',
                    label: '推广商等级',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        debugger;
                        switch (cellvalue) {
                            case "1":
                                return "一级";
                                break;
                            case "2":
                                return "二级";
                                break;
                            case "3":
                                return "三级";
                                break;
                        }
                    }
                },
                {name: 'ordersId', label: '订单ID', sortable: false, hidden: true},
                {name: 'sumOrderNo', label: '订单数', sortable: false},
                {name: 'sumOrderAmount', label: '订单总额', sortable: false},
                {name: 'promoterSettleId', label: '推广商收益ID', sortable: false, hidden: true},
                {name: 'profit', label: '推广商收益', sortable: false}

            ],
            //设置页面
            loadComplete: function () {
                debugger;
                var dataCorrectArr = new Array();
                var dataArr = $("#promoterList").jqGrid('getRowData');
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
                debugger;
                for (var index = 0; index < aRowids.length; index++) {
                    var str = $("#promoterList").jqGrid('getRowData', aRowids[index]).correctId;
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
                debugger;
                var str = $("#promoterList").jqGrid('getRowData', aRowids).correctId;
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

    init();
});
