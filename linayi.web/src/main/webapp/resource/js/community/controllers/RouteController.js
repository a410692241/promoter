'use strict';

app.controller('routeCtrl', function($scope,toaster,routeService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				name:"",
				communityId:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#routeList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/community/route/list.do",
			postData:$scope.search,
			pager : "#routePager",
			subGridRowExpanded: showChildGrid,
			subGrid: true,
		    subGridOptions : {
				reloadOnExpand :false,
				selectOnExpand : true 
			},
			colModel : [
				{name:'routeId',label:'主键',sortable:false,hidden:true}, 
				{name:'name',label:'路线名称',sortable:false}, 
				{name:'nickname',label:'司机',sortable:false}, 
				{name:'carNo',label:'车辆编号',sortable:false}, 
				{name:'plate',label:'车牌号',sortable:false}, 
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.routeId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.routeId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
	/**查询负责超市或者比价超市*/
	 function showChildGrid(parentRowID, parentRowKey) {
		 var row = $(this).jqGrid("getRowData",parentRowKey);
	     var childGridID = parentRowID + "_table";
	     var childGridPagerID = parentRowID + "_pager";
	     $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
	     $("#" + childGridID).jqGrid({
	    	 url : urls.ms+"/community/routeCommunity/list.do?routeId="+row.routeId,
				pager : "#routeCommunityPager",
				colModel : [
					{name:'communityName',label:'社区',sortable:false}, 
				],
			 loadonce: true,
	         width: 500,
	         rownumbers:false,
	         height: '100%',
	     });
	
	 }
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		routeService.remove({
    			"data":{routeId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#routeList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/route/edit.do?";
    	$.ajax({
    		url:url,
    		data:{routeId:id},
    		dataType:"json",
    		type:"post",
    		success:function( data ){
    			openEdit(data.data);
    		}
    	})
    	
    	function openEdit( data ){
    		templateform.open({
    			title:"路线信息",
    			url:urls.ms + "/jsp/community/RouteEdit.jsp",
    			data:data,
    			scope:$scope,
    			onOpen:function( $modalInstance, data ,$scope){
    				var route = $scope.route = data.route;
    				var checkedCommunityList = $scope.checkedCommunityList = data.checkedCommunityList;
    				for( var index in checkedCommunityList ){
    					checkedCommunityList[index].rank = index+1;
    				}
    				var unCheckedCommunityList = $scope.unCheckedCommunityList = data.unCheckedCommunityList;
    				var carCtrl= $scope.carCtrl = {
    					carList : data.carList,
    					car : data.carList.filter( item=>item.carId == route.carId )[0]
    				}
    				//选择或者取消选择
    				$scope.toggleCheckCommunity = function( community, toggle, index ){
    					if( toggle == 1 ){
    						checkedCommunityList.push( community );
    						unCheckedCommunityList.splice(index, 1);
    					}else{
    						unCheckedCommunityList.push( community );
    						checkedCommunityList.splice(index, 1);
    					}
    				}
    				/**
    				 * 交换两条数据
    				 */
    				$scope.switchItem = function( currentIndex, direction ){

    					//下移
    					if( direction == 1 ){
    						var length = checkedCommunityList.length;
    						if( currentIndex == length - 1 ){
    							return;
    						}
    						var current = checkedCommunityList[currentIndex];
    						var next = checkedCommunityList[currentIndex + 1];
    						checkedCommunityList[currentIndex] = next;
    						checkedCommunityList[currentIndex + 1] = current;
    					//上移
    					}else{
    						if( currentIndex == 0 ){
    							return;
    						}
    						var current = checkedCommunityList[currentIndex];
    						var next = checkedCommunityList[currentIndex - 1];
    						checkedCommunityList[currentIndex] = next;
    						checkedCommunityList[currentIndex - 1] = current;
    					}
    				}
    				
    				//排序
    				function reRank(){
    					checkedCommunityList.sort( function( a, b ){
    						return a.rank - b.rank;;
    					} );
    				}
    			}
    		},function( $modalInstance,data, $scope ){
    			save( $modalInstance,data, $scope );
    		});
    	}
    	
    }
    
    
	/**保存*/
    function save( $modalInstance,data, $scope ){
    	var carCtrl = $scope.carCtrl;
    	var route = $scope.route;
    	var checkedCommunityList = $scope.checkedCommunityList;
    	if( !route.name ){
    		return toaster.error( "","请输入路线名称",3000 );
    	}
    	if( !carCtrl.car || !carCtrl.car.carId ){
    		return toaster.error( "","请选择车辆",3000 );
    	}
    	if( checkedCommunityList.length == 0 ){
    		return toaster.error( "","请选择社区",3000 );
    	}
    	var routeCommunityList = new Array();
    	for( var index in checkedCommunityList ){
    		routeCommunityList.push({
    			communityId:checkedCommunityList[index].communityId,
    			rank:index+1
    		})
    	}
    	
    	$.ajax({
    		url:urls.ms + "/community/route/save.do?",
    		data:JSON.stringify({
    			route:{
	    			routeId:route.routeId,
	    			name:route.name,
	    			carId:carCtrl.car.carId
    			},
    			routeCommunityList:routeCommunityList
    		}),
    		dataTye:"json",
    		type:"post",
    		contentType:"application/json",
    		success:function( data ){
    			$scope.$apply( function(){
    				toaster.info( "", data.msg ,3000 );
    				if( data.success ){
    					$modalInstance.close();
    					list();
    				}
    			} )
    		}
    	})
    }
    
    /**查看*/
    function show( id ){
    	var url = urls.ms + "/community/route/show.do?";
		if( id ){
			url = url + $.param( {routeId:id} );
		}
		templateform.open({
			title:"Route",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});