'use strict';

app.controller('orderManCtrl', function($scope,toaster,orderManService,messager,templateform ) {
	function init(){
		$scope.list = list;
        $scope.orderMan = orderMan;
		$scope.batchDelivery= batchDelivery;
		$scope.exportData = exportData;
		$scope.list();

        $scope.user = {
            isMember:''
        };
	}

	/**列表查询*/
	function list() {
		var $grid = $("#orderManList");
		if ($grid[0].grid) {
			$grid.jqGrid('setGridParam', {
				page: 1,
				postData: $scope.search
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url: urls.ms + "/promoter/promoter/ordermanList.do",
			postData: $scope.search,
			pager: "#orderManPager",
			multiselect: true,
			colModel: [
		{name:'orderManId',label:'家庭服务师ID'},
		{name:'nickName',label:'家庭服务师昵称',sortable:false},
		{name:'sex',label:'性别',sortable:false,width:50,formatter:function(cellvalue, options, rowObject){
			if(cellvalue == "MALE"){
				return "男";
			}else if(cellvalue == "FEMALE"){
				return "女";
			}
			return "";
		}},
		{name:'mobile',label:'绑定手机号',sortable:false},
		{name:'realName',label:'家庭服务师姓名',sortable:false},
		{name:'salesName',label:'上级家庭服务师姓名',sortable:false},
		{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
			return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
		}},
		{name:'startTime',label:'开始时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
			return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
		}},
		{name:'endTime',label:'结束时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
			return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
		}},

				{
					label: "操作",
					name: "opt",
					width: 300,
					sortable: false,
					formatter: function (cellvalue, options, rowObject) {
						var opts = "";
						// opts = opts + "<a href='javascript:void(0);' ng-click='edit( " + rowObject.userId + " )' class='btn btn-primary shiny fa fa-edit btn-sm td-compile'>编辑</a> ";

							if( rowObject.isOrderMan == 'FALSE' ){
								opts = opts + "<a href='javascript:void(0);' ng-click='orderMan( "+rowObject.orderManId+","+ 20+ ")' class='btn btn-primary fa fa-show btn-sm td-compile'>启用家庭服务师</a> ";
							}else{
								opts = opts + "<a href='javascript:void(0);' ng-click='orderMan( "+rowObject.orderManId+" ,"+ 10 + ")' class='btn btn-primary fa fa-show btn-sm td-compile'>禁用家庭服务师</a> ";
							}

						return opts;
					}}

			],
		});
	}
	function batchDelivery() {
		var procurementTaskIdList = [];
		for (var i = 0; i < item_selected.length; i++) {
			procurementTaskIdList[i] = parseInt(item_selected[i]);
		}
		if (procurementTaskIdList.length == 0) {
			toaster.error("", "请先选择要发货的商品", 3000);
			return;
		}
		messager.confirm("确认发货？",function( $modalInstance ) {
			var params = {procurementTaskIdList: procurementTaskIdList};
			$.ajax({
				url: urls.ms + "/procurement/procurement/batchDelivery.do",
				data: params,
				dataType: 'json',
				type: "POST",
				success: function (data) {
					if (data.respCode === "S") {
						$modalInstance.close();
						var page = $('#correctList').getGridParam('page')
						toaster.success("", "操作成功", 3000);
					} else {
						$modalInstance.close();
						toaster.error("", "操作失败", 3000);
					}
					list();
					item_selected = new Array();
					$('#correctList').jqGrid('setGridParam', {
						url: urls.ms + "/procurement/procurement/list.do",
						postData: $scope.search,
						datatype: 'json',
						page: page,
					}).trigger("reloadGrid");
				}
			});
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
		if ( receiveStatus=='showAll' || deliveryWaveTime === undefined || deliveryWaveTime == '') {
			receiveStatus = null;
			sum++;
		} else {
			data += '&receiveStatus=' + receiveStatus;
		}
		if(sum==4){
			toaster.error("", "请先输入搜索条件后再导出!", 3000);
			return;
		}

		//window.location.href = urls.ms + "/goods/goods/exportGoodsData.do?" + data.replace("&", "");
		location.href = urls.ms + "/procurement/procurement/exportData.do?" + data.replace("&", "");
		toaster.success("", "导出成功!", 1000);


	}

    /**禁用/启用家庭服务师*/
    function orderMan(orderManId,man){
        var isOrderMan;
        if (man==20){
            isOrderMan="FALSE";
        }else{
            isOrderMan="TRUE";
        }
        var text = man == 20 ? "启用" : "禁用"
        messager.confirm("确认"+ text + "?",function( $modalInstance ){
            $.ajax({
                url:urls.ms+"/user/user/enableorderMan.do",
                data:{
                    userId:orderManId,
                    isOrderMan:isOrderMan
                },
                dataType:"json",
                type:"post",
                success:function( data ){
                    if( data.respCode == "S" ){
                        list();
                        $scope.$apply(function(){
                            $modalInstance.close();
                        });

                    }else{
                        toaster.error( "",data.msg,3000 );
                    }
                }
            });
        });
    }


	//初始化
	init();
});