<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<!--
BeyondAdmin - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.6
Version: 1.6.0
Purchase: https://wrapbootstrap.com/theme/beyondadmin-adminapp-angularjs-mvc-WB06R48S4
-->

<html xmlns="http://www.w3.org/1999/xhtml" ng-app="app" ng-controller="AppCtrl" style="height: 100%">
	
    <!-- Head -->
    <head>
        <meta charset="utf-8" />
        <title page-title></title>
        <base href="../" />
        
        <meta name="description" content="blank page" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="shortcut icon" href="resource/others/BeyondAdmin/assets/img/favicon.png" type="image/x-icon">

        <!--Basic Styles-->
        <link href="resource/others/BeyondAdmin/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link ng-if="settings.rtl" ng-href="resource/others/BeyondAdmin/assets/css/bootstrap-rtl.min.css" rel="stylesheet" />
        <link href="resource/others/BeyondAdmin/assets/css/font-awesome.min.css" rel="stylesheet" />
        <link href="resource/others/BeyondAdmin/assets/css/weather-icons.min.css" rel="stylesheet" />

        <!--Fonts-->
        <style>
	        @@font-face {
	            font-family: 'WYekan';
	            src: url('resource/others/BeyondAdmin/assets/fonts/BYekan.woff') format('woff');
	            font-weight: normal;
	            font-style: normal;
	        }
    	</style>
        <link href="http://fonts.googleapis.com/earlyaccess/droidarabickufi.css" rel="stylesheet" type="text/css" />
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300"
              rel="stylesheet" type="text/css">
              
        <!--Beyond styles-->
        <link ng-if="!settings.rtl" href="resource/others/BeyondAdmin/assets/css/beyond.min.css" rel="stylesheet" />
        <link ng-if="settings.rtl" href="resource/others/BeyondAdmin/assets/css/beyond-rtl.min.css" rel="stylesheet" />
        <link href="resource/others/BeyondAdmin/assets/css/demo.min.css" rel="stylesheet" />
        <link href="resource/others/BeyondAdmin/assets/css/typicons.min.css" rel="stylesheet" />
        <link href="resource/others/BeyondAdmin/assets/css/animate.min.css" rel="stylesheet" />
        <link ng-href="{{settings.skin}}" rel="stylesheet" type="text/css" />
        <link href="resource/css/common/common.css" rel="stylesheet" />
        <link href="resource/others/BeyondAdmin/lib/modules/angularjs-toaster/toaster.css" rel="stylesheet" />
    </head>
    <!-- /Head -->
    
    <!-- Body -->
    <body style="height: 100%">
    	<script type="text/javascript">
			var privilegeCodeList = "${sessionScope.userPrivilegeCodeList}".replace(/\s+/g, "").replace("[", "").replace("]", "").split(",");
			var _menuTree = <%=JSONObject.toJSONString( request.getAttribute( "privilegeTree" ) ) %>;
		</script>
        <div ui-view autoscroll="false" style="height: 100%"></div>

        <!-- Scripts -->
        <script src="resource/others/BeyondAdmin/lib/jquery/jquery.min.js"></script>
        <script src="resource/others/BeyondAdmin/lib/jquery/jquery.parser.js"></script>
        <script type="text/javascript" src="resource/others/BeyondAdmin/lib/jquery/jquery.form.js"></script>
        <script type="text/javascript">
	        $(document).ajaxComplete(function(event,request, settings){
				var status = request.status;
				var responseText = $.parseJSON(request.responseText);
				//登陆失效处理
				if(status == 405){
					alert( responseText.msg );
					window.parent.location.href=urls.ms+"/login.jsp";
				//无权限处理
				}else if( status == 403 ){
					alert( responseText.msg );
				}
			});
        </script>
        <script src="resource/others/BeyondAdmin/lib/jquery/bootstrap.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular.js"></script>

        <script src="resource/others/BeyondAdmin/lib/utilities.js"></script>

        <script src="resource/others/BeyondAdmin/lib/angular/angular-animate/angular-animate.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-cookies/angular-cookies.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-resource/angular-resource.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-sanitize/angular-sanitize.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-touch/angular-touch.js"></script>

        <script src="resource/others/BeyondAdmin/lib/angular/angular-ui-router/angular-ui-router.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-ocLazyLoad/ocLazyLoad.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-ngStorage/ngStorage.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-ui-utils/angular-ui-utils.js"></script>
        <script src="resource/others/BeyondAdmin/lib/angular/angular-breadcrumb/angular-breadcrumb.js"></script>

        <script src="resource/others/BeyondAdmin/lib/angular/angular-ui-bootstrap/ui-bootstrap.js"></script>
        <script src="resource/others/BeyondAdmin/lib/jquery/slimscroll/jquery.slimscroll.js"></script>
        
        <script src="resource/others/BeyondAdmin/lib/modules/angularjs-toaster/toaster.js"></script>



        <script src="resource/js/common/common.js"></script>
        <script src="resource/js/common/date.js"></script>
        <script src="resource/js/common/download.js"></script>
        <!-- App Config and Routing Scripts -->
        <script src="resource/js/common/app.js"></script>
        <script src="resource/js/common/config.js"></script>
        <script src="resource/js/common/config.lazyload.js"></script>
        <script src="resource/js/common/config.router.js"></script>
        
        <script src="resource/js/common/filters/filter.js"></script>
        <script src="resource/js/common/modules/templateform.js"></script>
        <script src="resource/js/common/modules/messager.js"></script>
        <script src="resource/js/common/beyond.js"></script>
    
    
        <!-- Layout Related Directives -->
        <script src="resource/others/BeyondAdmin/app/directives/loading.js"></script>
        <script src="resource/others/BeyondAdmin/app/directives/skin.js"></script>
        <script src="resource/others/BeyondAdmin/app/directives/sidebar.js"></script>
        <script src="resource/others/BeyondAdmin/app/directives/header.js"></script>
        <script src="resource/others/BeyondAdmin/app/directives/navbar.js"></script>
        <script src="resource/others/BeyondAdmin/app/directives/chatbar.js"></script>
        <script src="resource/others/BeyondAdmin/app/directives/widget.js"></script>
        <script src="resource/js/common/directives/initModel.js"></script>
        <script src="resource/js/common/directives/uiImagebox.js"></script>
        <script src="resource/js/common/directives/maxLengthAndMinLength.js"></script>
        <script src="resource/js/common/privilege.js"></script>
    </body>
    <!--  /Body -->
</html>