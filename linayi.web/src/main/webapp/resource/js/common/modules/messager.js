(function ( window, document ) {
'use strict';
angular.module('messager', ['ui.bootstrap'])
.constant('messagerConfig', {
	
	
})
.service('messager', ['$rootScope', 'messagerConfig','$modal',function ($rootScope, messagerConfig,$modal) {
	
	function alert( message,callback,options ){
		
		var content = message;
		if( typeof message != "string" ){
			content = message.content;
		}
		message = {
				content:content,
				type:"info",
				icon:"fa fa-info",
				alert:true
		}
		confirm( message,callback,options );
	}
	
	function info( message,callback,options ){
		var content = message;
		if( typeof message != "string" ){
			content = message.content;
		}
		message = {
				content:content,
				type:"info",
				icon:"fa fa-envelope",
				alert:false
		}
		confirm( message,callback,options );
	}
	
	function warn( message,callback,options ){
		var content = message;
		if( typeof message != "string" ){
			content = message.content;
		}
		message = {
				content:content,
				type:"warning",
				icon:"fa fa-warning",
				alert:false
		}
		confirm( message,callback,options );
	}
	
	function success( message,callback,options ){
		var content = message;
		if( typeof message != "string" ){
			content = message.content;
		}
		message = {
				content:content,
				type:"success",
				icon:"glyphicon glyphicon-check",
				alert:false
		}
		confirm( message,callback,options );
	}
	
	function danger( message,callback,options ){
		
		var content = message;
		if( typeof message != "string" ){
			content = message.content;
		}
		message = {
				content:content,
				type:"danger",
				icon:"glyphicon glyphicon-fire",
				alert:false
		}
		confirm( message,callback,options );
	}
	
	function getTemplate(){
		return "<div class='modal-content modal-primary'>\
			<div class='modal-header'>\
				<i class='{{_message.icon}}'></i>\
		   </div>\
		   <div class='modal-body'>\
				<h4>{{_message.content}}</h4>\
			</div>\
		   <div class='modal-footer'>\
		       <button type='button' class='btn btn-{{_message.type}}' ng-click='_confirm( true )'>是</button>\
		       <button type='button' ng-hide='_message.alert' class='btn btn-{{_message.type}}' ng-click='_confirm( false )''>否</button>\
		   </div>\
		</div>";
	}
	
	function confirm( message,callback,options ){
		
		options = options ? options : {};
		var content = message;
		if( typeof message != "string" ){
			content = message.content;
		}
		message = {
				content:content,
				type:message.type ? message.type : "warning",
				icon:message.icon ? message.icon : "fa fa-warning",
				alert:message.alert ? message.alert : false
		}
		//打开模态框
		var modalInstance = $modal.open({
			windowClass: options.windowClass ? options.windowClass +" modal-message " : " modal-message modal-"+message.type,
			templateUrl: options.templateUrl ? options.templateUrl : null,
			template: options.templateUrl ? null : (options.template ? options.template : getTemplate()  ),
	        controller:  function( $scope,$modalInstance ){
	        	$scope._message = message;
	        	//是，或者否
				$scope._confirm = function ( flag ) {
					if( !flag ){
						$modalInstance.dismiss();
						return;
					}
					if( callback){
						callback.call( this, $modalInstance );
					}
				};
	        } ,
	        size: options.size ? options.size : "sm",
	        backdrop: options.backdrop ? options.backdrop : true,
            keyboard: options.keyboard ? options.keyboard : true,
	    });
	}
	return {
		info:info,
		warn:warn,
		danger:danger,
		success:success,
		confirm:confirm,
		alert:alert
	}
}])


})(window, document);
