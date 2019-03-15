'use strict';

app.controller('skuSupermarketCtrl', function($scope,toaster,skuSupermarketService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				skuSupermarketId:"",
				goodsSkuId:"",
				supermarketId:"",
				price:"",
				priceType:"",
				validStart:"",
				validEnd:"",
				correctId:"",
				earningsTime:"",
				removed:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#skuSupermarketList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/skuSupermarket/list.do",
			postData:$scope.search,
			pager : "#skuSupermarketPager",
			colModel : [
				{name:'skuSupermarketId',label:'主键',sortable:false}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false}, 
				{name:'supermarketId',label:'超市ID',sortable:false}, 
				{name:'price',label:'价格',sortable:false}, 
				{name:'priceType',label:'价格类型编码',sortable:false}, 
				{name:'validStart',label:'价格有效开始时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'validEnd',label:'价格有效结束时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'correctId',label:'价格纠错ID',sortable:false}, 
				{name:'earningsTime',label:'开始发放收益时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'removed',label:'是否已删除',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.skuSupermarketId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.skuSupermarketId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.skuSupermarketId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		skuSupermarketService.remove({
    			"data":{skuSupermarketId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#skuSupermarketList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/skuSupermarket/edit.do?";
		if( id ){
			url = url + $.param( {skuSupermarketId:id} );
		}
		templateform.open({
			title:"SkuSupermarket",
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
			skuSupermarketService.save( {
				data:$scope.skuSupermarket,
				success:function( data ){
					if( data.success ){
						$("#skuSupermarketList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/skuSupermarket/show.do?";
		if( id ){
			url = url + $.param( {skuSupermarketId:id} );
		}
		templateform.open({
			title:"SkuSupermarket",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});