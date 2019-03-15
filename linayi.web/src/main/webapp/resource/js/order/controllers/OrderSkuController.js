'use strict';

app.controller('orderSkuCtrl', function($scope,toaster,orderSkuService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				orderSkuId:"",
				orderId:"",
				goodsSkuId:"",
				supermarketId:"",
				quantity:"",
				price:"",
				maxPrice:"",
				maxSupermarketId:"",
				receiveQuantity:"",
				receiveOption:"",
				serviceFee:"",
				deliverFee:"",
				buyFee:"",
				supermarketLs:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#orderSkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/order/orderSku/list.do",
			postData:$scope.search,
			pager : "#orderSkuPager",
			colModel : [
				{name:'orderSkuId',label:'主键',sortable:false}, 
				{name:'orderId',label:'订单ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'supermarketId',label:'超市ID',sortable:false}, 
				{name:'quantity',label:'数量',sortable:false}, 
				{name:'price',label:'用户创建订单时价格',sortable:false}, 
				{name:'maxPrice',label:'最高价',sortable:false}, 
				{name:'maxSupermarketId',label:'最高价超市',sortable:false}, 
				{name:'receiveQuantity',label:'收货数量',sortable:false}, 
				{name:'receiveOption',label:'操作',sortable:false}, 
				{name:'serviceFee',label:'订单服务费',sortable:false}, 
				{name:'deliverFee',label:'配送服务费',sortable:false}, 
				{name:'buyFee',label:'采买服务费',sortable:false}, 
				{name:'supermarketLs',label:'比价超市',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.orderSkuId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.orderSkuId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.orderSkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		orderSkuService.remove({
    			"data":{orderSkuId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#orderSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/orderSku/edit.do?";
		if( id ){
			url = url + $.param( {orderSkuId:id} );
		}
		templateform.open({
			title:"OrderSku",
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
			orderSkuService.save( {
				data:$scope.orderSku,
				success:function( data ){
					if( data.success ){
						$("#orderSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/orderSku/show.do?";
		if( id ){
			url = url + $.param( {orderSkuId:id} );
		}
		templateform.open({
			title:"OrderSku",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});