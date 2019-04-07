'use strict';

app.controller('routeCommunityCtrl', function($scope,toaster,routeCommunityService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				routeCommunityId:"",
				routeId:"",
				communityId:"",
				rank:"",
				createTime:"",
				routeTime:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#routeCommunityList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/community/routeCommunity/list.do",
			postData:$scope.search,
			pager : "#routeCommunityPager",
			colModel : [
				{name:'routeId',label:'路线名称',sortable:false}, 
				{name:'communityId',label:'社区',sortable:false}, 
				{name:'driverName',label:'司机',sortable:false}, 
				{name:'carNo',label:'车辆编号',sortable:false}, 
				{name:'plate',label:'车牌号',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.routeCommunityId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		routeCommunityService.remove({
    			"data":{routeCommunityId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#routeCommunityList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/routeCommunity/edit.do?";
		if( id ){
			url = url + $.param( {routeCommunityId:id} );
		}
		templateform.open({
			title:"RouteCommunity",
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
			routeCommunityService.save( {
				data:$scope.routeCommunity,
				success:function( data ){
					if( data.success ){
						$("#routeCommunityList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/routeCommunity/show.do?";
		if( id ){
			url = url + $.param( {routeCommunityId:id} );
		}
		templateform.open({
			title:"RouteCommunity",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});