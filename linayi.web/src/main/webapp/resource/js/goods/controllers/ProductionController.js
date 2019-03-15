'use strict';

app.controller('productionCtrl', function($scope,toaster,productionService,messager,templateform,$http ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.checkedCategory = checkedCategory;
		$scope.brandList = new Array( {name:"全部",code:""} );
		$scope.selectedBarand = $scope.brandList[0];
		var search = $scope.search={
				title:"",
				categoryId:"",
				brandId:""
		}
		var brand = $scope.brand={
				id:"",
				name:""
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#productionList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/production/list.do",
			postData:$scope.search,
			pager : "#productionPager",
			colModel : [
				{name:'title',label:'产品名称',sortable:false}, 
				{name:'categoryName',label:'类别',sortable:false}, 
				{name:'brandName',label:'品牌',sortable:false}, 
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.productionId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.productionId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.productionId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		productionService.remove({
    			"data":{productionId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#productionList").trigger("reloadGrid");
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
    
    /**列表查询页面选择类别*/
   function checkedCategory( $item ){
	   try{
		   $scope.search.categoryId=$item.code;
		   $scope.brandList.splice(1,$scope.brandList.length-1);
		   
		   angular.element("#brandId").scope().$select.select($scope.brandList[0]);
		   if( !$item.code ){
			   return;
		   }
		   //获取品牌信息
		   productionService.listBrand({
			   data:{categoryId:$scope.search.categoryId},
			   success:function( data ){
				   if( data instanceof Array ){
					   $scope.brandList = $scope.brandList.concat( data );
				   }else{
					   $scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
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
   
   /**编辑页面选择类别*/
   function checkCategory( $item ){
	   try{
		   var $scope = angular.element("#editBrandId").scope();
		   var brandList = $scope.brandCtrl.brandList;
		   brandList.splice(0,brandList.length);
		   $scope.$select.select( {} );
		   if( !$item.code ){
			   return;
		   }
		   //获取平品牌信息
		   productionService.listBrand({
			   data:{categoryId:$item.code},
			   success:function( data ){
				   if( data instanceof Array ){
					   $scope.brandCtrl.brandList = brandList.concat( data );
				   }else{
					   $scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
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
   
    /**新增，编辑*/
    function edit( id ){
    	var url = urls.ms + "/goods/production/edit.do?";
		if( id ){
			url = url + $.param( {productionId:id} );
		}
		$http.post(url).then(function( data ){
			openEditUI(
					angular.fromJson( data.data ).data,
					true
			);
		},function(){
			
		});
    }
    
    function openEditUI( data,isEdit ){
    	templateform.open({
			title:"产品信息",
			url:isEdit ? "jsp/goods/ProductionEdit.jsp" : "jsp/goods/ProductionShow.jsp",
			data:data,
			buttons:isEdit ? null : [],
			backdrop: isEdit ? false : true,
		    keyboard: isEdit ? false: true,
			onOpen:function( $modalInstance, data ,$scope){
				var categoryCtrl = $scope.categoryCtrl={
					categoryList:data.categoryList,
					category:{},
					checkCategory:checkCategory
				}
				var brandCtrl = $scope.brandCtrl={
						name:data.production.brandName,
						id:data.production.brandId
				}
				var production = $scope.production = data.production;
				if( production.categoryId ){
					angular.forEach( categoryCtrl.categoryList,function( item,index ){
						if( item.code == production.categoryId ){
							categoryCtrl.category = item;
							return;
						}
					} )
				}
//				if( production.brandId ){
//					angular.forEach( brandCtrl.brandList,function( item,index ){
//						if( item.code == production.brandId ){
//							brandCtrl.brand = item;
//							return;
//						}
//					} )
//				}
				var attrCtrl = $scope.attrCtrl={
						attrList:[],
						dataList:[]
				}
				var salesAttrCtrl = $scope.salesAttrCtrl={
						attrList:[],
						dataList:[]
				}
				var attrList = data.attrList.join(",");
				$scope.initAttrCtrl=function( ){
					angular.forEach( attrCtrl.dataList,function( item, index ){
						if( (","+attrList+",").indexOf(","+item.code+",") >= 0 ){
							attrCtrl.attrList.push( item );
						}
					} );
				}
				
				var salesAttrList = data.salesAttrList;
				$scope.initSalesAttrCtrl=function( ){
						for(var i = 0; i< salesAttrList.length;i++){
							angular.forEach( salesAttrCtrl.dataList,function( item, index ){
								console.log(item.name);
								if(salesAttrList[i] == item.code){
									salesAttrCtrl.attrList.push( item );
								}
							} );
						}
				}
				//选择品牌
				$scope.selectBrand = function(){
					templateform.open({
						title:"品牌信息",
						data:data,
						scope:$scope,
						url:"jsp/goods/ProductionAddBrand.jsp",
			    	},function( $modalInstance,data, $scope ){
			    		var rowId=$("#brandList").jqGrid('getGridParam','selrow');
			    		var rowData =  $("#brandList").jqGrid('getRowData',rowId);
			    		console.info( rowData );
			    		brandCtrl.name = rowData.name;
			    		brandCtrl.id = rowData.brandId;
			    		$modalInstance.close();
					})
				}
				
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,data, $scope );
		});
    }
    
	/**保存*/
    function save( $modalInstance,data, $scope ){
    	try{
    		if( !$scope.production.title ){
    			throw "产品标题不能为空";
    		}
    		var category = $scope.categoryCtrl.category;
    		if( !category.code ){
    			throw "请选择类别"
    		}
    		
    		var brand = $scope.brandCtrl;
    		if( !brand.name ){
    			throw "请选择品牌"
    		}
    		//规格列表
    		var attrList = $scope.attrCtrl.attrList;
    		var salesAttrList = $scope.salesAttrCtrl.attrList;
    		var productionAttributeList = new Array();
    		if( !salesAttrList.length ){
    			throw "请选择销售属性";
    		}
    		angular.forEach( attrList,function( item,index ){
    			productionAttributeList.push({
    				attributeId:item.code,
    				sales:1,
    			});
    		} )
    		angular.forEach( salesAttrList,function( item,index ){
    			productionAttributeList.push({
    				attributeId:item.code,
    				sales:2,
    			});
    		} )
    		var production = $scope.production;
    		var data={
    			productionId:production.productionId,
    			title:production.title,
    			categoryId:category.code,
    			brandId:brand.id,
    			productionAttributeList:productionAttributeList
    		}
			productionService.save( {
				data:data,
				success:function( data ){
					if( data.success ){
						$("#productionList").trigger("reloadGrid");
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
		var url = urls.ms + "/goods/production/edit.do?";
		if( id ){
			url = url + $.param( {productionId:id} );
		}
		$http.post(url).then(function( data ){
			openEditUI(
					angular.fromJson( data.data ).data,
					false
			);
		},function(){
			
		});
    }
    
    //初始化
    init();
});