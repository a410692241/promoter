'use strict';

app.controller('shoppingCarCtrl', function($scope,toaster,shoppingCarService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				shoppingCarId:"",
				userId:"",
				goodsSkuId:"",
				supermarketId:"",
				quantity:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#shoppingCarList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/user/shoppingCar/list.do",
			postData:$scope.search,
			pager : "#shoppingCarPager",
			colModel : [
				{name:'shoppingCarId',label:'主键',sortable:false}, 
				{name:'userId',label:'用户ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品',sortable:false}, 
				{name:'supermarketId',label:'超市ID',sortable:false}, 
				{name:'quantity',label:'数量',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.shoppingCarId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.shoppingCarId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.shoppingCarId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		shoppingCarService.remove({
    			"data":{shoppingCarId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#shoppingCarList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/shoppingCar/edit.do?";
		if( id ){
			url = url + $.param( {shoppingCarId:id} );
		}
		templateform.open({
			title:"ShoppingCar",
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
			shoppingCarService.save( {
				data:$scope.shoppingCar,
				success:function( data ){
					if( data.success ){
						$("#shoppingCarList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/shoppingCar/show.do?";
		if( id ){
			url = url + $.param( {shoppingCarId:id} );
		}
		templateform.open({
			title:"ShoppingCar",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});