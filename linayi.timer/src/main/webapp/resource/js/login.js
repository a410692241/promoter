'use strict';

angular.module('app', ["toaster","ngTouch"]).controller("AppCtrl",function( $rootScope,$scope,$window,toaster){
	
	$scope.user={
	}
	$scope.login=function(){
		$.ajax({ 
			url: urls.ms+'/account/employeeLogin.do',
			data:$scope.user,
			dataType:"json",
			type: "post", 
            error:function(){
            	toaster.error( "","服务器出错！",3000 );
            },
			success: function(result){
				console.log(result);
				
				if (result.respCode=="S"){
					window.location.href=urls.testMs+"/accountMenu/list.do";
				}else{
					$scope.$apply(function(){
						toaster.error( "",result.errorMsg,1903 );
					})
				}
			}
		});
	}
	var startSref = 1000;
	function setSref( menuTree ){
		for( var  i = 0 ; i < menuTree.length; i++ ){
			menuTree[i].sref = "app."+ ( ++startSref );
			if( menuTree[i].children ){
				setSref( menuTree[i].children );
			}
		}
	}
	
	document.onkeypress = function( event ) {
		var e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$scope.login();
		}
	}
});