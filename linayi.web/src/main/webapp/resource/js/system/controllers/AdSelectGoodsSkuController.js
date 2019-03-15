'use strict';

app.controller('AdSelectGoodsSkuCtrl', function($http,$scope,toaster,messager,templateform,$templateCache ) {
	
	function init(){
		$scope.list = list;
		$scope.checkedCategory = checkedCategory;
		$scope.brandList = new Array( {name:"全部",code:""} );
		$scope.selectedBarand = $scope.brandList[0];
		$scope.search={
				goodsName:"",
				brandId:"",
				categoryId:"",
				status:"",
		}
		$scope.list();
	}
	
	
	/**列表查询*/
	function list(){
		var $grid = $("#goodsRecommandList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/goods/list.do",
			postData:$scope.search,
			pager : "#goodsRecommandPager",
			multiselect :true,
			multiboxonly:true,  
			beforeSelectRow: beforeSelectRow,
			colModel : [
			    {name:'_rowId',label:'记录ID',hidden:true}, 
				{name:'imagePath',label:'商品图片',hidden:true}, 
				{name:'goodsId',label:'商品ID',hidden:true}, 
				{name:'goodsName',label:'商品名称',width:200,sortable:false}, 
				{name:'categoryName',label:'类别',width:60,sortable:false}, 
				{name:'brandName',label:'品牌',width:60,sortable:false}, 
				{name:'statusName',label:'上架状态',width:60,sortable:false}, 
				{name:'createTime',label:'创建时间',width:80,sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'adminName',label:'操作员',width:50,sortable:false}, 
			]
		});
	}
	function beforeSelectRow(){  
        $("#goodsRecommandList").jqGrid('resetSelection');  
        return(true);  
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
			   goodsService.listBrand({
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
    
    //初始化
    init();
});