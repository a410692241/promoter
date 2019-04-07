'use strict';

app.controller('rechargeCtrl', function($scope,toaster,rechargeService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				rechargeId:"",
				userId:"",
				amount:"",
				preAmount:"",
				status:"",
				adminId:"",
				createTime:"",
				type:"",
				userType:"",
				serviceFee:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#rechargeList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/user/recharge/list.do",
			postData:$scope.search,
			pager : "#rechargePager",
			colModel : [
				{name:'rechargeId',label:'主键',sortable:false}, 
				{name:'userId',label:'用户ID',sortable:false}, 
				{name:'amount',label:'金额',sortable:false}, 
				{name:'preAmount',label:'充值时余额',sortable:false}, 
				{name:'status',label:'状态',sortable:false}, 
				{name:'adminId',label:'操作员',sortable:false}, 
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'type',label:'类型',sortable:false}, 
				{name:'userType',label:'用户类型',sortable:false}, 
				{name:'serviceFee',label:'服务费',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.rechargeId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.rechargeId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.rechargeId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		rechargeService.remove({
    			"data":{rechargeId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#rechargeList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/recharge/edit.do?";
		if( id ){
			url = url + $.param( {rechargeId:id} );
		}
		templateform.open({
			title:"Recharge",
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
			rechargeService.save( {
				data:$scope.recharge,
				success:function( data ){
					if( data.success ){
						$("#rechargeList").trigger("reloadGrid");
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
    	var url = urls.ms + "/user/recharge/show.do?";
		if( id ){
			url = url + $.param( {rechargeId:id} );
		}
		templateform.open({
			title:"Recharge",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});