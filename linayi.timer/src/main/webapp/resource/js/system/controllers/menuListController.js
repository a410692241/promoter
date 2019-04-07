'use strict';

app.controller('menuListCtrl', function($http,$scope,toaster,messager,templateform,$interval,$timeout,$state ) {
	 function init(){
		 
		 $scope.enableView = false;
		 
		 $scope.expandAll=function(){
			 $scope.$broadcast('angular-ui-tree:expand-all');
		 }
		 $scope.collapseAll=function(){
			 $scope.$broadcast('angular-ui-tree:collapse-all');
		 }
		 
		 $scope.privilage={
				 status:1000,
				 common:2,
		 }
		 $scope.options={
			checkbox:false,
			accept: function(sourceNodeScope, destNodesScope, destIndex) {
				return sourceNodeScope.depth() == destNodesScope.depth()+1;
			},
		    dragStop:function( event ){
		    	if( event.source.index != event.dest.index ){
		    		postNode( $scope.privilageList[0] );
		    	}
		    }
		 }
		 $scope.privilageList=[];
		 
		 $scope.addChild = addChild;
		 $scope.edit = edit;
		 $scope.deletePrivilage = deletePrivilage;
		 $scope.changeStatus = changeStatus;
		 $scope.changeCommon = changeCommon;
		 $scope.save = save;
		 $scope.cancel = cancel;
		 
		 listPrivilage();
		 
	 }
	 
	 function postNode( data ){
			$.ajax({
				url:urls.ms + '/accountMenu/resetNodeIndex.do',
				data:angular.toJson( data ),
				dataType:"json",
				type:"post",
				error:function(){
					$scope.$apply(function(){
						$scope.enableView=false
					})
					listPrivilage();
				},
				success:function( data ){
					if( !data.success ){
						$scope.$apply(function(){
							$scope.enableView=false
						})
						listPrivilage()
					}
				}
			});
		}
	 
	 
	 function save(){
		 var menu = {
				 menuId:$scope.privilage.id ? $scope.privilage.id : "",
				 moduleId:$scope.privilage.parentId ? $scope.privilage.parentId : "-1",
				 menuName:$scope.privilage.text,
				 url:$scope.privilage.url ? $scope.privilage.url : "",
				 status:$scope.privilage.status,
				 category:$scope.privilage.category,
				 code:$scope.privilage.code
		 }
		 if( menu.menuName == "" ){
			 toaster.error( "","请输入菜单名称！",3000 );
			 return;
		 }
		 if( menu.url == "" && $scope.privilage.depth > 2 ){
			 toaster.error( "","请输入菜单URL！",3000 );
			 return;
		 }
		 var from = new FormData();
		 for( var prop in menu ){
			 from.append(prop,menu[prop]);
		 }
		 var file = $("#menuIcon").find("input[type='file']")[0].files[0];
		 if( file ){
			 from.append("file",file );
		 }
		 
		 if(menu.code == "addMenu"){
			 if( menu.category == "menu" ){
				 toaster.error( "","暂不支持添加三级菜单",3000 );
				 return;
			 }

			 $http({
	             method:'POST',
	             url:urls.ms+"/accountMenu/addMenu.do",
	             data: from,
	             headers: {'Content-Type':undefined},
	             transformRequest: angular.identity
	          }).success( function ( data ){
	        	  //;
	          	  data = JSON.parse( data );
//	        	  if( data.success ){
//						listPrivilage();
//						toaster.success( "",data.msg,3000 );
//	       			}else{
//		       			toaster.error( "",data.msg,3000 );
//	       		   }
	        	  if( data.respCode === "S" ){
	        		  listPrivilage();
	 			}else{
	 				toaster.error( "",data.errorType,3000 );
	 		   }
	          }); 
		 }else{
			 
			 $http({
	             method:'POST',
	             url:urls.ms+"/accountMenu/updateMenu.do",
	             data: from,
	             headers: {'Content-Type':undefined},
	             transformRequest: angular.identity
	          }).success( function ( data ){
	        	  //;
	          	  data = JSON.parse( data );
//	        	  if( data.success ){
//						listPrivilage();
//						toaster.success( "",data.msg,3000 );
//	       			}else{
//		       			toaster.error( "",data.msg,3000 );
//	       		   }
	        	  if( data.respCode =="S" ){
	        		  listPrivilage();
	 			}else{
	 				toaster.error( "",data.errorType,3000 );
	 		   }
	          }); 
		 }
	 }
	 
	 function cancel(){
		 $scope.enableView = false;
	 }
	 
	 
	 function changeStatus( status ){
		 $scope.privilage.status=status;
	 }
	 function changeCommon( common ){
		 $scope.privilage.common=common;
	 }
	 
	 function addChild( $event ){
		 $scope.enableView = true;
		 var nodeScope = angular.element( $event.target ).scope().$nodeScope;
		 var nodeValue = nodeScope.$modelValue;
		 
		 $scope.privilage = {
				 depth: nodeScope.depth()+1,
				 status:"ENABLED",
				 common:2,
				 parentId:nodeValue.id ? nodeValue.id : -1,
				 parentText:nodeValue.text,
				 category:nodeValue.category,
				 code:"addMenu"
		 };
	 }
	 
     
	 function edit( $event ){
		 $scope.enableView = true;
		 var nodeScope = angular.element( $event.target ).scope().$nodeScope;
		 var parentNodeScope = nodeScope.$parentNodeScope
		 var nodeValue = nodeScope.$modelValue;
		 var parentNodValue = parentNodeScope.$modelValue;
		
		 if( parentNodValue.parentId ){
			 nodeValue.parentText = parentNodValue.text
		 }
		 nodeValue.depth = nodeScope.depth();
		 $scope.privilage = angular.copy(nodeValue);
	 }
	 
	 function deletePrivilage( node){
		
		 messager.confirm("确认删除?",function( modalInstance ){
			 $http({
				 method:'POST',
				 url:urls.ms+"/accountMenu/deleteMenu.do",
				 data: {
					 id : node.id,
					 category:node.category
				 },
				 headers: {'Content-Type':"application/x-www-form-urlencoded"},
				 transformRequest: function (data) {
					 return $.param(data);
				  }
			 }).success( function ( data ){
				 modalInstance.close();
				 if( data.success ){
					 listPrivilage();
					 toaster.success( "",data.msg,3000 );
				 }else{
					 toaster.error( "",data.msg,3000 );
				 }
			 }); 
		 });
		 
	 }
	 
	 function listPrivilage(){
		 $.ajax({
			 url:urls.ms+"/accountMenu/getPrivilegeTree.do",
			 data:{
				 rid:1
			 },
			 datType:"json",
			 type:"post",
			 success:function( data ){
				 $scope.$broadcast('angular-ui-tree:collapse-all');
				 $scope.$apply(function(){
					 $scope.privilageList = data;
				 });
			 }
		 })
	 }
	 
     //初始化
     init();
});