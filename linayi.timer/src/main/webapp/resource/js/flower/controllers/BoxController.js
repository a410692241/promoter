'use strict';

app.controller('boxCtrl', function($scope,toaster,boxService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				boxId:"",
				boxNo:"",
				weight:"",
				openBox:"",
				type:"",
				imagePath:"",
				objectId:"",
				createTime:"",
				sortingCategoryId:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#boxList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/flower/box/list.do",
			postData:$scope.search,
			pager : "#boxPager",
			colModel : [
				{name:'boxId',label:'主键',sortable:false}, 
				{name:'boxNo',label:'箱子编号',sortable:false}, 
				{name:'weight',label:'重量',sortable:false}, 
				{name:'openBox',label:'是否开箱验货',sortable:false}, 
				{name:'type',label:'箱子类型',sortable:false}, 
				{name:'imagePath',label:'封箱图片',sortable:false}, 
				{name:'objectId',label:'车辆ID/订单ID',sortable:false}, 
				{name:'createTime',label:'装箱时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
				{name:'sortingCategoryId',label:'分拣类别ID',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.boxId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.boxId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.boxId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		boxService.remove({
    			"data":{boxId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#boxList").trigger("reloadGrid");
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
    	var url = urls.ms + "/flower/box/edit.do?";
		if( id ){
			url = url + $.param( {boxId:id} );
		}
		templateform.open({
			title:"Box",
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
			boxService.save( {
				data:$scope.box,
				success:function( data ){
					if( data.success ){
						$("#boxList").trigger("reloadGrid");
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
    	var url = urls.ms + "/flower/box/show.do?";
		if( id ){
			url = url + $.param( {boxId:id} );
		}
		templateform.open({
			title:"Box",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});