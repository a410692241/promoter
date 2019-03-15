'use strict';
angular.module('app')
    .run(
        [
            '$rootScope', '$state', '$stateParams',
            function($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]
    )
    .config(
        [
            '$stateProvider', '$urlRouterProvider',
            function($stateProvider, $urlRouterProvider) {
            	
                $urlRouterProvider
                    .otherwise('/app/dashboard');
                $stateProvider
                    .state('app', {
                        abstract: true,
                        url: '/app',
                        templateUrl: 'html/common/layout.html',
                    })
                    .state('app.dashboard', {
                        url: '/dashboard',
                        templateUrl: 'html/common/dashboard.html',
                        ncyBreadcrumb: {
                            label: '首页',
                            description: ''
                        },
                        resolve: {
                            deps: [
                                '$ocLazyLoad',
                                function($ocLazyLoad) {
                                    return $ocLazyLoad.load({
                                        serie: true,
                                        files: [
                                            'resource/others/BeyondAdmin/lib/jquery/charts/sparkline/jquery.sparkline.js',
                                            'resource/others/BeyondAdmin/lib/jquery/charts/easypiechart/jquery.easypiechart.js',
                                            'resource/others/BeyondAdmin/lib/jquery/charts/flot/jquery.flot.js',
                                            'resource/others/BeyondAdmin/lib/jquery/charts/flot/jquery.flot.resize.js',
                                            'resource/others/BeyondAdmin/lib/jquery/charts/flot/jquery.flot.pie.js',
                                            'resource/others/BeyondAdmin/lib/jquery/charts/flot/jquery.flot.tooltip.js',
                                            'resource/others/BeyondAdmin/lib/jquery/charts/flot/jquery.flot.orderBars.js',
                                            'resource/others/BeyondAdmin/app/controllers/dashboard.js',
                                            'resource/others/BeyondAdmin/app/directives/realtimechart.js'
                                        ]
                                    });
                                }
                            ]
                        }
                    });
                
	                var startSref = 1000;
	            	function setSref( menuTree ){
	            		for( var  i = 0 ; i < menuTree.length; i++ ){
	            			menuTree[i].sref = "app."+ ( ++startSref );
	            			if( menuTree[i].children ){
	            				setSref( menuTree[i].children );
	            			}
	            		}
	            		return menuTree;
	            	}
                
                    var menuTree = setSref( _menuTree );
                	for( var i = 0 ; i < menuTree.length; i++ ){
                		if( menuTree[i].children ){
                			for( var j = 0 ; j < menuTree[i].children.length; j++ ){
                				$stateProvider.state( menuTree[i].children[j].sref, {
                					url: menuTree[i].children[j].sref,
                					templateUrl: urls.testMs+menuTree[i].children[j].url,
                					ncyBreadcrumb: {
                						label: menuTree[i].children[j].text,
                						description: ''
                					},
                					resolve: {
                						deps: [
            						       '$ocLazyLoad',
            						       function($ocLazyLoad) {
            						    	   return $ocLazyLoad.load( [
						    	                             'ui.select', 
						    	                             'ngTagsInput', 
						    	                             'minicolors',
						    	                             'dropzone',
						    	                             'vr.directives.slider',
						    	                             'datetimepicker',
						    	                             'jqgrid',
						    	                             'angularBootstrapNavTree',
						    	                             'ui.tree',
						    	                             'ui-tree-nestable',
						    	                             'templateform',
						    	                             'messager',
						    	                             'kindeditor',
						    	                             'cgBusy'
            						    	        ] );
            						       }
                						]
                					}
                				});
                				
                			}
                		}
                	}
            }
        ]
    );