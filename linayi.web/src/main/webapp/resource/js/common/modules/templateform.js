(function ( window, document ) {
'use strict';
angular.module('templateform', ['ui.bootstrap'])
.constant('templateformConfig', {
	
})
.service('templateform', ['$rootScope','templateformConfig','$modal',function ($rootScope, templateformConfig,$modal) {
	
	function getTemplate( options ){
		
		var btns = "";
		if( options.buttons ){
			for( var i = 0; i < options.buttons.length; i++ ){
				var btn = options.buttons[i];
				btns = btns + "<a ng-click='_button_handle_"+i+"( "+i+" )' class='btn btn-primary' href='javascript:void(0);'>\
									<i class='fa "+btn.iconCls+"'></i>"
									+btn.text+
							   "</a>";
			}
		}else{
			btns = "<a ng-click='_confirm( true )' class='btn btn-primary' href='javascript:void(0);'>\
						<i class='fa fa-save'></i>确认\
				   </a>\
				   <a ng-click='_confirm( false )' class='btn btn-primary' href='javascript:void(0);'>\
				 		<i class='fa fa-close'></i>取消\
				   </a>";
		}
		if( !options.ngTemplate && options.url){
			options.url = options.url.indexOf("?") > 0 ? options.url+"&="+new Date().getTime() : options.url+"?&="+new Date().getTime();
		}
		return "<div class='modal-content modal-primary'>\
					<div class='modal-header'>\
					<button type='button' class='close' data-dismiss='modal' ng-click='_confirm( false )' aria-hidden='true'><i class=' fa fa-close'></i></button>\
					      <h5 class='modal-title'>"+options.title+"</h5>\
					</div>\
					<div class='modal-body'>\
					<!-- body -->\
					<div ng-include=\"'"+options.url+"'\"></div>\
					<!-- end body -->\
					</div>\
					<div class='modal-footer' style='text-align: center;'>"
					    +btns+
					"</div>\
				</div>";
	}
	
	function open( options,callback ){
		//options 默认值：{dataName:data}
		var defaultOptions = {
				windowClass:  "",
				templateUrl: null,
				template: options.templateUrl ? null : (options.template ? options.template : getTemplate( options )  ),
				scope : null,
		        size: "lg",
		        backdrop: "static",
	            keyboard: false,
	            resolve:options.resolve ? options.resolve : {},
	            style:options.style,
        		controller: function( $scope,$modalInstance ){
        			$scope._style=options.style;
    	        	if( options.buttons ){
    	        		for( var i = 0; i < options.buttons.length; i++ ){
    	        			$scope[ "_button_handle_" + i ] = function( i ){
								options.buttons[i].handle.call( this, $modalInstance,$scope[ options.dataName ],$scope );
    	        			}
    	        		}
    	        	}
    	        	//初始化数据
    	        	if( options.dataName ){
    	        		$scope[ options.dataName ] = options.data ? options.data : {};
    	        	}else if( options.data ){
    	        		options.dataName = "data";
    	        		$scope[ options.dataName ] = options.data;
    	        	}
    	        	//确认，取消
    				$scope._confirm = function ( flag ) {
    					if( !flag ){
    						$modalInstance.dismiss();
    						return;
    					}
    					if( callback){
    						callback.call( this, $modalInstance, $scope[ options.dataName ],$scope );
    					}else{
    						$modalInstance.close();
    					}
    				};
    				//打开事件处理
    				if( options.onOpen ){
    					options.onOpen.call( this, $modalInstance, $scope[ options.dataName ],$scope );
    				}
    	        }
			}
		
		defaultOptions = options ? angular.extend( defaultOptions,options )  : {};
		var buttons = options.buttons;
		//打开模态框
		var modalInstance = $modal.open( defaultOptions );
		return modalInstance;
	}
	return {
		open:open,
		close:close
	}
}])


})(window, document);
