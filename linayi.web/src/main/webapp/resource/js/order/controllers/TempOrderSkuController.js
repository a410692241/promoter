'use strict';

app.controller('tempOrderSkuCtrl', function($scope,toaster,tempOrderSkuService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				tempOrderSkuId:"",
				orderId:"",
				skuSupermarketId:"",
				supermarketId:"",
				price:"",
				quantity:"",
				communityId:"",
				buyFee:"",
				sortingCategoryId:"",
				createTime:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#tempOrderSkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/order/tempOrderSku/list.do",
			postData:$scope.search,
			pager : "#tempOrderSkuPager",
			colModel : [
				{name:'tempOrderSkuId',label:'主键',sortable:false}, 
				{name:'orderId',label:'订单ID',sortable:false}, 
				{name:'skuSupermarketId',label:'商品-超市ID',sortable:false}, 
				{name:'supermarketId',label:'超市ID',sortable:false}, 
				{name:'price',label:'价格',sortable:false}, 
				{name:'quantity',label:'数量',sortable:false}, 
				{name:'communityId',label:'社区ID',sortable:false}, 
				{name:'buyFee',label:'采买费',sortable:false}, 
				{name:'sortingCategoryId',label:'分拣类别',sortable:false}, 
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.tempOrderSkuId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.tempOrderSkuId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.tempOrderSkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		tempOrderSkuService.remove({
    			"data":{tempOrderSkuId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#tempOrderSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/tempOrderSku/edit.do?";
		if( id ){
			url = url + $.param( {tempOrderSkuId:id} );
		}
		templateform.open({
			title:"TempOrderSku",
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
			tempOrderSkuService.save( {
				data:$scope.tempOrderSku,
				success:function( data ){
					if( data.success ){
						$("#tempOrderSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/tempOrderSku/show.do?";
		if( id ){
			url = url + $.param( {tempOrderSkuId:id} );
		}
		templateform.open({
			title:"TempOrderSku",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});