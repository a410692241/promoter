'use strict';

app.controller('attributeCtrl', function($scope,toaster,attributeService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.search={
				attributeId:"",
				name:"",
				status:"",
				updateTime:"",
				createTime:"",
				adminName:"",
		}
		$scope.list();
	}
	
	//列表查询
	function list(){
		var $grid = $("#attributeList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/attribute/list.do",
			postData:$scope.search,
			pager : "#attributePager",
			colModel : [
				{name:'name',width:90,label:'名称',sortable:false}, 
				{name:'value',label:'规格值',width:660,sortable:false,formatter:function( cellValue, options, rowObject ){
					var values = [];
					for( var index in rowObject.attributeValueList ){
						values.push( rowObject.attributeValueList[index].value );
					}
					return values.join(" , ");
				}}, 
				{name:'createTime',label:'创建时间',width:110,sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}},
	             {label:"操作",name:"opt",width:170,width:110,sortable:false,formatter:function(cellvalue, options, rowObject){
					var opts = "";
					/*opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.attributeId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
					opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.attributeId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";*/
					opts = opts + "<a href='javascript:void(0);' ng-click='edit( "+rowObject.attributeId+" )' class='btn btn-primary fa fa-edit btn-sm td-compile'>新增规格值</a> ";
					return opts;
	             }}
			]
		});
	}
	
    //删除
    function remove( id ){
    	messager.confirm("确认删除？",function( modalInstance ){
    		attributeService.remove({
    			"data":{attributeId:id},
    			"success":function( data ){
            		if( data.success ){
            			$("#attributeList").trigger("reloadGrid");
            			$scope.$apply(function(){
            				toaster.error( "",typeof e == "string" ? //
            						e : e.msg ? //
            								e.msg : "出错了",3000 );
						});
            			modalInstance.close();
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
    
    /**添加属性*/
    function addAttrValue( $scope ){
    	var attributeValueList;
		if( $scope.attribute.attributeValueList ){
			attributeValueList = $scope.attribute.attributeValueList;
		}else{
			attributeValueList = $scope.attribute.attributeValueList = [];
		}
		attributeValueList.push({
			"value":""
		}); 
    }
    
    /**删除属性*/
    function removeAttrValue($scope,index){
		$scope.attribute.attributeValueList.splice( index,1 );
    }
    
    /**新增，编辑*/
    function edit( id ){
    	var url = urls.ms + "/goods/attribute/edit.do?";
		if( id ){
			url = url + $.param( {attributeId:id} );
		}
		templateform.open({
			title:"属性信息编辑",
			url:url,
			scope:$scope,
			onOpen:function( $modalInstance, data ,$scope){
				//添加属性
				$scope.addAttrValue = function(){
					addAttrValue( $scope );
				}
				//删除属性
				$scope.removeAttrValue=function( index ){
					removeAttrValue($scope,index);
				}
			}
		},function( $modalInstance,data, $scope ){
			save(  $modalInstance,data, $scope  );
		});
    }
    
    /**保存*/
    function save( $modalInstance,data, $scope ){
    	try{
    		
    		var attribute = $scope.attribute;
			attributeService.save( {
				data:attribute,
				success:function( data ){
					if( data.respCode ==='S' ){
						$("#attributeList").trigger("reloadGrid");
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
    
    //查看
    function show( id ){
    	var url = urls.ms + "/goods/attribute/show.do?";
		if( id ){
			url = url + $.param( {attributeId:id} );
		}
		templateform.open({
			title:"Attribute",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:url
		});
    }
    
    //初始化
    init();
});