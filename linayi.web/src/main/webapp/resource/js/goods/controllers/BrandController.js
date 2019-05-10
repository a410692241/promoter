'use strict';

app.controller('brandCtrl', function($q,$scope,toaster,brandService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.upload = upload;
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
			colModel : [
				{name:'brandId',label:'品牌ID',sortable:false,hidden:true}, 
				{name:'name',label:'名称',sortable:false}, 
				/*{name:'logo',label:'品牌LOGO',sortable:false}, */
				{name:'content',label:'介绍',sortable:false}, 
				{name:'rank',label:'排序',sortable:false}

			]
		});
	}

    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		brandService.remove({
    			"data":{brandId:id},
    			"success":function( data ){
            		if( data.respCode==="S" ){
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
		var url = urls.ms + "/goods/brand/save.do?";
    	try{
			brandService.save( {
				data:$scope.selectUser,
				success:function( data ){
					if( data.respCode==='S' ){
						$("#brandList").trigger("reloadGrid");
						$modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "","品牌名不能为空",3000 );
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
	$scope.confirmImport=function(){
		var defer = $q.defer();
		$("<input name='file' type='file' style='visibility: hidden;'/>").click().change( function( value ){
			// 提交表单
			var $body = $("body:eq(0)");
			var $form = $("<form action='#' enctype='multipart/form-data' method='post'>");
			$body.append( $form );
			$form.append( $( this ) ).form("submit",{
				onSubmit:function(param){
					$scope.promise = defer.promise;
				},
				success:function (data){
					data = angular.fromJson( data );
					$scope.$apply(function(){
						if( data.respCode ){
							toaster.success("",data.data,3000 );
							list();
						}
					})
				},
				url:"goods/brand/parse.do"
			});
		});
	}

	function upload() {


	}
    //初始化
    init();
});