'use strict';

app.controller('goodsSkuCtrl', function($scope,toaster,goodsSkuService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				goodsSkuId:"",
				goodsId:"",
				goodsName:"",
				quantity:"",
				unit:"",
				barcode:"",
				skuNo:"",
				removed:"",
				buyFee:"",
				deliverFee:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#goodsSkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/goodsSku/list.do",
			postData:$scope.search,
			pager : "#goodsSkuPager",
			colModel : [
				{name:'goodsSkuId',label:'主键',sortable:false}, 
				{name:'goodsId',label:'货品ID',sortable:false}, 
				{name:'goodsName',label:'商品名称',sortable:false}, 
				{name:'quantity',label:'库存量',sortable:false}, 
				{name:'unit',label:'单位编码',sortable:false}, 
				{name:'barcode',label:'商品条码',sortable:false}, 
				{name:'skuNo',label:'货号',sortable:false}, 
				{name:'removed',label:'是否已删除',sortable:false}, 
				{name:'buyFee',label:'采买服务费',sortable:false}, 
				{name:'deliverFee',label:'配送服务费',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.goodsSkuId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.goodsSkuId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.goodsSkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		goodsSkuService.remove({
    			"data":{goodsSkuId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#goodsSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/goodsSku/edit.do?";
		if( id ){
			url = url + $.param( {goodsSkuId:id} );
		}
		templateform.open({
			title:"GoodsSku",
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
			goodsSkuService.save( {
				data:$scope.goodsSku,
				success:function( data ){
					if( data.success ){
						$("#goodsSkuList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/goodsSku/show.do?";
		if( id ){
			url = url + $.param( {goodsSkuId:id} );
		}
		templateform.open({
			title:"GoodsSku",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});