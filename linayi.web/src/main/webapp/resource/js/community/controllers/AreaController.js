'use strict';

app.controller('areaCtrl', function ($scope, toaster, areaService, messager, templateform) {

    function init() {
        $scope.list = list;
        $scope.showCommunity = showCommunity;
        $scope.list();
        $scope.search = {
            areaId: '',
            fullName: '',
            streetName: '',
            communityName: '',
            createTime: ''
        }
    }

    /**列表查询*/
    function list() {
        var $grid = $("#areaList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search,
            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url: urls.ms + "/area/list.do",
            postData: $scope.search,
            pager: "#areaPager",
            colModel: [
                {name: 'code', label: '主键', hidden: true},
                {name: 'streetName', label: '街道'},
                {name: 'fullName', label: '省市区', sortable: false},
                {
                    name: 'communityName',
                    label: '网点',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == null || cellvalue == '') {
                            return "<span style='color:red;border: 1px solid red;padding-left: 10px;padding-right: 10px'>暂未绑定</span>";
                        } else {
                            return "<span class='btn btn-blue btn-sm td-compile'>"+cellvalue+"</span>";;
                        }
                    }
                },
                {
                    label: "操作",
                    name: "opt",
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var opts = "";
                        opts = opts + "<a href='javascript:void(0);' ng-click='showCommunity( " + rowObject.code + " )' class='btn btn-purple fa fa-bookmark-o btn-sm td-compile'>绑网点</a> ";
                        return opts;
                    }
                }
            ], ondblClickRow: function (rowid, iRow, iCol, e) {
                var row = $(this).jqGrid("getRowData", rowid);
                showCommunity(row.code);
            }, //设置页面
        });
    }


    /**查看*/
    function showCommunity(code) {
        var url = urls.ms + "/area/showCommunity.do?";
        if (code) {
            url = url + $.param({code: code});
        }
        $scope.width = "120px";
        templateform.open({
            title: "街道绑定网点",
            buttons: [],
            backdrop: true,
            keyboard: true,
            url: url,
            scope: $scope,
            onOpen: function ($modalInstance, data, $scope) {

            }
        });

    }

    //初始化
    init();
});