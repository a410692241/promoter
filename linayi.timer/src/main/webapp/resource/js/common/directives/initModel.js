//initModel
angular.module('app')
    .directive('initModel', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, el, attr) {
            	el[0].style.display="none";
            	var model = angular.fromJson( el[0].outerText );
            	if( model ){
            		scope.$parent[attr.initModel] = model;
            	}else{
            		scope.$parent[attr.initModel] = {};
            	}
            }
        };
});