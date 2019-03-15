'use strict';

app.controller('procurementSkuCtrl', function($scope,toaster,procurementSkuService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				procurementSkuId:"",
				taskId:"",
				goodsSkuId:"",
				price:"",
				quantity:"",
				buyQuantity:"",
				buyOption:"",
				buyPrice:"",
				buyPriceImage:"",
				receiveQuantity:"",
				receiveOption:"",
				receiveSite:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#procurementSkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/task/procurementSku/list.do",
			postData:$scope.search,
			pager : "#procurementSkuPager",
			colModel : [
				{name:'procurementSkuId',label:'主键',sortable:false}, 
				{name:'taskId',label:'任务ID',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'price',label:'价格',sortable:false}, 
				{name:'quantity',label:'总需购买商品数量',sortable:false}, 
				{name:'buyQuantity',label:'数量',sortable:false}, 
				{name:'buyOption',label:'买手操作',sortable:false}, 
				{name:'buyPrice',label:'真实价格',sortable:false}, 
				{name:'buyPriceImage',label:'价高图片',sortable:false}, 
				{name:'receiveQuantity',label:'验货数量',sortable:false}, 
				{name:'receiveOption',label:'验货操作',sortable:false}, 
				{name:'receiveSite',label:'位置编号',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.procurementSkuId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.procurementSkuId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.procurementSkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		procurementSkuService.remove({
    			"data":{procurementSkuId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#procurementSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/task/procurementSku/edit.do?";
		if( id ){
			url = url + $.param( {procurementSkuId:id} );
		}
		templateform.open({
			title:"ProcurementSku",
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
			procurementSkuService.save( {
				data:$scope.procurementSku,
				success:function( data ){
					if( data.success ){
						$("#procurementSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/task/procurementSku/show.do?";
		if( id ){
			url = url + $.param( {procurementSkuId:id} );
		}
		templateform.open({
			title:"ProcurementSku",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});