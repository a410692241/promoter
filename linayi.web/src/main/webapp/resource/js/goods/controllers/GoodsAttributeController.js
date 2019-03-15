'use strict';

app.controller('goodsAttributeCtrl', function($scope,toaster,goodsAttributeService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				goodsAttributeId:"",
				goodsId:"",
				attributeId:"",
				sales:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#goodsAttributeList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/goodsAttribute/list.do",
			postData:$scope.search,
			pager : "#goodsAttributePager",
			colModel : [
				{name:'goodsAttributeId',label:'主键',sortable:false}, 
				{name:'goodsId',label:'货品ID',sortable:false}, 
				{name:'attributeId',label:'商品属性ID',sortable:false}, 
				{name:'sales',label:'是否销售属性',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.goodsAttributeId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.goodsAttributeId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.goodsAttributeId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		goodsAttributeService.remove({
    			"data":{goodsAttributeId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#goodsAttributeList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/goodsAttribute/edit.do?";
		if( id ){
			url = url + $.param( {goodsAttributeId:id} );
		}
		templateform.open({
			title:"GoodsAttribute",
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
			goodsAttributeService.save( {
				data:$scope.goodsAttribute,
				success:function( data ){
					if( data.success ){
						$("#goodsAttributeList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/goodsAttribute/show.do?";
		if( id ){
			url = url + $.param( {goodsAttributeId:id} );
		}
		templateform.open({
			title:"GoodsAttribute",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});