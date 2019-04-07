'use strict';

app.controller('sortingSkuCtrl', function($scope,toaster,sortingSkuService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				sortingSkuId:"",
				taskId:"",
				taskCommunityId:"",
				orderId:"",
				goodsSkuId:"",
				supermarketId:"",
				sortingQuantity:"",
				sortingOption:"",
				boxId:"",
				flowerQuantity:"",
				flowerOption:"",
				status:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#sortingSkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/flower/sortingSku/list.do",
			postData:$scope.search,
			pager : "#sortingSkuPager",
			colModel : [
				{name:'sortingSkuId',label:'主键',sortable:false}, 
				{name:'taskId',label:'任务ID',sortable:false}, 
				{name:'taskCommunityId',label:'社区ID',sortable:false}, 
				{name:'orderId',label:'订单ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'supermarketId',label:'超市ID',sortable:false}, 
				{name:'sortingQuantity',label:'数量',sortable:false}, 
				{name:'sortingOption',label:'操作',sortable:false}, 
				{name:'boxId',label:'流转箱ID',sortable:false}, 
				{name:'flowerQuantity',label:'流转收货数量',sortable:false}, 
				{name:'flowerOption',label:'流转收货操作',sortable:false}, 
				{name:'status',label:'状态',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.sortingSkuId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.sortingSkuId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.sortingSkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		sortingSkuService.remove({
    			"data":{sortingSkuId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#sortingSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/flower/sortingSku/edit.do?";
		if( id ){
			url = url + $.param( {sortingSkuId:id} );
		}
		templateform.open({
			title:"SortingSku",
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
			sortingSkuService.save( {
				data:$scope.sortingSku,
				success:function( data ){
					if( data.success ){
						$("#sortingSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/flower/sortingSku/show.do?";
		if( id ){
			url = url + $.param( {sortingSkuId:id} );
		}
		templateform.open({
			title:"SortingSku",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});