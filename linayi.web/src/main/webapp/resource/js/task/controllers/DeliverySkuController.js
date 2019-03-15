'use strict';

app.controller('deliverySkuCtrl', function($scope,toaster,deliverySkuService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				deliverySkuId:"",
				taskId:"",
				goodsSkuId:"",
				quantity:"",
				option:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#deliverySkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/task/deliverySku/list.do",
			postData:$scope.search,
			pager : "#deliverySkuPager",
			colModel : [
				{name:'deliverySkuId',label:'主键',sortable:false}, 
				{name:'taskId',label:'任务ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'quantity',label:'数量',sortable:false}, 
				{name:'option',label:'操作',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.deliverySkuId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.deliverySkuId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.deliverySkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		deliverySkuService.remove({
    			"data":{deliverySkuId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#deliverySkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/task/deliverySku/edit.do?";
		if( id ){
			url = url + $.param( {deliverySkuId:id} );
		}
		templateform.open({
			title:"DeliverySku",
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
			deliverySkuService.save( {
				data:$scope.deliverySku,
				success:function( data ){
					if( data.success ){
						$("#deliverySkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/task/deliverySku/show.do?";
		if( id ){
			url = url + $.param( {deliverySkuId:id} );
		}
		templateform.open({
			title:"DeliverySku",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});