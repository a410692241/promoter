'use strict';

app.controller('evaluateCtrl', function($scope,toaster,evaluateService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				evaluateId:"",
				orderId:"",
				content:"",
				deliverScore:"",
				platformScore:"",
				communityId:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#evaluateList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/order/evaluate/list.do",
			postData:$scope.search,
			pager : "#evaluatePager",
			colModel : [
				{name:'evaluateId',label:'主键',sortable:false}, 
				{name:'orderId',label:'订单ID',sortable:false}, 
				{name:'content',label:'评价内容',sortable:false}, 
				{name:'deliverScore',label:'配送服务',sortable:false}, 
				{name:'platformScore',label:'平台服务',sortable:false}, 
				{name:'communityId',label:'社区ID',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.evaluateId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.evaluateId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.evaluateId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		evaluateService.remove({
    			"data":{evaluateId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#evaluateList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/evaluate/edit.do?";
		if( id ){
			url = url + $.param( {evaluateId:id} );
		}
		templateform.open({
			title:"Evaluate",
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
			evaluateService.save( {
				data:$scope.evaluate,
				success:function( data ){
					if( data.success ){
						$("#evaluateList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/evaluate/show.do?";
		if( id ){
			url = url + $.param( {evaluateId:id} );
		}
		templateform.open({
			title:"Evaluate",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});