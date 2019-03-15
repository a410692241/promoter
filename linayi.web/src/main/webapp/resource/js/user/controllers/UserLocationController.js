'use strict';

app.controller('userLocationCtrl', function($scope,toaster,userLocationService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				userLocationId:"",
				address:"",
				areaCode:"",
				userId:"",
				nickname:"",
				mobile:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#userLocationList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/user/userLocation/list.do",
			postData:$scope.search,
			pager : "#userLocationPager",
			colModel : [
				{name:'userLocationId',label:'主键',sortable:false}, 
				{name:'address',label:'详细地址',sortable:false}, 
				{name:'areaCode',label:'地区编码',sortable:false}, 
				{name:'userId',label:'用户ID',sortable:false}, 
				{name:'nickname',label:'联系人昵称',sortable:false}, 
				{name:'mobile',label:'联系电话',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.userLocationId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.userLocationId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.userLocationId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		userLocationService.remove({
    			"data":{userLocationId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#userLocationList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/userLocation/edit.do?";
		if( id ){
			url = url + $.param( {userLocationId:id} );
		}
		templateform.open({
			title:"UserLocation",
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
			userLocationService.save( {
				data:$scope.userLocation,
				success:function( data ){
					if( data.success ){
						$("#userLocationList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/userLocation/show.do?";
		if( id ){
			url = url + $.param( {userLocationId:id} );
		}
		templateform.open({
			title:"UserLocation",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});