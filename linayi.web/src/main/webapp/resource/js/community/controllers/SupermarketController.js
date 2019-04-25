'use strict';

app.controller('supermarketCtrl', function($scope,toaster,supermarketService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.selectArea = selectArea;
		$scope.search={
				supermarketId:"",
				name:"",
                procurerId:"",
				userId:"",
				createTimeStart:"",
				createTimeEnd:"",

		}
		$scope.list();
		$scope.supermarket = {
				supermarketId:'',
				name:'',
				address:'',
				areaName:'',
				areaCode:'',
				logo:'',
				status:''
		}
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#supermarketList");
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
			pager : "#supermarketPager",
			colModel : [
				{name:'supermarketId',label:'主键',hidden:true}, 
				{name:'name',label:'名称',sortable:false}, 
				{name:'areaName',label:'地址',sortable:false,width:80}, 
				{name:'address',label:'详细地址',sortable:false},
				{name:'userId',label:'用户编号',sortable:false,hidden:true},
                {name:'procurerId',label:'采买员编号',sortable:false},
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'updateTime',label:'最后修改时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.supermarketId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.supermarketId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.supermarketId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
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
									if(data.data.length != 0){
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
					$scope.supermarket.areaName = result;
					$scope.supermarket.areaCode = areaCode;
					$modalInstance.close();
				}
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,data, $scope );
		});
		
		
		
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("删除超市信息,是否继续？",function( $modalInstance ){
    		
    		$.ajax({
				url : urls.ms + "/community/supermarket/logicDelete.do",
				async : false,
				type : "post",
				data : {"supermarketId":id},
				dataType : "JSON",
				success : function(data){
					if(data.respCode === 'S'){
						$("#supermarketList").trigger("reloadGrid");
						$modalInstance.close();
						toaster.success( "","删除成功",3000 );
					}else{
						toaster.error( "",data.msg,3000 );
					}
				}
			})
    		
    	});
    }
    
    /**新增，编辑*/
    function edit( id ){
    	$scope.supermarket = {
    			name : "",
    			address : "",	
    			areaCode : "",
				logo : "",
				userId:"",
				status : "",
				procurerId:""
			}
    	var url = urls.ms + "/jsp/community/SupermarketEdit.jsp";
		templateform.open({
			title: "",
			url:url,
			scope:$scope,
			onOpen:function( $modalInstance, data ,$scope){
				if(id){
					$.ajax({
						url : urls.ms + "/community/supermarket/edit.do",
						async : false,
						type : "post",
						data : {"supermarketId":id},
						dataType : "JSON",
						success : function(data){
							var supermarket = data.data;
							if(data.respCode ==='S'){
								$scope.supermarket = {
										supermarketId:supermarket.supermarketId,
										name:supermarket.name,
										address:supermarket.address,
										areaName:supermarket.areaName,
										logo:supermarket.logo,
										userId:supermarket.userId,
										status:supermarket.status,
										procurerId:supermarket.procurerId
								}
							}
						}
					})
				}
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,$scope.supermarket, $scope );
		});
    }
    
	/**保存*/
    function save( $modalInstance,data, $scope ){
    	try{
    		$('#supermarketInfoForm').form("submit",{
    		    url:urls.ms + "/community/supermarket/save.do",
    		    onSubmit: function( param ){   
    		    	for(var key in data){
    		    		param[key] = data[key]
    		    	}
    		    },   
    		    success:function(datas){   
    		    	datas = JSON.parse(datas);
    		    	if( datas.respCode==="S" ){
						$("#supermarketList").trigger("reloadGrid");
						$scope.$apply(function(){
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						});
					}else{
						$scope.$apply(function(){
							toaster.error( "",datas.msg,3000 );
						});
					}
    		    }   
    		});
		}catch (e) {
			console.error( e );
			toaster.error( "",typeof e == "string" ? //
					e : e.msg ? //
							e.msg : "出错了",3000 );
		}
    }
    
    /**查看*/
    function show( id ){
    	var url = urls.ms + "/community/supermarket/show.do?";
		if( id ){
			url = url + $.param( {supermarketId:id} );
		}
		templateform.open({
			title:"超市信息",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});