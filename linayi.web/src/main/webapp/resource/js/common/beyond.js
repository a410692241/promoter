'use strict';

angular.module('app')
    .controller('AppCtrl',
    	['$rootScope', '$localStorage', '$state', '$timeout','$scope','templateform','toaster','messager',	
        function ($rootScope, $localStorage, $state, $timeout,$scope,templateform,toaster,messager) {
    		//统一默认配置
    		$rootScope.linayi=app.linayi;
    		
    		//统一HTTP请求处理
    		app.httpProvider.defaults.headers["X-Requested-With"]="XMLHttpRequest";
    		app.httpProvider.defaults.headers["Content-type"]="application/x-www-form-urlencoded;charset=UTF-8";
    		app.httpProvider.defaults.transformResponse=function( data, headersGetter, status ) {
    			//登陆失效处理
				if(status == 405){
					toaster.error("","登陆失效",3000);
	//				$.messager.alert("提示",responseText.msg,"info",function(){
	//					window.parent.location.href=urls.ms+"/index.jsp";
	//				});
				//无权限处理
				}else if( status == 403 ){
					alert("无权限");
	//				$.messager.alert("提示",responseText.msg);
				}
				return data;
		}
    	
    	//设置二级菜单用于首页显示
    	var seccondMenuItemList = new Array();
    	for( var i = 0 ; i<_menuTree.length; i++  ){
    		var children = _menuTree[i].children;
    		if( children ){
    			for( var j = 0 ; j < children.length; j++ ){
    				seccondMenuItemList.push( children[j] );
    			}
    		}
    	}
    	$scope.menuItemList = _menuTree;
    	$rootScope.seccondMenuItemList = seccondMenuItemList;
        	//退出登陆
        	$scope.logout=function(){
    			$.post(urls.ms+"/backstageUserAction/logout.do",function(data){
    				window.location.href="login.jsp";
    			});
        	}
        	//修改密码
        	$scope.changePwd=function(){
        		$scope.user={
        				oldPassword:"",
        				password:"",
        				rePassword:""
        		}
        		var modalInstance = templateform.open({
    				templateUrl:"html/partials/navbar/changePwd.html",
    				scope:$scope,
    				size:"xm"
    			});
        		$scope.cancel=function(){
        			modalInstance.dismiss();
        		}
        		$scope.save=function(){
        			if($scope.user.oldPassword == "" ){
    					toaster.error("","请输入原密码",3000);
    					return;
    				}
    				
    				if( $scope.user.password == "" ){
    					toaster.error("","请输入密码",3000);
    					return;
    				}
    				
    				if($scope.user.password.length < 6 ){
    					toaster.error("","请至少输入6位密码",3000);
    					return;
    				}
    				
    				if($scope.user.rePassword != $scope.user.password){
    					toaster.error("","两次输入的密码不一致",3000);
    					return;
    				}
    				try{
    					$.ajax({
    						url:urls.ms+"/backstageUserAction/changeUserPWD.do",
    						data:$scope.user,
    						dataType:"json",
    						type:"post"
    					}).then( function( data ){
    						$scope.$apply(function(){
    							toaster.error("",data.msg,3000);
    						});
    						if( data.success ){
    							modalInstance.close();
    						}
    					} );
    				}catch (e) {
						$scope.$apply(function(){
							toaster.error("","出错了",3000);
						});
    				}
        		}
        	}
        	
            $rootScope.settings = {
                skin: 'resource/others/BeyondAdmin/assets/css/skins/azure.min.css',
                color: {
                    themeprimary: '#2dc3e8',
                    themesecondary: '#fb6e52',
                    themethirdcolor: '#ffce55',
                    themefourthcolor: '#a0d468',
                    themefifthcolor: '#e75b8d'
                },
                rtl: false,
                fixed: {
                    navbar: false,
                    sidebar: false,
                    breadcrumbs: false,
                    header: false
                }
            };
            if (angular.isDefined($localStorage.settings))
                $rootScope.settings = $localStorage.settings;
            else
                $localStorage.settings = $rootScope.settings;

            $rootScope.$watch('settings', function () {
                if ($rootScope.settings.fixed.header) {
                    $rootScope.settings.fixed.navbar = true;
                    $rootScope.settings.fixed.sidebar = true;
                    $rootScope.settings.fixed.breadcrumbs = true;
                }
                if ($rootScope.settings.fixed.breadcrumbs) {
                    $rootScope.settings.fixed.navbar = true;
                    $rootScope.settings.fixed.sidebar = true;
                }
                if ($rootScope.settings.fixed.sidebar) {
                    $rootScope.settings.fixed.navbar = true;


                    //Slim Scrolling for Sidebar Menu in fix state
                    var position = $rootScope.settings.rtl ? 'right' : 'left';
                    if (!$('.page-sidebar').hasClass('menu-compact')) {
                        $('.sidebar-menu').slimscroll({
                            position: position,
                            size: '3px',
                            color: $rootScope.settings.color.themeprimary,
                            height: $(window).height() - 90,
                        });
                    }
                } else {
                    if ($(".sidebar-menu").closest("div").hasClass("slimScrollDiv")) {
                        $(".sidebar-menu").slimScroll({ destroy: true });
                        $(".sidebar-menu").attr('style', '');
                    }
                }

                $localStorage.settings = $rootScope.settings;
            }, true);

            $rootScope.$watch('settings.rtl', function () {
                if ($state.current.name != "persian.dashboard" && $state.current.name != "arabic.dashboard") {
                    switchClasses("pull-right", "pull-left");
                    switchClasses("databox-right", "databox-left");
                    switchClasses("item-right", "item-left");
                }

                $localStorage.settings = $rootScope.settings;
            }, true);

            $rootScope.$on('$viewContentLoaded',
                function (event, toState, toParams, fromState, fromParams) {
                    if ($rootScope.settings.rtl && $state.current.name != "persian.dashboard" && $state.current.name != "arabic.dashboard") {
                        switchClasses("pull-right", "pull-left");
                        switchClasses("databox-right", "databox-left");
                        switchClasses("item-right", "item-left");
                    }
                    if ($state.current.name == 'error404') {
                        $('body').addClass('body-404');
                    }
                    if ($state.current.name == 'error500') {
                        $('body').addClass('body-500');
                    }
                    $timeout(function () {
                        if ($rootScope.settings.fixed.sidebar) {
                            //Slim Scrolling for Sidebar Menu in fix state
                            var position = $rootScope.settings.rtl ? 'right' : 'left';
                            if (!$('.page-sidebar').hasClass('menu-compact')) {
                                $('.sidebar-menu').slimscroll({
                                    position: position,
                                    size: '3px',
                                    color: $rootScope.settings.color.themeprimary,
                                    height: $(window).height() - 90,
                                });
                            }
                        } else {
                            if ($(".sidebar-menu").closest("div").hasClass("slimScrollDiv")) {
                                $(".sidebar-menu").slimScroll({ destroy: true });
                                $(".sidebar-menu").attr('style', '');
                            }
                        }
                    }, 500);

                    window.scrollTo(0, 0);
                });
        }
    ]);