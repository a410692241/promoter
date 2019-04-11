'use strict';

app.controller('communitySupermarketCtrl', function($scope,toaster,communitySupermarketService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
//		$scope.bindSupermarket = bindSupermarket;
		$scope.replaceCommunity = replaceCommunity;
		$scope.search={
				communityId:$scope.communityId,
				name:"",
				startTime:"",
				endTime:"",
		}
		$scope.list();
		$scope.bind = bind;
		$scope.unbind = unbind;
	}
	   /**根据选定的状态获取超市*/
   
	/**列表查询*/
	function list(){
		var $grid = $("#communitySupermarketList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/community/supermarket/list.do",
			postData:$scope.search,
			pager : "#communitySupermarketPager",
			colModel : [
				{name:'supermarketId',label:'超市ID',sortable:false,hidden:true}, 
				{name:'name',label:'名称',sortable:false}, 
				{name:'areaName',label:'地址',sortable:false}, 
				{name:'address',label:'详细地址',sortable:false},
				{name:'type',label:'绑定类型',sortable:false,formatter:function(cellvalue, options, rowObject){
					if(cellvalue == "bind"){
						return cellvalue = "绑定";
					}else{
						return cellvalue = "未绑定";
					}
				}},
				{name:'rank',label:'超市排序',sortable:false},
//				formatter:function( cellvalue, options, rowObject ){
//					if( rowObject.type == 10 ){
//						return "负责超市";
//					}else if( rowObject.type == 20 || rowObject.type == 20 ){
//						return "比价超市";
//					}else{
//						return "未绑定";
//					}
//			}}, 
				{name:'createTime',label:'创建时间',sortable:false,formatter:function(cellvalue, options, rowObject){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}}, 
//				{name:'bind',label:'是否绑定过负责超市',width: 110, align:"center",sortable:false,formatter:function(cellvalue, options, rowObject){
//					return cellvalue ? "是" : "否";
//				}}, 
	            {label:"操作",name:"opt",width:170,align:"center",sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
//					if( rowObject.type == 10 ){
//						opts = opts + "<a href='javascript:void(0);' ng-click='replaceCommunity( "+rowObject.supermarketId+",50 )' class='btn btn-warning fa fa-eye btn-sm td-compile'>换绑负责社区</a> ";
//					}
//					if( rowObject.type == 20 ){
//						opts = opts + "<a href='javascript:void(0);' ng-click='bindSupermarket( "+rowObject.supermarketId+",40 )' class='btn btn-danger fa fa-remove btn-sm td-compile'>解除社区比价超市</a> ";
//					}
//					if( rowObject.type == 30 ){
//						opts = opts + "<a href='javascript:void(0);' ng-click='bindSupermarket( "+rowObject.supermarketId+",20 )' class='btn btn-success fa fa-eye btn-sm td-compile'>绑为社区比价超市</a> ";
//						opts = opts + "<a href='javascript:void(0);' ng-click='bindSupermarket( "+rowObject.supermarketId+",10 )' class='btn btn-primary fa fa-eye btn-sm td-compile'>绑为社区负责超市</a> ";
//					}
//					if( rowObject.type == 40 ){
//						opts = opts + "<a href='javascript:void(0);' ng-click='bindSupermarket( "+rowObject.supermarketId+",40 )' class='btn btn-danger fa fa-eye btn-sm td-compile'>解除社区比价超市</a> ";
//					}
//					if( rowObject.type == 50 ){
//						opts = opts + "<a href='javascript:void(0);' ng-click='bindSupermarket( "+rowObject.supermarketId+",20 )' class='btn btn-success fa fa-eye btn-sm td-compile'>绑为社区比价超市</a> ";
//						opts = opts + "<a href='javascript:void(0);' ng-click='bindSupermarket( "+rowObject.supermarketId+",10 )' class='btn btn-primary fa fa-eye btn-sm td-compile'>绑为社区负责超市</a> ";
//					}
					if(rowObject.type =="bind"){
						opts = opts + "<a href='javascript:void(0);' ng-click='unbind( "+rowObject.supermarketId+")' class='btn btn-warning fa fa-eye btn-sm td-compile'>解除绑定</a> ";
					}else{
						opts = opts + "<a href='javascript:void(0);' ng-click='bind( "+rowObject.supermarketId+")' class='btn btn-warning fa fa-eye btn-sm td-compile'>绑定超市</a> ";
					}
					return opts;
	             }}
			]
		});
	}
	 /**绑定超市*/
    function bind(supermarketId){
    	messager.confirm("确认绑定该超市？",function( $modalInstance ){
    		$.ajax({
    			url:urls.ms+"/communitySupermarket/bind.do",
    			data:{
    				supermarketId:supermarketId,
    				communityId:$scope.communityId,
    			},
    			dataType:"json",
    			type:"post",
    			success:function( data ){
	    				if( data.respCode == "S" ){
	    					list();
	    					$scope.$apply(function(){
	    						$modalInstance.close();
							});
						}else{
							$scope.$apply(function(){
	    						$modalInstance.close();
							});
							toaster.error( "",data.data,3000 );
						}
    			}
    		});
    	});
    }
    /**解除绑定超市*/
    function unbind(supermarketId){
    	messager.confirm("确认解绑该超市？",function( $modalInstance ){
    		$.ajax({
    			url:urls.ms+"/communitySupermarket/unbind.do",
    			data:{
    				supermarketId:supermarketId,
    				communityId:$scope.communityId,
    			},
    			dataType:"json",
    			type:"post",
    			success:function( data ){
	    				if( data.respCode == "S" ){
	    					list();
	    					$scope.$apply(function(){
	    						$modalInstance.close();
							});
						}else{
							$scope.$apply(function(){
	    						$modalInstance.close();
							});
							toaster.error( "",data.data,3000 );
						}
    			}
    		});
    	});
    }
	function replaceCommunity(supermarketId, type ){
		//弹出社区框
		var url = urls.ms + "/community/communitySupermarket/selectCommunity.do?";
		templateform.open({
			title:"选择社区",
			url:url,
			data:{"supermarketId":supermarketId},
			scope:$scope,
			onOpen:function( $modalInstance, data ,$scope){
				
			}
		},function( $modalInstance, data ,$scope ){
			var communityId = $scope.communitySupermarket.community.code;
			bindSupermarket( data.supermarketId, 50,communityId );
			$modalInstance.close();
		});
	}
	
//	/**绑定超市,解除绑定*/
//	function bindSupermarket( supermarketId, type, communityId ){
//		if( type == 10 || type == 20){
//			messager.confirm("确认绑定？",function( $modalInstance ){
//				postRequest(supermarketId, type,null,$modalInstance);
//			})
//		}else if( type == 40){
//			messager.confirm("确认解除绑定？",function( $modalInstance ){
//				postRequest(supermarketId, type,null,$modalInstance);
//			})
//		}else{
//			postRequest(supermarketId, type,communityId);
//		}
//		
//		function postRequest( supermarketId, type ,communityId,$modalInstance){
//			$.ajax({
//				url:urls.ms+"/community/communitySupermarket/bindSupermarket.do",
//				data:{
//					communityId:$scope.communityId,
//					supermarketId:supermarketId,
//					type:type,
//					newCommunityId:communityId
//				},
//				dataType:"json",
//				type:"post",
//				success:function( data ){
//					$scope.$apply(function(){
//						toaster.info( "正在为你处理,请稍后刷新" );
//						$modalInstance.close();
//						toaster.info( "",data.msg,3000 );
//						if( data.success ){
//							$("#communitySupermarketList").trigger("reloadGrid");
//						}
//					});
//				}
//			})
//		}
//	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		communitySupermarketService.remove({
    			"data":{communitySupermarketId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#communitySupermarketList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/communitySupermarket/edit.do?";
		if( id ){
			url = url + $.param( {communitySupermarketId:id} );
		}
		templateform.open({
			title:"CommunitySupermarket",
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
			communitySupermarketService.save( {
				data:$scope.communitySupermarket,
				success:function( data ){
					if( data.success ){
						$("#communitySupermarketList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/communitySupermarket/show.do?";
		if( id ){
			url = url + $.param( {communitySupermarketId:id} );
		}
		templateform.open({
			title:"CommunitySupermarket",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});