'use strict';

app.controller('taskCtrl', function($scope,toaster,userService,messager,templateform ) {

	function init(){
		$scope.list = list;
		$scope.exportData = exportData;
		$scope.search={
			priceType:'',
            supermarkerName:'',
			manualAuditStatus:'',
			realName:'',
			createTimeStart:'',
			createTimeEnd:''
		};
		$scope.list();
	}

	/**列表查询*/
	function list(){
		var $grid = $("#AuditTaskList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/correct/correct/getTaskList.do",
/*			postData:$scope.search,*/
			pager : "#TaskListPager",
	/*		multiselect :true,*/
			rownumWidth: 100,
			colModel : [
			            {name:'goodsSkuId',label:'商品ID',width:50,sortable:false},
			            {name:'fullName',label:'商品名称',sortable:false,width:200},
						{name:'supermarkerName',label:'超市名称',sortable:false,width:150},
						{name:'barcode',label:'商品条码',sortable:false,width:100},
						{name:'priceType',label:'审核类型',sortable:false,width:80},
			            {name:'price',label:'价格(元)',sortable:false,width:60,
							formatter: function (cellvalue, options, rowObject) {
								return cellvalue / 100;
							},},
						{name:'manualAuditStatus',label:'审核状态',sortable:false,width:80},
						{name:'realName',label:'审核人',sortable:false,width:80},
						{name:'createTime',label:'发布时间',sortable:false,
                            width: 166,
                            formatter: function (cellvalue, options, rowObject) {
                                return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
                            }
                        },
			            ],

			            onSelectRow:function(rowId,status){
			            	var rows = $( this ).jqGrid( "getRowData" , rowId);
			            	if(status){
			            		$scope.checkUserId.add(rows.userId);
			            	}else{
			            		$scope.checkUserId.deletes(rows.userId);
			            	}
			            }
		});
	}

	/*//查看其它价格
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

*/


	//导出数据
	function exportData() {
		var priceType = $scope.search.priceType;
		var supermarkerName = $scope.search.supermarkerName;
		var manualAuditStatus = $scope.search.manualAuditStatus;
		var realName = $scope.search.realName;
        var createTimeStart = $scope.search.createTimeStart;
        var createTimeEnd = $scope.search.createTimeEnd;

		var data = '';
		if (priceType === undefined || priceType == '') {
            priceType = null;
		} else {
			data += '&priceType=' + priceType;
		}
		if (supermarkerName === undefined || supermarkerName == '') {
            supermarkerName = null;
		} else {
			data += '&supermarkerName=' + supermarkerName;
		}

		if (manualAuditStatus === undefined || manualAuditStatus == '') {
            manualAuditStatus = null;
		} else {
			data += '&manualAuditStatus=' + manualAuditStatus;
		}

		if (realName === undefined || realName == '') {
            realName = null;
		} else {
			data += '&realName=' + realName;
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


		location.href = urls.ms + "/goods/goods/viewTask.do?" + data.replace("&", "");
		toaster.success("", "导出成功!", 1000);

	}

	//初始化
	init();
});
