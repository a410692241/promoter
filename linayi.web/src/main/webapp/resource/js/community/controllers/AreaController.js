'use strict';

app.controller('areaCtrl', function ($scope, toaster, areaService, messager, templateform,$http ) {
    var provinceList = [];
    function init() {
        $scope.list = list;
        $scope.showCommunity = showCommunity;
        $scope.edit = edit;
        $scope.save = save;
        $scope.list();
        provinceList = $scope.provinceList;
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
                            return "<span class='btn btn-blue btn-sm td-compile'>" + cellvalue + "</span>";
                            ;
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

    function edit() {
        //新增
        var url = urls.ms + "/area/getArea.do?";
        $http({
            method: 'post',
            url: url,
            responseType:"json"
        }).then(function successCallback(response) {
            openEdit( response.data.data )
            // this callback will be called asynchronously
            // when the response is available
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });
    }

    function openEdit(areaList) {
        console.log(areaList);
        debugger;
        var url = urls.ms + "/area/add.do?";
        templateform.open({
            title: "添加街道",
            url: url,
            scope: $scope,
            onOpen: function ($modalInstance, data, $scope) {
//				/*绑定数据edit*/
                provinceList = areaList[1000];


            }
        }, function ($modalInstance, data, $scope) {
            save($modalInstance, data, $scope);
        });
    }

    function save() {
        //新增
        try {
            areaService.save({
                data: $scope.community,
                success: function (data) {
                    if (data.respCode == "S") {
                        $("#areaCommunityList").trigger("reloadGrid");
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

    }


    //初始化
    init();
});