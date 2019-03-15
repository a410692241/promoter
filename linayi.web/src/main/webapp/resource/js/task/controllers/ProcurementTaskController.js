'use strict';

app.controller('procurementTaskCtrl', function($timeout,$scope,toaster,procurementTaskService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				taskId:"",
				taskNo:"",
				orderId:"",
				title:"",
				content:"",
				communityId:"",
				supermarketId:"",
				startTime:"",
				endTime:"",
				receiveTime:"",
				buyTime:"",
				finishTime:"",
				deliveryTime:"",
				serviceFee:"",
				realServiceFee:"",
				userId:"",
				status:"",
				createTime:"",
				instancy:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		if( !$scope.search.communityId ){
			return;
		}
		var $grid = $("#procurementTaskList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/task/procurementTask/list.do",
			postData:$scope.search,
			pager : "#procurementTaskPager",
			colModel : [
				{name:'communityId',label:'社区ID',sortable:false,hidden:true}, 
				{name:'taskId',label:'任务ID',sortable:false,hidden:true}, 
				{name:'taskNo',label:'任务编号',sortable:false}, 
				{name:'statusName',label:'状态',sortable:false}, 
				{name:'supermarketName',label:'采买超市',sortable:false}, 
				{name:'amount',label:'金额(元)',sortable:false,formatter:function( cellValue, options, rowObject ){
					return cellValue ? cellValue / 100 : "";
				}}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.taskId+","+rowObject.communityId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>详情</a> ";
            		return opts;
	             }}
			],
			subGrid: true,
			subGridRowExpanded: showChildGrid,
		    subGridOptions : {
				reloadOnExpand :false,
				selectOnExpand : true 
			}
		});
	}
	/**任务商品，任务跟踪*/
	 function showChildGrid(parentRowID, parentRowKey) {
		 var $parent = $('#' + parentRowID);
		 var row = $(this).jqGrid("getRowData",parentRowKey);
	     var childGridID = parentRowID + "_table";
	     var childGridPagerID = parentRowID + "_pager";
	     var $template = $( $("#template-goods-list").html() );
	     $parent.append( $template );
	     var $injector = angular.element( this ).injector();
	     var $compile = $injector.get("$compile");
	     var $scope = angular.element( $('#' + parentRowID) ).scope();
		 var $compileElm = $compile( $template )( $scope );
		 listTaskGoodsSku.apply( $compileElm,[row] );
		 listTaskGoodsSkuHist.apply( $compileElm,[row] );
	 }
	 
	 /***
	  * 获取商品操作记录
	  */
	 function listTaskGoodsSkuHist( task ){
		 var $compileElm = $( this );
		 $timeout( function(){
				var $goodsList = $compileElm.find( ".goods-list-hist" );
				if( !$goodsList.attr( "id" ) ){
					$goodsList.attr( "id",new Date().getTime() );
				}
				$goodsList.jqGrid({
					url : urls.ms+"/task/procurementSku/listTaskGoodsSkuHist.do",
					postData:{
						"taskId":task.taskId,
						"communityId":task.communityId
					},
					gridComplete:function(){
						$( this ).jqGrid("resizeGrid");
					},
					colModel : [
				            {name:'procurementSkuId',label:'主键',sortable:false,hidden:true}, 
				            {name:'goodsName',label:'商品名称',sortable:false,width:60}, 
				            {name:'statusName',label:'状态',sortable:false,width:60}, 
				            {name:'quantity',label:'数量',sortable:false,width:30}, 
				            {name:'time',label:'时间',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
				            	return cellValue ?  new Date( cellValue ).format("yyyy-MM-dd hh:mm:ss") : "";
				            }}
					]
				});
		},100 )
	 }
	 /**获取任务商品*/
	function listTaskGoodsSku( task ){
		var $compileElm = $( this );
		$timeout( function(){
			var $goodsList = $compileElm.find( ".goods-list" );
			if( !$goodsList.attr( "id" ) ){
				$goodsList.attr( "id",new Date().getTime() );
			}
			$goodsList.jqGrid({
				url : urls.ms+"/task/procurementSku/list.do",
				postData:{
					"taskId":task.taskId,
					"communityId":task.communityId
				},
				colModel : [
				            {name:'procurementSkuId',label:'主键',sortable:false,hidden:true}, 
				            {name:'goodsName',label:'商品名称',sortable:false,width:60}, 
				            {name:'quantity',label:'数量',sortable:false,width:30}, 
				            {name:'price',label:'价格(元)',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
				            	return cellValue ? cellValue / 100 : "";
				            }}
				            ]
			});
		},100 )
	}
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		procurementTaskService.remove({
    			"data":{taskId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#procurementTaskList").trigger("reloadGrid");
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
    	$.ajax({
    		url:urls.ms + "/task/procurementTask/edit.do?",
    		data:{},
    		dataType:"json",
    		type:"post"
    	}).then(function( data ){
    		openEdit( data );
    	})
    	
    	function openEdit( data ){
    		templateform.open({
    			title:"新增任务",
    			url:urls.ms + "/jsp/task/ProcurementTaskEdit.jsp",
    			scope:$scope,
    			data:data,
    			onOpen:function( $modalInstance, data ,$scope){
    				//当前登陆账号社区
    				var community = $scope.community = data.community;
    				//任务类型
    				var replenishTypeCtrl = $scope.replenishTypeCtrl={
    						replenishType:null,
    						replenishTypeList:data.replenishTypeList,
    						
    				}
    				//社区列表
    				var communityCtrl = $scope.communityCtrl={
    						communityId:null,
    						communityList:data.communityList,
    						
    				}
    				var search = $scope.search = {
    						goodsName:"",
    				}
    				//采买员
    				var userCtrl = $scope.userCtrl={
    						userId:null,
    						userList:data.userList
    				}
    				//切换社区
    				var changeCommunity = $scope.changeCommunity=function( community ){
    					communityCtrl.communityId = community.code;
    					$("#confirmOrderSkuList").jqGrid("clearGridData");
    				}
    				
    				//查询商品列表
    				$scope.listOrderSkuByCommunityId=function(){
    					if( !communityCtrl.communityId ){
    						return 	toaster.error( "","请选择指定社区",3000 );
    					}
    					listOrderSkuByCommunityId( communityCtrl.communityId,search.goodsName );
    				}
    				$scope.confirmOrderSkuList = function(){
    					confirmOrderSkuList();
    				}
    				$scope.generateTask =  function(){
    					generateTask();
    				}
    				$scope.removeConfirm = function( rowId ){
    					messager.confirm("确认删除？",function( $modalInstance ){
    						var $grid = $("#confirmOrderSkuList");
    						$grid.jqGrid("delRowData",rowId);
    						$modalInstance.close();
    					})
    				}
    			}
    		},function( $modalInstance,data, $scope ){
    			save( $modalInstance,data, $scope );
    		});
    	}
    }
    
    /**
     * 生成任务
     * */
    function generateTask(){
    	
    }
    
    /**选择添加*/
    function confirmOrderSkuList(){
    	var rowData = $("#orderSkuList").jqGrid('getRowData');
		var rowIndexArr =  $("#orderSkuList").jqGrid('getGridParam', 'selarrrow');
		var selectedRows = [];
		for( var i = 0 ; i < rowIndexArr.length; i++ ){
			selectedRows.push( rowData[rowIndexArr[i]] );
		}
		var totalCount = 0;
		var $grid = $("#confirmOrderSkuList");
		if( $grid[0].grid ){
			var confirmRows = $grid.jqGrid('getRowData');
			totalCount = confirmRows.length;
			lable:
			for( var j = 0 ; j < selectedRows.length; j++ ){
				for( var i = 0 ; i < confirmRows.length; i++ ){
					if( rowData[j].orderSkuId == confirmRows[i].orderSkuId ){
						continue lable;
					}
				}
				$grid.jqGrid('addRowData',totalCount++,selectedRows[j]);
			}
		}else{
			$grid.jqGrid({
				autoCompile:false,
				datatype: 'local',
				editurl: 'clientArray',
				afterInsertRow:function( rowId, rowData){
					var _this = this
					angular.element( _this ).injector().invoke(function( $compile ) {
						$compile( $( _this ).find( ".td-compile:last" ) )( angular.element( _this ).scope() );
					});
				},
				colModel : [
					{name:'orderSkuId',label:'主键',sortable:false,hidden:true}, 
					{name:'supermarketId',label:'超市ID',sortable:false,hidden:true}, 
					{name:'orderId',label:'订单ID',sortable:false,hidden:true}, 
					{name:'goodsSkuId',label:'商品ID',sortable:false,hidden:true}, 
					{name:'serviceFee',label:'服务费',sortable:false,hidden:true}, 
					{name:'nickname',label:'顾客',sortable:false}, 
					{name:'createTime',label:'下单时间',sortable:false,formatter:function( cellValue, rowIndex, rowObject ){
						return cellValue ? new Date( cellValue ).format("yyyy-MM-dd hh:mm:ss") : "";
					}}, 
					{name:'goodsName',label:'商品',sortable:false}, 
					{name:'supermarketName',label:'超市',sortable:false}, 
					{name:'price',label:'单价',sortable:false}, 
					{name:'quantity',label:'采购数量',sortable:false, editable:true,edittype:"text",editoptions:{
						dataEvents:[{
							type:"blur",
							fn:function( e ){
								var ids = $grid.jqGrid('getDataIDs');
								console.info( ids );
					            for (var i = 0; i < ids.length; i++) {
					            	$grid.jqGrid('saveRow', ids[i]);
					            }
							}
						}]
					}}, 
					{name:'opt',label:'操作',sortable:false,formatter:function( cellValue, row, rowObject ){
						return 	"<a href='javascript:void(0);' ng-click='removeConfirm( "+row.rowId+")' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
					}}
				],
				ondblClickRow:function(rowId, rowIndex, cellIndex){
					if( cellIndex == 11 ){
						$( this ).jqGrid("editRow", rowId, true );
					}
				}
			});
			for( var i = 0 ; i < selectedRows.length; i++ ){
				$grid.jqGrid('addRowData',totalCount++,selectedRows[i]);
			}
		}
    }
    
    /**订单商品列表查询*/
    function listOrderSkuByCommunityId( communityId,goodsName ){
    	var $grid = $("#orderSkuList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:{"communityId":communityId,"goodsName":goodsName},
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/order/orderSku/listOrderSkuByCommunityId.do",
			postData:{"communityId":communityId},
			pager : "#orderSkuPager",
			multiselect :true,
			colModel : [
				{name:'orderSkuId',label:'主键',sortable:false,hidden:true}, 
				{name:'orderId',label:'订单ID',sortable:false,hidden:true}, 
				{name:'supermarketId',label:'超市ID',sortable:false,hidden:true}, 
				{name:'goodsSkuId',label:'商品ID',sortable:false,hidden:true}, 
				{name:'serviceFee',label:'服务费',sortable:false,hidden:true}, 
				{name:'nickname',label:'顾客',sortable:false}, 
				{name:'createTime',label:'下单时间',sortable:false,formatter:function( cellValue, rowIndex, rowObject ){
					return cellValue ? new Date( cellValue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}}, 
				{name:'goodsName',label:'商品',sortable:false}, 
				{name:'supermarketName',label:'超市',sortable:false}, 
				{name:'price',label:'单价',sortable:false,formatter:function( cellValue, rowIndex, rowObject ){
					return cellValue ? cellValue / 100 : "";
				}}, 
				{name:'quantity',label:'采购数量',sortable:false }
			]
		});
    }
    
	/**保存*/
    function save( $modalInstance,data, $scope ){
    	var getConfirmRows = $("#orderSkuList").jqGrid('getRowData');
    	var selarrrow = $("#orderSkuList").jqGrid('getGridParam','selarrrow');
    	var confirmRows = new Array();
    	for(var i  in  selarrrow){
    		confirmRows.push(getConfirmRows[i]);
    	}
    	
    	if( selarrrow == '' ){
    		return toaster.error( "","请选择商品！",3000 );
    	}
    	for( var index in confirmRows ){
    		confirmRows[index].opt = "";
    	}
    	try{
			procurementTaskService.save( {
				data:JSON.stringify({
					communityId:$scope.communityCtrl.communityId,
					orderSkuList:(function(){
						var result = new Array();
						for( var i = 0; i  < confirmRows.length; i ++ ){
							result.push({
								goodsSkuId:confirmRows[i].goodsSkuId,
								orderId:confirmRows[i].orderId,
								quantity:confirmRows[i].quantity,
								serviceFee:confirmRows[i].serviceFee,
								price:confirmRows[i].price*100
							});
						}
						return result;
					})()
				}),
				success:function( data ){
					if( data.success ){
						$("#procurementTaskList").trigger("reloadGrid");
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
    function show( id,communityId ){
    	var url = urls.ms + "/task/procurementTask/show.do?";
		if( id ){
			url = url + $.param( {taskId:id,communityId:communityId} );
		}
		templateform.open({
			title:"任务详情",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});