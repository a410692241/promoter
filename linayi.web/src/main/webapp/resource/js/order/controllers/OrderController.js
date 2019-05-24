'use strict';

app.controller('orderCtrl', function($scope,toaster,orderService,messager,templateform, $timeout ) {

	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
        $scope.binding = binding;
		$scope.search={
				communityId:"",
				ordersId:"",
				mobile:"",
				userStatus:"",
				communityStatus:"",
				createTimeStart:"",
				createTimeEnd:"",
				supermarketId:""
		}
		$scope.list();
	}

	/** 列表查询 */
	function list(){
		var $grid = $("#orderList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,

			}).trigger("reloadGrid");
			return;
		}

		$grid.jqGrid({
			url : urls.ms+"/order/order/orderList.do",
			postData:$scope.search,
			pager : "#orderPager",
			colModel : [
				{name:'ordersId',label:'订单号',sortable:false},
				{name:'communityId',label:'社区ID',sortable:false,hidden:true},
				{name:'receiverName',label:'下单人',sortable:false},
				// {name:'goodsSkuId',label:'商品订单id',sortable:false},
				{name:'amount',label:'订单金额(元)',sortable:false,formatter:function( value, row, rowObject ){
					return value / 100;
				}},
				{name:'orderGoodsTotalPrice',label:'实际采买总额(元)',sortable:false,formatter:function( value, row, rowObject ){
						return value / 100;
					}},
				{name:'communityStatus',label:'订单流转状态',sortable:false,formatter:function( value, row, rowObject ){
					 switch (value) {
						 case 'PROCURING':
							 return '采买中';
							 break;
						 case 'DELIVERING':
							 return '配送中';
							 break;
						 case 'PROCURE_FINISHED':
							 return '采买完成';
							 break;
						 case 'RECEIVED':
							 return '已收货(社区网点)';
							 break;
							 case 'CANCELED':
							 return '已取消';
							 break;
						 case 'PACKED':
							 return '已装箱';
							 break;
						 case 'DELIVER_FINISHED':
							 return '配送完成';
							 break;
					default:
						break;
					}
					}},
				{name:'userStatus',label:'订单用户端状态',sortable:false,formatter:function( value, row, rowObject ){
						switch (value) {
							case 'IN_PROGRESS':
								return '待收货';
								break;
							case 'CANCELED':
								return '已取消';
								break;
							case 'CONFIRM_RECEIVE':
								return '确认收货';
								break;
							case 'FINISHED':
								return '已完成';
								break;
							default:
								break;
						}
					}},
				{name:'mobile',label:'手机号码',sortable:false},
				{name:'address',label:'送货地址',sortable:false},
				{name:'remark',label:'备注',sortable:false},
				{name:'createTime',label:'下单时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'communityName',label:'所属社区',sortable:false},
                {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
						var opts = "";
						if(rowObject.userStatus != 'CANCELED'){
							opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.ordersId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>修改状态</a> ";
						}
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
	// var parentId;
	// var parentRow;


	/** 订单商品，订单跟踪 */
	 function showChildGrid(parentRowID, parentRowKey) {
		// parentId = parentRowID;
		// parentRow = parentRowKey;
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

	 /** 订单商品 */
	function listOrderSkuList( order ){
		var $compileElm = $( this );
		$timeout( function(){
			var $goodsList = $compileElm.find( ".goods-list" );
			if( !$goodsList.attr( "id" ) ){
				$goodsList.attr( "id",new Date().getTime() );
			}
			var date =new Date();
			var longTime = date.getTime();
			$goodsList.jqGrid({
				url : urls.ms+"/order/orderSku/list.do?longTime=" + longTime,
				async:false,
				postData:{
					"ordersId":order.ordersId
				},
				editurl: 'clientArray',
				colModel : [
		            {name:'ordersGoodsId',label:'订单商品编号',sortable:false,width:20},
		            {name:'communityId',label:'社区ID',sortable:false,hidden:true},
					{name:'image',label:'商品图片',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
							return "<img src="+cellValue+" height=60 width=100>";
						}},
		            {name:'fullName',label:'商品',sortable:false,width:30},
		            {name:'quantity',label:'下单数量',sortable:false,width:30},
		            {name:'procureQuantity',label:'已采买总数量',sortable:false,width:30},
		            {name:'supermarketName',label:'当前采买超市',sortable:false,width:30},
		            {name:'price',label:'当前超市采买价格(元)',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
		            	return cellValue ? cellValue / 100 : "";
		            },editable:true,edittype:"text"},
		            // {name:'quantity',label:'数量',sortable:false,width:30},
		            // {name:'procureQuantity',label:'采买数量',sortable:false,width:30},
		            {name:'procureStatus',label:'采买状态',sortable:false,width:30,formatter:function(value,row,rowObject){
		            	switch (value) {
							// case 'WAIT_PROCURE':
							// 	return '待采买';
							// 	break;
							// case 'NO_GOODS':
							// 	return '缺货';
							case 'PRICE_HIGH':
								return '价高未买';
							case 'CANCELED':
								return '已取消';
							// case 'WAIT_DEAL':
							// 	return '待处理';
							case 'LACK':
								return '缺货';
							case 'PROCURING':
								return '采买中';
							case 'FINISHED':
								return '采买结束';
							default:
								return '';
								break;
						}
		            }},
		            {name:'createTime',label:'下单时间',sortable:false,width:30,formatter:function(value,row,rowObject){
		            	return new Date(value).format('yyyy-MM-dd HH:mm:ss')
		            }},
					{label:"操作",name:"opt",width:40,sortable:false,formatter:function(cellvalue, options, rowObject){
							var opts = "";
							/*	if( rowObject.status == 1 || rowObject.status==2 ){
                                    opts = opts + "<a href='javascript:void(0);' ng-click='changeStatus( "+rowObject.goodsId+","+3+")' class='btn btn-primary fa fa-eye btn-sm td-compile'>上架</a> ";
                                }else{
                                    opts = opts + "<a href='javascript:void(0);' ng-click='changeStatus( "+rowObject.goodsId+","+2+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>下架</a> ";
                                }
                                opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.goodsId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";*/
							opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.procurementTaskId+" )' class='btn btn-primary fa fa-show btn-sm td-compile'>查看全部超市</a> ";
							return opts;
						}}
		            // {name:'opt',label:'操作',sortable:false,width:30,formatter:function( value, option, rowObject ){
		            // 	console.info( rowObject );
		            // 	return '<a href="javascript:void(0);" onclick="modifyPrice( this,'+option.rowId+","+option.pos+' )" class="btn btn-primary fa fa-edit btn-sm td-compile">改价</a>';
		            // }},
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

    /**
     * 改状态
     * @param communityLocationId
     *
     */
    function edit( ordersId){
            openEdit( ordersId);
    }

    function openEdit( ordersId){
        templateform.open({
            title:"编辑",
            url:urls.ms + "/jsp/order/OrderStatus.jsp",
            scope:$scope,
            data:ordersId,
            onOpen:function( data ,$scope) {
			}
        },function( $modalInstance,data,$scope){
            save( $modalInstance,ordersId, $scope);
        });
    }



    /**保存*/
    function save( $modalInstance, ordersId, $scope){
		messager.confirm("确认修改？",function( $modalInstance1 ){
         try{
			 var data = {ordersId: ordersId,userStatus:$scope.search.userStatus};
			 $.ajax({
				 url:urls.ms+"/smallCommunity/saveOrders.do",
				 data :data,
				 dataType : "json",
				 type : "post",
				 // 设置请求头信息
				 success:function( data ){
					 $scope.$apply(function(){
						 if( data.respCode == "S" ){
							 $("#communityLocationList").trigger("reloadGrid");
							 $scope.search.userStatus = '';
							 list();
							 $modalInstance.close();
							 $modalInstance1.close();
						 }else{
							 toaster.error( "",data.data,3000 );
						 }
					 });
				 }
			 })

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
        }catch (e) {
            toaster.error( "",typeof e == "string" ? //
                e : e.msg ? //
                    e.msg : "出错了",3000 );
        }
		});
	}
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


    /** 删除 */
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
    var supermarketId;
    /** 查看 */
    function show( procurementTaskId) {
		var url = urls.ms + "/order/orderSku/orderSupermarketList.do?";
		if (procurementTaskId) {
			url = url + $.param({procurementTaskId: procurementTaskId});
		}
		$.ajax({
			url: urls.ms + "/order/orderSku/orderGoodsIsDeal.do",
			async: false,
			type: "post",
			data: {"procurementTaskId": procurementTaskId},
			dataType: "JSON",
			success: function (data) {
				$scope.procurement={
					ordersId:data.ordersId
				}
				var procurementTaskId = data.procurementTaskId;

				if (data.procureStatus == "FINISHED") {
					templateform.open({
						title: "超市比价",
						backdrop: true,
						keyboard: true,
						url: url,
						scope: $scope,
						buttons: [{
							text: "继续购买",
							iconCls: 'fa fa-check',
							handle: function ($modalInstance, data, $scope) {
								$.ajax({
									url: urls.ms + "/order/orderSku/buySecondHeigh.do",
									async: false,
									type: "post",
									data: {"procurementTaskId": procurementTaskId,"supermarketId":supermarketId},
									dataType: "JSON",
									success: function (data) {
										$("#correctList").trigger("reloadGrid");
										list();
										$modalInstance.close();
										if (data.data == "success"){
											toaster.success("", "操作成功", 3000);
										} else if(data.data == "no_supermarketId"){
											toaster.error( "","请选择超市",3000 );
										} else {
											toaster.error( "","操作失败",3000 );
										}

									}
								})
							}
						}, {
							text: "取消购买",
							iconCls: 'fa fa-times',
							handle: function ($modalInstance, data, $scope) {
								$.ajax({
									url: urls.ms + "/order/orderSku/cancelBuy.do",
									async: false,
									type: "post",
									data: {"procurementTaskId": procurementTaskId},
									dataType: "JSON",
									success: function (data) {
										$("#correctList").trigger("reloadGrid");
										// listOrderSkuList(data);
										// showChildGrid(parentId, parentRow);
										$modalInstance.close();
										if (data.data == "success"){
											toaster.success("", "操作成功", 3000);
										} else {
											toaster.error( "","操作失败",3000 );
										}
									}
								})
							}
						}
						],
						onOpen: function ($modalInstance, data, $scope) {
						}
					});
				} else {
					templateform.open({
						title: "超市比价",
						backdrop: true,
						keyboard: true,
						url: url,
						scope: $scope,
						buttons: [],
						onOpen: function ($modalInstance, data, $scope) {
						}
					});
				}
			}
		});

	}

    function binding(){
        supermarketId = $("#supermarketId").val();
    }

    // 初始化
    init();
});
