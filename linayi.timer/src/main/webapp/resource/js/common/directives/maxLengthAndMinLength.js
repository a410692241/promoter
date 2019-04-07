angular.module('app')
.directive('maxLength', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        compile: function(element,attrs,ngModelCtrl){
        	return function($scope, $element, $attrs, $ngModel){
        		var maxLength = Number($attrs.maxLength);
        		   $scope.$watch( $attrs.ngModel,function(newValue,oldValue){
	                   if(!newValue){
	                      return;
	                    }
	                   console.info( newValue.length >  maxLength );
	                    if ( newValue.length >  maxLength) {
	                    	 newValue = newValue.substring(0, maxLength);
	                         element.val( newValue );
	                         $ngModel.$setViewValue( newValue );
	//                       scope.$eval("scope."+attr.ngModel + "='" + newValue +"'");
	                   }
                });
        	}
        }
    }
});
/**
 * 控制输入最小值
 */
angular.module('app').directive('numberMin', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attr, linkfn) {
            var numberMin = Number(attr.minLength);
            scope.$watch(attr.ngModel,function(newValue,oldValue){
               if(!newValue){
                  return;
                }
                if (angular.isNumber(numberMin) && !isNaN(numberMin)) {
                  if(oldValue == '-'){
                     var exp = attr.ngModel + '=' + undefined;
                     element.val(oldValue);
                     scope.$eval(exp);
                  }else if(newValue < numberMin){
                     var exp = attr.ngModel + '=' + oldValue;
                     element.val(oldValue);
                     scope.$eval(exp);
                  }
               }
            });
        }
    }
})
/**限制只能输入数字*/
.directive('numberBox', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        compile: function(element,attrs,ngModelCtrl){
        	return function($scope, $element, $attrs, $ngModel){
        		var maxLength = Number($attrs.maxLength);
        		   $scope.$watch( $attrs.ngModel,function(newValue,oldValue){
	                   if(!newValue){
	                      return;
	                    }
	                    if ( !/^\d{1,}\d{0,}$/.test( newValue ) ) {
	                         element.val( oldValue );
	                         $ngModel.$setViewValue( oldValue );
	                   }
                });
        	}
        }
    }
});