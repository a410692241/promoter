'use strict';

app.controller('procurementOrderCtrl', function($scope,toaster,procurementOrderService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				procurementOrderId:"",
				taskId:"",
				orderId:"",
				orderCommunityId:"",
				goodsSkuId:"",
				quantity:"",
				allotQuantity:"",
				sortingQuantity:"",
				sortingOption:"",
				sortingStatus:"",
				boxId:"",
				receiveSite:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#procurementOrderList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/task/procurementOrder/list.do",
			postData:$scope.search,
			pager : "#procurementOrderPager",
			colModel : [
				{name:'procurementOrderId',label:'主键',sortable:false}, 
				{name:'taskId',label:'任务ID',sortable:false}, 
				{name:'orderId',label:'订单ID',sortable:false}, 
				{name:'orderCommunityId',label:'社区ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'quantity',label:'数量',sortable:false}, 
				{name:'allotQuantity',label:'实际分配数量',sortable:false}, 
				{name:'sortingQuantity',label:'分拣数量',sortable:false}, 
				{name:'sortingOption',label:'分拣操作',sortable:false}, 
				{name:'sortingStatus',label:'分拣状态',sortable:false}, 
				{name:'boxId',label:'流转箱ID',sortable:false}, 
				{name:'receiveSite',label:'位置编号',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.procurementOrderId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.procurementOrderId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.procurementOrderId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		procurementOrderService.remove({
    			"data":{procurementOrderId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#procurementOrderList").trigger("reloadGrid");
            			$scope.$apply(function(){
            				toaster.error( "",typeof e == "string" ? //
            						e : e.msg ? //
            								e.msg : "出错了",3000 );
						});
            			$modalInstance.close();
            		}else{
            			$scope.$apply(function(){
            				toaster.error( "",typeof e == "string" ? //
            						e : e.msg ? //
            								e.msg : "出错了",3000 );
						});
            		}
            	}
    		});
    	});
    }
    
    /**新增，编辑*/
    function edit( id ){
    	var url = urls.ms + "/task/procurementOrder/edit.do?";
		if( id ){
			url = url + $.param( {procurementOrderId:id} );
		}
		templateform.open({
			title:"ProcurementOrder",
			url:url,
			scope:$scope,
			onOpen:function( $modalInstance, data ,$scope){
				
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,data, $scope );
		});
    }
    
	/**保存*/
    function save( $modalInstance,data, $scope ){
    	try{
			procurementOrderService.save( {
				data:$scope.procurementOrder,
				success:function( data ){
					if( data.success ){
						$("#procurementOrderList").trigger("reloadGrid");
						$modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			} )
		}catch (e) {
			console.error( e );
			toaster.error( "",typeof e == "string" ? //
					e : e.msg ? //
							e.msg : "出错了",3000 );
		}
    }
    
    /**查看*/
    function show( id ){
    	var url = urls.ms + "/task/procurementOrder/show.do?";
		if( id ){
			url = url + $.param( {procurementOrderId:id} );
		}
		templateform.open({
			title:"ProcurementOrder",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});