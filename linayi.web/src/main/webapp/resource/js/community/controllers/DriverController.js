'use strict';

app.controller('driverCtrl', function($scope,toaster,driverService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				driverId:"",
				nickname:"",
				mobile:"",
				account:"",
				password:"",
				address:"",
				areaCode:"",
				ammount:"",
				remark:"",
				createTime:"",
				lastModify:"",
				mobileToken:"",
				removed:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#driverList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/community/driver/list.do",
			postData:$scope.search,
			pager : "#driverPager",
			colModel : [
				{name:'nickname',label:'昵称',sortable:false,width:100}, 
				{name:'plate',label:'车牌号',sortable:false,width:60}, 
				{name:'carNo',label:'车辆编号',sortable:false,width:60}, 
				{name:'mobile',label:'手机号',sortable:false,width:80}, 
				{name:'account',label:'账号',sortable:false,width:80}, 
				{name:'address',label:'地址',sortable:false,width:200}, 
				{name:'remark',label:'备注',sortable:false,width:200}, 
	             {label:"操作",name:"opt",sortable:false,width:60,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.driverId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		driverService.remove({
    			"data":{driverId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#driverList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/driver/edit.do?";
		if( id ){
			url = url + $.param( {driverId:id} );
		}
		templateform.open({
			title:"司机信息",
			url:url,
			scope:$scope,
			onOpen:function( $modalInstance, data ,$scope){
				
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,$scope.driver, $scope );
		});
    }
    
	/**保存*/
    function save( $modalInstance,data, $scope ){
    	try{
			driverService.save( {
				data:$scope.driver,
				success:function( data ){
					if( data.success ){
						$("#driverList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/driver/show.do?";
		if( id ){
			url = url + $.param( {driverId:id} );
		}
		templateform.open({
			title:"Driver",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});