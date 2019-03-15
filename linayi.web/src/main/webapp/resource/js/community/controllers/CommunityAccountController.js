'use strict';

app.controller('communityAccountCtrl', function($scope,toaster,communityAccountService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				communityAccountId:"",
				address:"",
				areaCode:"",
				createTime:"",
				nickname:"",
				mobile:"",
				account:"",
				password:"",
				parentId:"",
				communityId:"",
				remark:"",
				mobileToken:"",
				sex:"",
		}
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		var $grid = $("#communityAccountList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/community/communityAccount/list.do",
			postData:$scope.search,
			pager : "#communityAccountPager",
			colModel : [
			    {name:'account',label:'登陆账号',sortable:false}, 
				{name:'areaCode',label:'地区编码',sortable:false}, 
				{name:'communityId',label:'社区网点ID',sortable:false}, 
	             {label:"操作",name:"opt",width:170,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.communityAccountId+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>查看</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.communityAccountId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.communityAccountId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";
            		return opts;
	             }}
			]
		});
	}
	
    /**删除*/
    function remove( id ){
    	messager.confirm("确认删除？",function( $modalInstance ){
    		communityAccountService.remove({
    			"data":{communityAccountId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#communityAccountList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/communityAccount/edit.do?";
		if( id ){
			url = url + $.param( {communityAccountId:id} );
		}
		templateform.open({
			title:"CommunityAccount",
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
			communityAccountService.save( {
				data:$scope.communityAccount,
				success:function( data ){
					if( data.success ){
						$("#communityAccountList").trigger("reloadGrid");
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
    	var url = urls.ms + "/community/communityAccount/show.do?";
		if( id ){
			url = url + $.param( {communityAccountId:id} );
		}
		templateform.open({
			title:"CommunityAccount",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});