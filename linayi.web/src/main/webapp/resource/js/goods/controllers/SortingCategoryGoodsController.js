'use strict';

app.controller('sortingCategoryGoodsCtrl', function($scope,toaster,sortingCategoryGoodsService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				sortingCategoryGoodsId:"",
				goodsId:"",
				sortingCategoryId:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#sortingCategoryGoodsList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/sortingCategoryGoods/list.do",
			postData:$scope.search,
			pager : "#sortingCategoryGoodsPager",
			colModel : [
				{name:'sortingCategoryGoodsId',label:'主键',sortable:false}, 
				{name:'goodsId',label:'货品ID',sortable:false}, 
				{name:'sortingCategoryId',label:'分拣类别ID',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.sortingCategoryGoodsId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.sortingCategoryGoodsId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.sortingCategoryGoodsId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		sortingCategoryGoodsService.remove({
    			"data":{sortingCategoryGoodsId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#sortingCategoryGoodsList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/sortingCategoryGoods/edit.do?";
		if( id ){
			url = url + $.param( {sortingCategoryGoodsId:id} );
		}
		templateform.open({
			title:"SortingCategoryGoods",
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
			sortingCategoryGoodsService.save( {
				data:$scope.sortingCategoryGoods,
				success:function( data ){
					if( data.success ){
						$("#sortingCategoryGoodsList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/sortingCategoryGoods/show.do?";
		if( id ){
			url = url + $.param( {sortingCategoryGoodsId:id} );
		}
		templateform.open({
			title:"SortingCategoryGoods",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});