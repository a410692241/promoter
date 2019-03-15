'use strict';

app.controller('afterSaleCtrl', function($scope,toaster,afterSaleService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				afterSaleId:"",
				orderSkuId:"",
				type:"",
				cause:"",
				content:"",
				amount:"",
				processStatus:"",
				processCause:"",
				processContent:"",
				processTime:"",
				processUser:"",
				createTime:"",
				communityId:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#afterSaleList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/order/afterSale/list.do",
			postData:$scope.search,
			pager : "#afterSalePager",
			colModel : [
				{name:'afterSaleId',label:'主键',sortable:false}, 
				{name:'orderSkuId',label:'订单-商品ID',sortable:false}, 
				{name:'type',label:'售后类型编码',sortable:false}, 
				{name:'cause',label:'售后原因',sortable:false}, 
				{name:'content',label:'售后说明',sortable:false}, 
				{name:'amount',label:'实际退款金额',sortable:false}, 
				{name:'processStatus',label:'处理状态',sortable:false}, 
				{name:'processCause',label:'处理原因',sortable:false}, 
				{name:'processContent',label:'处理说明',sortable:false}, 
				{name:'processTime',label:'处理时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'processUser',label:'处理人',sortable:false}, 
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'communityId',label:'社区ID',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.afterSaleId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.afterSaleId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.afterSaleId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		afterSaleService.remove({
    			"data":{afterSaleId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#afterSaleList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/afterSale/edit.do?";
		if( id ){
			url = url + $.param( {afterSaleId:id} );
		}
		templateform.open({
			title:"AfterSale",
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
			afterSaleService.save( {
				data:$scope.afterSale,
				success:function( data ){
					if( data.success ){
						$("#afterSaleList").trigger("reloadGrid");
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
    	var url = urls.ms + "/order/afterSale/show.do?";
		if( id ){
			url = url + $.param( {afterSaleId:id} );
		}
		templateform.open({
			title:"AfterSale",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});