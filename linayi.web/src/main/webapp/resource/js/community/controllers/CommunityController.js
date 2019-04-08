'use strict';

app.controller('communityCtrl', function($scope,toaster,communityService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.selectArea = selectArea;
		$scope.showCommunityLocation = showCommunityLocation;
		$scope.updateCommunityPrice = updateCommunityPrice;
		$scope.search={
				communityId:"",
				name:"",
				address:"",
				mobile:"",
				areaName:'',
				areaCode:'',
//				createTime:"",
//				amount:"",
//				remark:"",
//				lastModify:"",
//				removed:"",
//				sectionNo:"",
//				longitude:"",
//				latitude:"",
		}

		$scope.list();
		$scope.openBindSupermarket= openBindSupermarket;
	}
    function selectArea(){
		var url = urls.ms + "/jsp/community/SupermarketArea.jsp";
		var $scope = angular.element("#supermarketInfoForm").scope();
		templateform.open({
			title:"选择街道",
			url:url,
			scope:$scope,
			buttons:[],
			onOpen:function( $modalInstance, data ,$scope){
				$scope.provinceList = [];
				$scope.cityList = [];
				$scope.regionList = [];
				$scope.streetList = [];
				
				var result = '';
				var provinceName = '';
				var cityName = '';
				var regionName = '';
				var streetName = '';
		
				$.ajax({
					url : urls.ms + "/community/supermarket/getProvince.do",
					async : false,
					type : "post",
					dataType : "json",
					success : function(data){
						if(data.respCode ==='S'){
							$scope.provinceList = data.data;
						}
					}
				})
				
				$scope.getCity = function( province ){
					provinceName = '';
					provinceName = province.name;
					$scope.checkProvince = province;
					$scope.cityList = [];
					$scope.regionList = [];
					$scope.streetList = [];
					$.ajax({
						url : urls.ms + "/community/supermarket/getAreaInfo.do",
						type : "post",
						data : {"parent":province.code},
						dataType : "json",
						success : function(data){
							if(data.respCode ==='S'){
								$scope.$apply( function(){
									$scope.cityList = data.data;
								} );
							}
						}
					})
				}
				
				$scope.getRegion = function( city ){
					cityName = '';
					cityName = city.name;
					$scope.checkCity = city;
					$scope.regionList = [];
					$scope.streetList = [];
					$.ajax({
						url : urls.ms + "/community/supermarket/getAreaInfo.do",
						type : "post",
						data : {"parent":city.code},
						dataType : "json",
						success : function(data){
							if(data.respCode ==='S'){
								$scope.$apply( function(){
									$scope.regionList = data.data;
								} );
							}
						}
					})
				}
				
				$scope.getStreet = function( region ){
					regionName = '';
					regionName = region.name;
					$scope.checkRegion = region;
					$scope.streetList = [];
					$.ajax({
						url : urls.ms + "/community/supermarket/getAreaInfo.do",
						type : "post",
						data : {"parent":region.code},
						dataType : "json",
						success : function(data){
							if(data.respCode ==='S'){
								$scope.$apply( function(){
									if(data.data.length == 0){
										// $scope.streetList = [{"name":"该区域暂无服务点"}]
									}else{
										$scope.streetList = data.data;
									}
								} );
							}
						}
					})
				}
				
				$scope.getResult = function( street ){
					var areaCode = street.code;
					streetName = '';
					streetName = street.name;
					$scope.checkStreet = street
					result = provinceName + cityName + regionName + streetName;
					$scope.community.areaName = result;
					$scope.community.areaCode = areaCode;
					$modalInstance.close();
				}
			}
		});	
	}
	/**列表查询*/
	function list(){
		var $grid = $("#communityList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/community/community/list.do",
			postData:$scope.search,
			pager : "#communityPager",
			subGrid: true,
			subGridRowExpanded: showChildGrid,
		    subGridOptions : {
				reloadOnExpand :false,
				selectOnExpand : true 
			},
			colModel : [
				{name:'communityId',label:'社区ID',sortable:false,width:80}, 
				{name:'email',label:'email',sortable:false,width:80}, 
				{name:'name',label:'社区名称',sortable:false,width:160}, 
				{name:'qq',label:'qq',sortable:false,width:80}, 
				{name:'mobile',label:'手机号',sortable:false,width:80}, 
				{name:'address',label:'地址',sortable:false,width:200}, 
	             {label:"操作",name:"opt",width:300,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.communityId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>修改</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.communityId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>删除</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='showCommunityLocation( "+rowObject.communityId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>绑定小区</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='openBindSupermarket( "+rowObject.communityId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>绑定超市</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='communityInfo( "+rowObject.communityId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='updateCommunityPrice( "+rowObject.communityId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>更新价格</a> ";
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
	         url: urls.ms+"/community/supermarket/listBindSupermarket.do?communityId="+row.communityId,
	         mtype: "GET",
	         datatype: "json",
	         colModel: [
	             { name: 'name', label: '超市名称', key: true, width: 60,sortable:false },
	             { name: 'typeName', label: '超市类型', width: 60,sortable:false },
	             { name: 'communityName', label: '所属社区', key: true, width: 60,sortable:false },
	             { name: 'address', label: '地址', width: 200 ,sortable:false},
	             {name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
						return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
	         ],
			loadonce: true,
	         width: 500,
	         rownumbers:false,
	         height: '100%',
	     });
	
	 }
	
	 /**社区配送地址*/
	 function showCommunityLocation( communityId ){
		 var url = urls.ms + "/jsp/community/CommunityLocationList.jsp?";
		 if( communityId ){
			url = url + $.param( {communityId:communityId} );
		 }
		 $scope.fromCommunity = communityId;
		 $scope.width="120px"
		 templateform.open({
			title:"社区编辑",
			url:url,
			scope:$scope,
			buttons:[],
			onOpen:function( $modalInstance, data ,$scope){
				
			}
		 },function( $modalInstance,data, $scope ){
			save( $modalInstance,data, $scope );
		 });
	 }
	 
    /**删除*/
    function remove( communityId ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		communityService.remove({
    			"data":{communityId:communityId},
    			"success":function( data ){
            		if( data.respCode =="S" ){
            			$("#communityList").trigger("reloadGrid");
            			list();
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
    	var url = urls.ms + "/community/community/edit.do?";
		if( id ){
			url = url + $.param( {communityId:id} );
		}
		templateform.open({
			title:"社区编辑",
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
			communityService.save( {
				data:$scope.community,
				success:function( data ){
					if( data.respCode =="S" ){
						$("#communityList").trigger("reloadGrid");
						$modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.errorType,1000 );
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
    
    /**
     * 绑定负责超市或者比较超市
     * @param type 10:负责超市，20:比价超市
     */
    function openBindSupermarket( communityId ){
    	var url = urls.ms + "/jsp/community/CommunitySupermarketList.jsp?";
		 if( communityId ){
			url = url + $.param( {communityId:communityId} );
		 }
		 templateform.open({
			title:"超市信息列表",
			url:url,
			scope:$scope,
			buttons:[],
			onOpen:function( $modalInstance, data ,$scope){
				$scope.communityId = communityId;
			}
		 },function( $modalInstance,data, $scope ){

		 });
    }
    
    /**查看*/
    function show( id ){
    	var url = urls.ms + "/community/community/show.do?";
		if( id ){
			url = url + $.param( {communityId:id} );
		}
		templateform.open({
			title:"Community",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }

	/** 社区信息 */
	function communityInfo( communityId ){
		var url = urls.ms + "/community/community/info.do?";
		if( communityId ){
			url = url + $.param( {communityId:communityId} );
		}
		templateform.open({
			title:"社区信息",
			buttons:[],
			backdrop: true,
			keyboard: true,
			url:url
		});
	}

	/**删除*/
	function updateCommunityPrice( communityId ){
		messager.confirm("确认更新该社区所有商品价格？",function( $modalInstance ){
			communityService.updateCommunityPrice({
				"data":{communityId:communityId},
				"success":function( data ){
					if( data.respCode =="S" ){
						$("#communityList").trigger("reloadGrid");
						// list();
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


    //初始化
    init();
});