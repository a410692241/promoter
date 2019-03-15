'use strict';

app.controller('brandCtrl', function($scope,toaster,brandService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				brandId:"",
				name:"",
				logo:"",
				content:"",
				rank:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#brandList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/brand/list.do",
			postData:$scope.search,
			pager : "#brandPager",
			multiselect :true,
			multiboxonly:true,  
			beforeSelectRow: beforeSelectRow,
			colModel : [
	            {name:'brandId',label:'品牌id',hidden:true}, 
				{name:'name',label:'名称',sortable:false}, 
				{name:'content',label:'介绍',sortable:false}, 
			]
		});
	}
	
	function beforeSelectRow(){  
        $("#brandList").jqGrid('resetSelection');  
        return(true);  
    } 
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		brandService.remove({
    			"data":{brandId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#brandList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/brand/edit.do?";
		if( id ){
			url = url + $.param( {brandId:id} );
		}
		templateform.open({
			title:"Brand",
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
			brandService.save( {
				data:$scope.brand,
				success:function( data ){
					if( data.success ){
						$("#brandList").trigger("reloadGrid");
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
    	var url = urls.ms + "/goods/brand/show.do?";
		if( id ){
			url = url + $.param( {brandId:id} );
		}
		templateform.open({
			title:"Brand",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});