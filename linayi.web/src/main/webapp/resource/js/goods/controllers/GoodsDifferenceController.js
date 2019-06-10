'use strict';

app.controller('userCtrl', function($scope,toaster,userService,messager,templateform ) {

	function init(){
		$scope.list = list;
		$scope.exportData = exportData;
		$scope.search={
			goodsSkuId:'',
			name:'',
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
	/*		multiselect :true,*/
			rownumWidth: 100,
			colModel : [
			            {name:'goodsSkuId',label:'商品ID',width:80,sortable:false},
			            {name:'fullName',label:'商品名称',sortable:false,width:200},
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
								return   cellvalue>100? "<span style='color: #ff0d20;text-decoration:none;font-weight: bold;'>"+cellvalue +"%"+"</span>":"<span style='font-weight: bold;' >"+cellvalue+"%"+"</a>";
							}},
			            ],

		});
	}


	//导出数据
	function exportData() {
		var goodsSkuId = $scope.search.goodsSkuId;
		var name = $scope.search.name;
		var spreadRate = $scope.search.spreadRate;
		var data = '';
		if (goodsSkuId === undefined || goodsSkuId == '') {
			goodsSkuId = null;
		} else {
			data += '&goodsSkuId=' + goodsSkuId;
		}
		if (name === undefined || name == '') {
			name = null;
		} else {
			data += '&name=' + name;
		}
		if (spreadRate === undefined || spreadRate == '') {
			name = null;
		} else {
			data += '&spreadRate=' + spreadRate;
		}
		location.href = urls.ms + "/goods/goods/exportDifferenceRanking.do?" + data.replace("&", "");
		toaster.success("", "导出成功!", 1000);

	}

	//初始化
	init();
});
