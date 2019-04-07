'use strict';

app.controller('skuCommunityCtrl', function($scope,toaster,skuCommunityService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				skuCommunityId:"",
				goodsSkuId:"",
				communityId:"",
				minSupermarketId:"",
				maxSupermarketId:"",
				minPrice:"",
				maxPrice:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#skuCommunityList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/skuCommunity/list.do",
			postData:$scope.search,
			pager : "#skuCommunityPager",
			colModel : [
				{name:'skuCommunityId',label:'主键',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'communityId',label:'社区网点ID',sortable:false}, 
				{name:'minSupermarketId',label:'最低价超市ID',sortable:false}, 
				{name:'maxSupermarketId',label:'最高价超市ID',sortable:false}, 
				{name:'minPrice',label:'最低价超市价格',sortable:false}, 
				{name:'maxPrice',label:'最高价超市价格',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.skuCommunityId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.skuCommunityId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.skuCommunityId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		skuCommunityService.remove({
    			"data":{skuCommunityId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#skuCommunityList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/skuCommunity/edit.do?";
		if( id ){
			url = url + $.param( {skuCommunityId:id} );
		}
		templateform.open({
			title:"SkuCommunity",
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
			skuCommunityService.save( {
				data:$scope.skuCommunity,
				success:function( data ){
					if( data.success ){
						$("#skuCommunityList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/skuCommunity/show.do?";
		if( id ){
			url = url + $.param( {skuCommunityId:id} );
		}
		templateform.open({
			title:"SkuCommunity",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});