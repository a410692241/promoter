'use strict';

app.controller('orderStoreCtrl', function($scope,toaster,orderStoreService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				orderStoreId:"",
				orderId:"",
				goodsSkuId:"",
				supermarketId:"",
				quantity:"",
				status:"",
				receiveSite:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#orderStoreList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/flower/orderStore/list.do",
			postData:$scope.search,
			pager : "#orderStorePager",
			colModel : [
				{name:'orderStoreId',label:'主键',sortable:false}, 
				{name:'orderId',label:'订单ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'supermarketId',label:'超市ID',sortable:false}, 
				{name:'quantity',label:'入库数量',sortable:false}, 
				{name:'status',label:'状态',sortable:false}, 
				{name:'receiveSite',label:'所在位置',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.orderStoreId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.orderStoreId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.orderStoreId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		orderStoreService.remove({
    			"data":{orderStoreId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#orderStoreList").trigger("reloadGrid");
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
    	var url = urls.ms + "/flower/orderStore/edit.do?";
		if( id ){
			url = url + $.param( {orderStoreId:id} );
		}
		templateform.open({
			title:"OrderStore",
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
			orderStoreService.save( {
				data:$scope.orderStore,
				success:function( data ){
					if( data.success ){
						$("#orderStoreList").trigger("reloadGrid");
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
    	var url = urls.ms + "/flower/orderStore/show.do?";
		if( id ){
			url = url + $.param( {orderStoreId:id} );
		}
		templateform.open({
			title:"OrderStore",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});