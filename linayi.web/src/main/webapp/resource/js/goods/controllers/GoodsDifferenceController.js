'use strict';

app.controller('userCtrl', function($scope,toaster,userService,messager,templateform ) {

	function init(){

		$scope.list = list;
		$scope.otherPrice = otherPrice;
		$scope.exportData = exportData;
		$scope.search={
			goodsSkuId:'',
			fullName:'',
			barcode:'',
			spreadRate:''
		};
		$scope.list();

	}

	/**列表查询*/
	function list(){
		var $grid = $("#GoodsDifferenceList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/goods/getBackstageDifference.do",
/*			postData:$scope.search,*/
			pager : "#GoodsDifferenceListPager",
			multiselect :true,
			rownumWidth: 100,
			colModel : [
			            {name:'goodsSkuId',label:'商品ID',width:80},
			            {name:'fullName',label:'商品名称',sortable:false,width:200},
						{name:'barcode',label:'商品条码',sortable:false,width:80},
			            {name:'maxPrice',label:'最高价(单位:元)',sortable:false,
							formatter: function (cellvalue, options, rowObject) {
								return cellvalue / 100;
							},},
						{name:'minPrice',label:'最低价(单位:元)',sortable:false,
							formatter: function (cellvalue, options, rowObject) {
								return cellvalue / 100;
							},},
						{name:'spreadRate',label:'价差率(%)',sortable:false,
							formatter: function (cellvalue, options, rowObject) {
								return   cellvalue>100? "<a style='color: red;text-decoration:none;font-weight: bold;' href='javascript:void(0);' ng-click='otherPrice( " + rowObject.goodsSkuId + " )' class='btn-sm td-compile'>"+cellvalue +"%"+"</a>":"<a style='font-weight: bold;' href='javascript:void(0);' ng-click='otherPrice( " + rowObject.goodsSkuId + " )' class='btn-sm td-compile'>"+cellvalue+"%"+"</a>";
							}},
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

	//查看其它价格
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

	//导出数据
	function exportData() {
		var goodsSkuId = $scope.search.goodsSkuId;
		var fullName = $scope.search.fullName;
		var barcode = $scope.search.barcode;
		var spreadRate = $scope.search.spreadRate;
		var data = '';
		if (goodsSkuId === undefined || goodsSkuId == '') {
			goodsSkuId = null;
		} else {
			data += '&goodsSkuId=' + goodsSkuId;
		}
		if (fullName === undefined || fullName == '') {
			fullName = null;
		} else {
			data += '&fullName=' + fullName;
		}

		if (barcode === undefined || barcode == '') {
			barcode = null;
		} else {
			data += '&barcode=' + barcode;
		}

		if (spreadRate === undefined || spreadRate == '') {
			spreadRate = null;
		} else {
			data += '&spreadRate=' + spreadRate;
		}

		location.href = urls.ms + "/goods/goods/exportDifferenceRanking.do?" + data.replace("&", "");
		toaster.success("", "导出成功!", 1000);


	}

	//初始化
	init();
});