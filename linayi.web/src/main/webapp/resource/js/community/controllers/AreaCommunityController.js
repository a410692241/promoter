'use strict';

app.controller('areaCommunityCtrl', function ($scope, toaster, areaCommunityService,areaService, messager, templateform) {

    function init() {

        $scope.list = list;
        $scope.bindCommunity = bindCommunity;
        $scope.search = {
            name: "",
        };
        $scope.list();
    }

    /**列表查询*/
    function list() {
        var $grid = $("#areaCommunityList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search,
            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url: urls.ms + "/community/community/list.do",
            postData: $scope.search,
            pager: "#areaCommunityPager",
            colModel: [
                {name: 'name', label: '社区名称', sortable: false},
                {name: 'mobile', label: '手机号', sortable: false},
                {name: 'address', label: '地址', sortable: false, width: 200},
                {
                    label: "操作",
                    name: "opt",
                    width: 120,
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var opts = "";
                        if (rowObject.communityId == $scope.communityId) {
                            opts = opts + "<a href='javascript:void(0);' ng-click='bindCommunity( " + rowObject.communityId + " )' class='btn btn-danger disabled fa fa-bookmark-o btn-sm td-compile'>已绑</a> ";
                        } else {
                            opts = opts + "<a href='javascript:void(0);' ng-click='bindCommunity( " + rowObject.communityId + " )' class='btn btn-purple fa fa-bookmark-o btn-sm td-compile'>绑定</a> ";
                        }
                        return opts;
                    }
                }
            ]
        });
    }


    function bindCommunity(communityId) {
        var code = $scope.code;
        messager.confirm("确认绑定？", function ($modalInstance) {
            var url = urls.ms + "/area/bindCommunity.do?";
            $.ajax({
                url: url,
                async: false,
                data: {communityId: communityId, code: code},
                dataType: "json",
                success: function (data) {
                    if (data.respCode === 'S') {
                        $modalInstance.close();
                        toaster.success("", "操作成功", 3000);
                        $scope.communityId = communityId;
                        $("#areaCommunityList").trigger("reloadGrid");
                    } else {
                        toaster.error("", data.msg, 3000);
                    }
                }
            })

        });
    }


    //初始化
    init();
});
