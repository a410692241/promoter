'use strict';

app.controller('promoterCtrl', function ($http, $scope, toaster, promoterService, messager, templateform) {
    var item_selected = new Array();

    function init() {
        debugger;
        $scope.list = list;
        $scope.show = show;
        $scope.exportData = exportData;
        list();
        $scope.search = {
            status: "",
            priceType: ""
        };
    }

    /**列表查询*/
    function list() {
        if ($scope.search.createTimeEnd != "") {
            //结束时间不能大于当前时间
            var date = new Date();
            var createTimeEnd = convertDateFromString($scope.search.createTimeEnd);
            if(date.getTime() < createTimeEnd.getTime()){
                toaster.error("", "结束时间必须大于当前时间!", 3000);
                return;
            }

            var year1 = createTimeEnd.getFullYear();//年
            var month1 = createTimeEnd.getMonth();//月
            //时间不能和开始时间不是同一个月份
            if($scope.search.createTimeStart != ""){
                var createTimeStart = convertDateFromString($scope.search.createTimeStart);
                var year2 = createTimeStart.getFullYear();//年
                var month2 = createTimeStart.getMonth();//月
                if(createTimeStart.getTime() > createTimeEnd.getTime()){
                    toaster.error("", "开始时间必须小于结束时间!", 3000);
                    return;
                }

                if(!(year1 == year2 && month1 == month2)){
                    toaster.error("", "查询时间必须是同一月份的!", 3000);
                    return;
                }
            }

        }
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

    //导出excel
    function exportData() {
        var createTimeStart = $scope.search.createTimeStart;
        var createTimeEnd = $scope.search.createTimeEnd;
        var data = '';
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

        location.href = urls.ms + "/finace/exportData.do?" + data.replace("&", "");
        toaster.success("", "导出成功!", 1000);
    }

    function convertDateFromString(dateString) {
        if (dateString) {
            var arr1 = dateString.split(" ");
            var sdate = arr1[0].split('-');
            var date = new Date(sdate[0], sdate[1]-1, sdate[2]);
            return date;
        }
    }


    init();
});
