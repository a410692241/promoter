'use strict';

app.controller('earningsItemCtrl', function($scope,toaster,earningsItemService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				earningsItemId:"",
				earningsId:"",
				amount:"",
				type:"",
				objectId:"",
				communityId:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#earningsItemList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/user/earningsItem/list.do",
			postData:$scope.search,
			pager : "#earningsItemPager",
			colModel : [
				{name:'earningsItemId',label:'主键',sortable:false}, 
				{name:'earningsId',label:'收益ID',sortable:false}, 
				{name:'amount',label:'收益金额',sortable:false}, 
				{name:'type',label:'收益类型',sortable:false}, 
				{name:'objectId',label:'对象ID',sortable:false}, 
				{name:'communityId',label:'任务社区ID',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.earningsItemId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.earningsItemId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.earningsItemId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		earningsItemService.remove({
    			"data":{earningsItemId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#earningsItemList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/earningsItem/edit.do?";
		if( id ){
			url = url + $.param( {earningsItemId:id} );
		}
		templateform.open({
			title:"EarningsItem",
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
			earningsItemService.save( {
				data:$scope.earningsItem,
				success:function( data ){
					if( data.success ){
						$("#earningsItemList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/earningsItem/show.do?";
		if( id ){
			url = url + $.param( {earningsItemId:id} );
		}
		templateform.open({
			title:"EarningsItem",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});