'use strict';

app.controller('smallCommunityReqCtrl', function ($scope, toaster, smallCommunityReqService, messager, templateform) {
    var item_selected;

    function init() {
        $scope.show = show;
        $scope.list = list;
        $scope.batchUpdateStatus = batchUpdateStatus;
        $scope.updateStatus = updateStatus;
        $scope.list();
        item_selected = new Array();
        $scope.search = {
            smallCommunityReqId: '',
            status: '',
            smallCommunity: '',
            nickname: '',
            mobile: '',
            createTime: ''
        }
    }

    /**列表查询*/
    function list() {
        var $grid = $("#smallCommunityReqList");
        if ($grid[0].grid) {
            $grid.jqGrid('setGridParam', {
                page: 1,
                postData: $scope.search,
            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url: urls.ms + "/smallReqCommunity/list.do",
            postData: $scope.search,
            pager: "#smallCommunityReqPager",
            multiselect: true,
            colModel: [
                {name: 'smallCommunityReqId', label: '主键', hidden: true},
                {name: 'smallCommunity', label: '小区', sortable: false},
                {
                    name: 'status', label: '状态', sortable: false, formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == "PROCESSED") {
                            return "已查看";
                        } else if (cellvalue == "NOTVIEWED") {
                            return "<span style='color:red;border: 1px solid red;padding-left: 10px;padding-right: 10px'>未查看</span>";
                        }
                    }
                },

                {name: 'nickname', label: '申请人昵称', sortable: false},
                {name: 'mobile', label: '申请人手机号', sortable: false},
                {
                    name: 'createTime',
                    label: '创建时间',
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd hh:mm:ss") : "";
                    }
                },
                {
                    label: "操作",
                    name: "opt",
                    sortable: false,
                    formatter: function (cellvalue, options, rowObject) {
                        var opts = "";
                        opts = opts + "<a href='javascript:void(0);' ng-click='show( " + rowObject.smallCommunityReqId + " )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
                        if (rowObject.status == "PROCESSED") {
                            opts = opts + "<a href='javascript:void(0);' ng-click='updateStatus( " + rowObject.smallCommunityReqId + ',"NOTVIEWED"' + "  )' class='btn btn-danger fa fa-reply btn-sm td-compile'>取消</a> ";
                        } else {
                            opts = opts + "<a href='javascript:void(0);' ng-click='updateStatus( " + rowObject.smallCommunityReqId + ',"PROCESSED"' + ")' class='btn btn-purple fa fa-bookmark-o btn-sm td-compile'>处理</a> ";

                        }
                        return opts;
                    }
                }
            ], ondblClickRow: function (rowid, iRow, iCol, e) {
                var row = $(this).jqGrid("getRowData", rowid);
                show(row.smallCommunityReqId);
            }, onSelectAll: function (aRowids, status) {
                for (var index = 0; index < aRowids.length; index++) {
                    var str = $("#smallCommunityReqList").jqGrid('getRowData', aRowids[index]).smallCommunityReqId;
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
                var str = $("#smallCommunityReqList").jqGrid('getRowData', aRowids).smallCommunityReqId;
                if (status) {
                    item_selected.push(str);
                } else {
                    for (var i = 0; i < item_selected.length; i++) {
                        if (item_selected[i] == str) {
                            item_selected.splice(i, 1);
                        }
                    }
                }

            }, //设置页面
            loadComplete: function () {
                var dataCorrectArr = new Array();
                var dataArr = $("#smallCommunityReqList").jqGrid('getRowData');
                for (var i = 0; i < dataArr.length; i++) {
                    dataCorrectArr.push(dataArr[i].smallCommunityReqId);
                }
                for (var j = 0; j < item_selected.length; j++) {
                    for (var i = 0; i < dataCorrectArr.length; i++) {
                        if (dataCorrectArr[i] == item_selected[j]) {
                            $(this).jqGrid('setSelection', i, false);
                        }

                    }
                }
            },

        });
    }


    /**查看*/
    function show(id) {
        var url = urls.ms + "/smallReqCommunity/show.do?";
        if (id) {
            url = url + $.param({smallCommunityReqId: id});
        }
        templateform.open({
            title: "待添加小区信息",
            buttons: [],
            backdrop: true,
            keyboard: true,
            url: url
        });

    }

    /**查看*/
    function updateStatus(id, status) {
        messager.confirm("确认操作？", function ($modalInstance) {
            var url = urls.ms + "/smallReqCommunity/updateStatus.do?";
            $.ajax({
                url: url,
                async: false,
                data: {smallCommunityReqId: id, status: status},
                dataType: "json",
                success: function (data) {
                    if (data.respCode === 'S') {
                        $modalInstance.close();
                        toaster.success("", "操作成功", 3000);
                        $("#smallCommunityReqList").trigger("reloadGrid");
                    } else {
                        toaster.error("", data.msg, 3000);
                    }
                }
            })

        });
    }


    /**批量查看*/
    function batchUpdateStatus(id) {
        var idList = [];
        for (var i = 0; i < item_selected.length; i++) {
            idList[i] = parseInt(item_selected[i]);
        }
        var params = {idList: idList};
        messager.confirm("确认操作？", function ($modalInstance) {
            var url = urls.ms + "/smallReqCommunity/batchUpdateStatus.do?";
            $.ajax({
                url: url,
                async: false,
                data: params,
                dataType: "json",
                success: function (data) {
                    if (data.respCode === 'S') {
                        $modalInstance.close();
                        toaster.success("", "操作成功", 3000);
                        item_selected = new Array();
                        $("#smallCommunityReqList").trigger("reloadGrid");
                    } else {
                        toaster.error("", data.msg, 3000);
                    }
                }
            });
        });
    }

    //初始化
    init();
});