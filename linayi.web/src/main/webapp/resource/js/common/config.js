var app =
    angular.module('app')
        .config(
        [
            '$controllerProvider', '$compileProvider', '$filterProvider', '$provide','$httpProvider',
            function($controllerProvider, $compileProvider, $filterProvider, $provide,$httpProvider) {
            	
                app.controller = $controllerProvider.register;
                app.directive = $compileProvider.directive;
                app.filter = $filterProvider.register;
                app.factory = $provide.factory;
                app.service = $provide.service;
                app.constant = $provide.constant;
                app.value = $provide.value;
                app.httpProvider = $httpProvider;
                app.linayi={
                	defaultImage:"resource/image/addImg.png"
                }
            }
        ]);


app.config(function($breadcrumbProvider) {
    $breadcrumbProvider.setOptions({
//        template: '<ul class="breadcrumb"><li><i class="fa fa-home"></i><a href="#">Home</a></li><li ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract"><a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a><span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span></li></ul>'
    		template: '<ul class="breadcrumb"><li><i class="fa fa-home"></i><a  ui-sref="app.dashboard" href="javascript:void(0)">首页</a></li><li ng-repeat="step in steps" ng-class="{active: $last}" ng-switch="$last || !!step.abstract"><a ng-switch-when="false" href="{{step.ncyBreadcrumbLink}}">{{step.ncyBreadcrumbLabel}}</a><span ng-switch-when="true">{{step.ncyBreadcrumbLabel}}</span></li></ul>'
    });
});