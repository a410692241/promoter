'use strict';

app.controller('flowCenterCtrl', function($scope,toaster,flowCenterService,messager,templateform ) {
	var item_selected = new Array();
	function init(){
		$scope.list = list;
		$scope.batchDelivery= batchDelivery;
		$scope.exportData = exportData;
		$scope.list();
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
			url: urls.ms + "/procurement/procurement/list.do",
			postData: $scope.search,
			pager: "#correctPager",
			multiselect: true,
			colModel: [
				{name: 'procurementTaskId', label: '主键', sortable: false, hidden: true},
				{name: 'communityName', label: '网点名称', sortable: false},
				{name: 'deliveryWaveTime', label: '配送波次', sortable: false},
				{name: 'ordersId', label: '顾客订单号', sortable: false},
				{name: 'receiveStatus', label: '订单状态', sortable: false},
				{name: 'fullName', label: '商品名称', sortable: false},
				{name: 'barcode', label: '商品条码', sortable: false},
				{name: 'actualQuantity', label: '数量', sortable: false},
				{
					name: 'price',
					label: '商品单价(元)',
					width: 100,
					sortable: false,
					formatter: function (cellvalue, options, rowObject) {
						return cellvalue / 100;
					}
				},
				{name: 'supermarketName', label: '采买超市', sortable: false},
				{
					name: 'createTime',
					label: '下单时间',
					sortable: false,
					formatter: function (cellvalue, options, rowObject) {
						return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss") : "";
					}
				},

				// {
				// 	label: "操作",
				// 	name: "opt",
				// 	width: 300,
				// 	sortable: false,
				// 	formatter: function (cellvalue, options, rowObject) {
				// 		var opts = "";
				//
				// 		opts = opts + "<a href='javascript:void(0);' ng-click='show( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-eye btn-sm td-compile'>详情</a> ";
				// 		opts = opts + "<a href='javascript:void(0);' ng-click='edit( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>编辑</a> ";
				// 		/*opts = opts + "<a href='javascript:void(0);' ng-click='cancel( "+rowObject.correctId+" )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>撤销</a> ";*/
				// 		var status = rowObject.status;
				// 		;
				// 		if (status === "WAIT_AUDIT" || status === "AUDIT_SUCCESS") {
				// 			opts = opts + "<a href='javascript:void(0);' ng-click='cancel( " + rowObject.correctId + " )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>撤销</a> ";
				// 		}
				// 		//待纠错提供审核按钮
				// 		if (status === "WAIT_AUDIT") {
				// 			opts = opts + "<a href='javascript:void(0);' ng-click='audit( " + rowObject._rowId + ',' + 70 + " )' class='btn btn-darkorange fa fa-edit btn-sm td-compile'>审核</a> ";
				// 		}
				// 		return opts;
				// 	}
				// }
			],
			//设置页面
			loadComplete: function () {
				var dataCorrectArr = new Array();
				var dataArr = $("#correctList").jqGrid('getRowData');
				for (var i = 0; i < dataArr.length; i++) {
					dataCorrectArr.push(dataArr[i].procurementTaskId);
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
					var str = $("#correctList").jqGrid('getRowData', aRowids[index]).procurementTaskId;
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
				var str = $("#correctList").jqGrid('getRowData', aRowids).procurementTaskId;
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
	function batchDelivery() {
		var procurementTaskIdList = [];
		for (var i = 0; i < item_selected.length; i++) {
			procurementTaskIdList[i] = parseInt(item_selected[i]);
		}

		var params = {procurementTaskIdList: procurementTaskIdList};
		$.ajax({
			url: urls.ms + "/procurement/procurement/batchDelivery.do",
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
					url: urls.ms + "/procurement/procurement/batchDelivery.do",
					postData: $scope.search,
					datatype: 'json',
					page: page,
				}).trigger("reloadGrid");
			}
		});
	}
	//导出数据
	function exportData() {
		var deliveryWaveTime = $scope.search.deliveryWaveTime;
		var startTime = $scope.search.startTime;
		var endTime = $scope.search.endTime;
		var receiveStatus = $scope.search.receiveStatus;
		var sum = 0;
		var data = '';
		if (deliveryWaveTime === undefined || deliveryWaveTime == '' || deliveryWaveTime=='showAll') {
			deliveryWaveTime = null;
			sum++;
		} else {
			data += '&deliveryWaveTime=' + deliveryWaveTime;
		}
		if (startTime === undefined || startTime == '') {
			startTime = null;
			sum++;
		} else {
			data += '&startTime=' + startTime;
		}
		if (endTime === undefined || endTime == '') {
			endTime = null;
			sum++;
		} else {
			data += '&endTime=' + endTime;
		}
		if ( receiveStatus='showAll' || deliveryWaveTime === undefined || deliveryWaveTime == '') {
			receiveStatus = null;
			sum++;
		} else {
			data += '&receiveStatus=' + receiveStatus;
		}
		if(sum==4){
			toaster.error("", "请输入搜索条件后导出!", 3000);
			return;
		}
		//window.location.href = urls.ms + "/goods/goods/exportGoodsData.do?" + data.replace("&", "");
		location.href = urls.ms + "/procurement/procurement/exportData.do?" + data.replace("&", "");
		toaster.success("", "导出成功!", 1000);


	}
    //初始化
    init();
});