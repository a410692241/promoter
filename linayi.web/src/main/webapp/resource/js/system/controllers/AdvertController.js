'use strict';

app.controller('advertCtrl', function($scope,toaster,advertService,messager,templateform ) {
	
	function init(){
		$scope.release = release;
		$scope.unRelease = unRelease;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.advertItemList = [];
		$scope.search={
				title:"",
				status:"",
				startTime:"",
				endTime:"",
				openType:"",
		};
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#advertList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/system/advert/list.do",
			postData:$scope.search,
			pager : "#advertPager",
			colModel : [
				{name:'advertId',label:'主键',sortable:false,hidden:true}, 
				{name:'title',label:'广告标题',sortable:false}, 
				{name:'statusName',label:'发布状态',width:80,sortable:false}, 
				{name:'publishTime',label:'发布时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'validStart',label:'有效开始时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'validEnd',label:'有效结束时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'lastModify',label:'最后修改时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'account',label:'操作员',width:80,sortable:false}, 
                {label:"操作",name:"opt",width:200,sortable:false,formatter:function(cellvalue, options, rowObject){
                	var opts = "";
                	if(rowObject.status == 10 || rowObject.status == 30){
                		opts = opts + "<a href='javascript:void(0);' ng-click='release( "+rowObject.advertId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>发布</a> ";
                	}
                	if(rowObject.status == 20){
                		opts = opts + "<a href='javascript:void(0);' ng-click='unRelease( "+rowObject.advertId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>取消发布</a> ";
                	}
                	opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.advertId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.advertId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
					return opts;
                }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		advertService.remove({
    			"data":{advertId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#advertList").trigger("reloadGrid");
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
    	var ad_1 = {'advertItemId':'','imagePath':'','imageTextValue':'','hrefValue':'','openType':10};
		var ad_2 = {'advertItemId':'','imagePath':'','imageTextValue':'','hrefValue':'','openType':10};
		var ad_3 = {'advertItemId':'','imagePath':'','imageTextValue':'','hrefValue':'','openType':10};
		var ad_4 = {'advertItemId':'','imagePath':'','imageTextValue':'','hrefValue':'','openType':10};
		var ad_5 = {'advertItemId':'','imagePath':'','imageTextValue':'','hrefValue':'','openType':10};
		var categoryId = '';
		var categoryName = '';
		var resultDataList = [];
    	$scope.advertItemList = [ad_1];
    	$scope.advert = {
				advertId:'',
				template:1000,	
				title:'',
				validStart:'',
				validEnd:''
		};
//    	initOpenValueStatus();
    	
		var url = urls.ms + "/jsp/system/AdvertEdit.jsp";
		if( id ){
			$.ajax({
				url:urls.ms + "/system/advert/edit.do",
				type:"POST",
				data:{"advertId":id},
				dataType:"JSON",
				asyn:false,
				success:function(data){

					console.log(data)
					$scope.advert = {
						advertId:data.advertId,
						template:data.template,	
						title:data.title,
						validStart:new Date(data.validStart).format("yyyy-MM-dd HH:mm:ss"),
						validEnd:new Date(data.validEnd).format("yyyy-MM-dd HH:mm:ss")
					};
					resultDataList = data.advertItemList;
					$scope.advertItemList = resultDataList;
				}
			});
		}
		templateform.open({
			title:"广告内容",
			url:url,
			height:2000,
			scope:$scope,
			onOpen:function( $modalInstance, data ,$scope){
				//选择不同的模板
				$scope.listenTemplate = function(template){
					if(template == 1000){
						$scope.advert.template = 1000;
						$scope.advertItemList = resultDataList.length == 1 ? resultDataList : [ad_1];
					}else if(template == 5000){
						$scope.advert.template = 5000;
						var length = resultDataList.length;
						//根据新增或者编辑状态下点击不同的模板显示内容
						if(length == 0){
							$scope.advertItemList = [ad_1,ad_2,ad_3,ad_4,ad_5];
						}else if(length == 1){
							$scope.advertItemList = [resultDataList[0],ad_2,ad_3,ad_4,ad_5];
						}else{
							$scope.advertItemList = resultDataList;
						}
//						$scope.advertItemList = resultDataList.length == 1 ? [resultDataList[0],ad_2,ad_3,ad_4,ad_5] : [ad_1,ad_2,ad_3,ad_4,ad_5];
					}
				}
				//监听内容
				$scope.listenOpenType = function(index,openTypeValue){
//					switchOpenValue(index,openTypeValue);
					$scope.advertItemList[index-1]['openType'] = openTypeValue;
				}
				//选择商品
				$scope.getGoodsSku = function(index){
					templateform.open({
						title:"商品信息",
						data:data,
						scope:$scope,
						url:"jsp/system/AdSelectGoodsSku.jsp",
			    	},function( $modalInstance,data, $scope ){
			    		var rowId=$("#goodsRecommandList").jqGrid('getGridParam','selrow');
			    		var rowData =  $("#goodsRecommandList").jqGrid('getRowData',rowId);
			    		console.info( rowData );
			    		$scope.advertItemList[index]['goodsName'] = rowData.goodsName;
			    		$scope.advertItemList[index]['goodsId'] = rowData.goodsId;
			    		$modalInstance.close();
					})
				}
				//获取选中的类别id
				$scope.selectCategory = function(category){
					categoryId = category.categoryId;
					categoryName = category.name;
				}
				//选择商品类别
				$scope.getCategory = function(index){
					templateform.open({
						title:"商品信息",
						data:data,
						scope:$scope,
						url:"jsp/system/AdSelectGoodsCategory.jsp",
			    	},function( $modalInstance,data, $scope ){
			    		$scope.advertItemList[index]['categoryName'] = categoryName;
			    		$scope.advertItemList[index]['categoryId'] = categoryId;
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
    	    $('#advertForm').form("submit",{
    	    	url:urls.ms + "/system/advert/save.do",
     		    onSubmit: function( param ){ 

     		    	var template = $scope.advert.template;
     		    	var itemList = $scope.advertItemList;
     		    	var title = $scope.advert.title;
     		    	var validStart = $scope.advert.validStart;
     		    	var validEnd = $scope.advert.validEnd;
     		    	console.log(itemList)
     		    	//判断标题是否为空
     		    	if(title == ''){
     		    		toaster.error("","请填写广告标题",3000);
     		    		return false;
     		    	}
     		    	//判断有效开始时间、有效结束时间
     		    	if(validStart == ''){
     		    		toaster.error("","请填写有效开始时间",3000);
     		    		return false;
     		    	}
     		    	if(validEnd == ''){
     		    		toaster.error("","请填写有效结束时间",3000);
     		    		return false;
     		    	}
     		    	
     		    	if(template == 1000){
     		    		var item = itemList[0];
     		    		if(item.imagePath.indexOf("http") == -1){
     		    			toaster.error("","请提交1个广告",3000);
     		    			return false;
     		    		}
     		    		if(checkIsNull(item)){
     		    			return false;
     		    		};
     		    	}else{
     		    		var flag = false;
     		    		var isNull = false;
     		    		for(var i in itemList){
     		    			var item = itemList[i];
     		    			if(item.imagePath.indexOf("http") == -1){
     		    				flag = true;
     		    				break;
     		    			}
     		    			if(checkIsNull(item)){
     		    				isNull = true;
     		    				break;
         		    		};
     		    		}
     		    		if(flag){
     		    			toaster.error("","请提交5个广告",3000);
     		    			return false;
     		    		}
     		    		if(isNull){
     		    			return false;
     		    		}
     		    	}
     		    	param['advertItemListStr'] = JSON.stringify($scope.advertItemList);
     		    	param['position'] = 1;
     		    },   
    	    	success:function(data){
    	    		data = angular.fromJson( data );
    		    	if( data.success ){
						$("#advertList").trigger("reloadGrid");
						$scope.$apply(function(){
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						});
					}else{
						$scope.$apply(function(){
							toaster.error( "","操作失败",3000 );
						});
					}
    	    	}
    	    })
		}catch (e) {
			console.error( e );
			toaster.error( "",typeof e == "string" ? //
					e : e.msg ? //
							e.msg : "出错了",3000 );
		}
    }
    //检查填写广告内容是否为空
    function checkIsNull(item){

    	if(item.openType == 10 && (item.imageTextValue == '' || item.imageTextValue == null)){
 			toaster.error("","请填写图文详情",3000);
 			return true;
 		}
    	if(item.openType == 20 && (item.hrefValue == '' || item.hrefValue == null)){
 			toaster.error("","请填超链接",3000);
 			return true;
 		}
    	if(item.openType == 30 && item.goodsName == null){
 			toaster.error("","请选择商品",3000);
 			return true;
 		}
    	if(item.openType == 40 && item.categoryName == null){
 			toaster.error("","请选择类别",3000);
 			return true;
 		}
    }
    
    //发布
    function release(id){
    	var state = 20;
    	updateRelease(id,state);
    }
    
    //取消发布
    function unRelease(id){
    	var state = 30;
    	updateRelease(id,state)
    }
	
    function updateRelease(id,state){
    	//20：已发布，30：已取消
    	var url = urls.ms + "/system/advert/release.do";
    	if(state == 30){
    		url = urls.ms + "/system/advert/unRelease.do";
    	}
    	$.ajax({
			url:url,
			type:"POST",
			data:{"advertId":id},
			dataType:"JSON",
			success:function(data){
				data = angular.fromJson( data );
		    	if( data.success ){
					$("#advertList").trigger("reloadGrid");
					$scope.$apply(function(){
						toaster.success( "","操作成功",3000 );
					});
				}else{
					$scope.$apply(function(){
						toaster.error( "","操作失败",3000 );
					});
				}
			}
		});
    }
    //初始化
    init();
});