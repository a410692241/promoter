angular.module("datetimepicker",[])
.directive("datetimepicker",function($filter,$timeout,$parse){
    return {
        restrict: "EA",
        require : "ngModel",
        link: function (scope, element, attrs, ctrl) {
        	
        	element.css("padding","0 12");
        	
        	var options = {};
        	if( attrs.options ){
        		if ( attrs.options.substring(0, 1) != '{' ){
        			attrs.options = '{' + attrs.options + '}';
				}
        		options = (new Function('return ' + attrs.options ))();
        	}
        
        	if( options.timepicker === false ){
        		options.format = options.format || 'Y-m-d';
        	}
        	if( options.datepicker === false ){
        		options.format = options.format || 'H:i:s';
        	}
        	
        	options.format = options.format || 'Y-m-d H:i:s';
        	
        	options.lang = options.lang || 'zh';
        	options.value=element.val();
        	
        	$.datetimepicker.setLocale( options.lang );
        	
        	if (attrs.ngModel && element.is('select,input,textarea')) {
        		element.bind('change', function() {
        			element.trigger('input');
        		});
        	}
        	function callPlugin() {
                 $timeout(function() {
                	 element["datetimepicker"].call(element, options);
                	 var parseFun = $parse( attrs.ngModel );
                	 var parsedValue = parseFun( scope );
                	 if( parsedValue && parsedValue > 0 ){
                		 element.val( new DateFormatter({}).guessDate( new Date( parsedValue).format("yyyy-MM-dd HH:mm:ss"), options.format) );
                	 }
                 }, 0, false);
            }
        	callPlugin();
        }
    }
});