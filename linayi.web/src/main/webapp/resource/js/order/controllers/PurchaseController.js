'use strict';

app.controller('purchaseCtrl', function($scope,toaster,purchaseService,messager,templateform, $timeout ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				communityId:"",
				ordersId:"",
				receiveOption:"",
				supermarketName:"",
				// createTime:"",
				quantity:"",
				communityStatus:""
		}
		$scope.list();
	}
	
	/** 列表查询 */
	function list(){
		var $grid = $("#purchaseList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
				
			}).trigger("reloadGrid");
			return;
		}
		
		$grid.jqGrid({
			url : urls.ms+"/order/orderSku/pList.do",
			postData:$scope.search,
			pager : "#purchasePager",
			colModel : [
				{name:'procurementTaskId',label:'采买任务编号',sortable:false,width:20},
				{name:'ordersId',label:'订单编号',sortable:false,width:20},
				{name:'goodsSkuId',label:'订单编号',sortable:false,width:20,hidden:true},
				{name:'communityId',label:'社区ID',sortable:false,hidden:true},
				{name:'goodsSkuName',label:'商品',sortable:false,width:40},
				// {name:'receiveOption',label:'收付状态',sortable:false,width:30,formatter:function(value,row,rowObject){
				// 		switch (value) {
				// 			case 10:
				// 				return '已收';
				// 				break;
				// 			case 20:
				// 				return '拒收';
				// 			default:
				// 				return '';
				// 				break;
				// 		}
				// 	}},
				{name:'price',label:'超市价格(元)',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
						return cellValue ? cellValue / 100 : "";
					},editable:true,edittype:"text"},
				{name:'supermarketName',label:'超市',sortable:false,width:30},
				{name:'createTime',label:'下单时间',sortable:false,width:30,formatter:function(value,row,rowObject){
						return new Date(value).format('yyyy-MM-dd HH:mm:ss')
					}},
				{name:'quantity',label:'数量',sortable:false,width:30},
				{name:'procureStatus',label:'采买状态',sortable:false,width:20,formatter:function(value,row,rowObject){
						switch (value) {
							case 'PROCURING':
								return '商品采买中';
								break;
							case 'LACK':
								return '缺货';
								break;
							case 'PRICE_HIGH':
								return '价高未买';
								break;
							case 'BOUGHT':
								return '已买';
								break;
							default:
								return '';
								break;
						}
					}},
				// {name:'opt',label:'操作',sortable:false,width:30,formatter:function( value, option, rowObject ){
				// 	console.info( rowObject );
				// 	return '<a href="javascript:void(0);" onclick="modifyPrice( this,'+option.rowId+","+option.pos+' )" class="btn btn-primary fa fa-edit btn-sm td-compile">改价</a>';
				// }},
			],
			subGrid: true,
			subGridRowExpanded: showChildGrid,
			subGridOptions : {
				reloadOnExpand :false,
				selectOnExpand : true
			}
		});
	}

	// /** 订单商品，订单跟踪 */
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
		listOrderSkuList.apply( $compileElm,[row] );
	}

	//  /** 订单商品 */
	function listOrderSkuList( order ){
		var $compileElm = $( this );
		$timeout( function(){
			var $goodsList = $compileElm.find( ".goods-list" );
			if( !$goodsList.attr( "id" ) ){
				$goodsList.attr( "id",new Date().getTime() );
			}
			$goodsList.jqGrid({
				url : urls.ms+"/order/orderSku/sPList.do",
				postData:{
					"ordersId":order.ordersId,
					"goodsSkuId":order.goodsSkuId
				},
				colModel : [
		            {name:'supermarketName',label:'超市名称',sortable:false,width:30},

		            {name:'price',label:'超市价格(元)',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
		            	return cellValue ? cellValue / 100 : "";
		            },editable:true,edittype:"text"},
					{name:'procureStatus',label:'状态',sortable:false,width:40,formatter:function(value,row,rowObject){
							switch (value) {
								// case 'WAIT_PROCURE':
								// 	return '待采买';
								// 	break;
								case 'LACK':
									return '缺货';
									break;
								case 'PRICE_HIGH':
									return '价高未买';
									break;
								case 'BOUGHT':
									return '已买';
									break;
								case 'PROCURING':
									return '采买中';
									break;
								// case 'FINISHED':
								// 	return '采买完成';
								default:
									return '';
									break;
							}
						}},

				]
			});
			$goodsList.bind("jqGridInlineAfterSaveRow", function ( e, rowid, resp, tmp, o ) {
				var price = arguments[3].price;
				console.info( price );
				var rowData = $goodsList.jqGrid("getRowData", rowid);
				rowData.price = price * 100 ;
				$goodsList.jqGrid( "setRowData", rowid, rowData );
			});
		},100 )
	}


    // /**
    //  * 改状态
    //  * @param communityLocationId
    //  *
    //  */
    function edit( ordersId){
            openEdit( ordersId);
    }
	//
    // function openEdit( ordersId ){
    //     templateform.open({
    //         title:"编辑",
    //         url:urls.ms + "/jsp/order/OrderStatus.jsp",
    //         scope:$scope,
    //         data:ordersId,
    //         onOpen:function( data ,$scope) {
	// 		}
    //     },function( $modalInstance,data,$scope ){
    //         save( $modalInstance,ordersId, $scope );
    //     });
    // }



    // /**保存*/
    // function save( $modalInstance, ordersId, $scope ){
    //      try{
	// 		 var data = {ordersId: ordersId,userStatus:$scope.search.userStatus};
	// 		 $.ajax({
	// 			 url:urls.ms+"/smallCommunity/saveOrders.do",
	// 			 data :data,
	// 			 dataType : "json",
	// 			 type : "post",
	// 			 // 设置请求头信息
	// 			 success:function( data ){
	// 				 $scope.$apply(function(){
	// 					 if( data.respCode == "S" ){
	// 						 $("#communityLocationList").trigger("reloadGrid");
	// 						 $scope.search.userStatus = '';
	// 						 list();
	// 						 $modalInstance.close();
	// 					 }else{
	// 						 toaster.error( "",data.data,3000 );
	// 					 }
	// 				 });
	// 			 }
	// 		 })

            // $.ajax({
            //     url:urls.ms+"/smallCommunity/saveOrders.do",
            //     data:{
            //         ordersId:data
            //     },
            //     dataType:"json",
            //     type:"post",
            //     success:function( data ){
            //         $scope.$apply(function(){
            //             if( data.respCode == "S" ){
            //                 $("#communityLocationList").trigger("reloadGrid");
            //                 $modalInstance.close();
            //             }else{
            //                 toaster.error( "",data.msg,3000 );
            //             }
            //         });
            //     }
            // })
        // }catch (e) {
        //     toaster.error( "",typeof e == "string" ? //
        //         e : e.msg ? //
        //             e.msg : "出错了",3000 );
        // }
    //}

	// /**
	//  * 改价
	//  */
	// window.modifyPrice = function modifyPrice( obj, rowId, colIndex ){
	// 	var text = $( obj ).text();
	// 	var $goodsList = $( obj ).closest("table");
	// 	if( text == "改价" ){
	// 		var rowData = $goodsList.jqGrid("getRowData", rowId);
	// 		$goodsList.jqGrid("editRow", rowId, true );
	// 		$( obj ).text("保存");
	// 		$( obj ).data("_rowData",rowData );
	// 	}else{
	// 		var rowData = $goodsList.jqGrid("getRowData", rowId);
	// 		rowData.price = $("#"+$( rowData.price ).attr("id") ).val() * 100;
	// 		$.ajax({
	// 			url:urls.ms+"/order/orderSku/modifyPrice.do",
	// 			data:{
	// 				orderSkuId : rowData.orderSkuId,
	// 				price : rowData.price ,
	// 				communityId : rowData.communityId
	// 			},
	// 			dataType:"json",
	// 			type:"post",
	// 			success:function( data ){
	// 				$scope.$apply(function(){
	// 					if( data.success ){
	// 						toaster.success( "", data.msg, 3000);
	// 						$goodsList.jqGrid('saveRow', rowId);
	// 						$( obj ).text("改价");
	// 					}else{
	// 						toaster.error( "", data.msg, 3000);
	// 						rowData.price = $( obj ).data( "_rowData" ).price * 100;
	// 						$goodsList.jqGrid('saveRow', rowId);
	// 						$goodsList.jqGrid( "setRowData", rowId, rowData );
	// 					}
	// 					list();
	// 				});
	// 			}
	// 		})
	// 	}
	//  }


    // /** 删除 */
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		orderService.remove({
    			"data":{orderId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#orderList").trigger("reloadGrid");
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
    
    // /** 新增，编辑 */
    // function edit( id ){
    // 	var url = urls.ms + "/order/order/edit.do?";
	// 	if( id ){
	// 		url = url + $.param( {orderId:id} );
	// 	}
	// 	templateform.open({
	// 		title:"Order",
	// 		url:url,
	// 		scope:$scope,
	// 		onOpen:function( $modalInstance, data ,$scope){
	//
	// 		}
	// 	},function( $modalInstance,data, $scope ){
	// 		save( $modalInstance,data, $scope );
	// 	});
    // }
    
	// /** 保存 */
    // function save( $modalInstance,data, $scope ){
    // 	try{
	// 		orderService.save( {
	// 			data:$scope.order,
	// 			success:function( data ){
	// 				if( data.success ){
	// 					$("#orderList").trigger("reloadGrid");
	// 					$modalInstance.close();
	// 				}else{
	// 					$scope.$apply(function(){
	// 						toaster.error( "",data.msg,3000 );
	// 					});
	// 				}
	// 			}
	// 		} )
	// 	}catch (e) {
	// 		console.error( e );
	// 		toaster.error( "",typeof e == "string" ? //
	// 				e : e.msg ? //
	// 						e.msg : "出错了",3000 );
	// 	}
    // }
    
    // /** 查看 */
    function show( id ){
    	var url = urls.ms + "/order/order/show.do?";
		if( id ){
			url = url + $.param( {orderId:id} );
		}
		templateform.open({
			title:"Order",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }

    // 初始化
    init();
});