<%@ page language="java" contentType="text/html; charset=utf-8"%>

<!DOCTYPE html>
<!--
BeyondAdmin - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.6
Version: 1.6.0
Purchase: https://wrapbootstrap.com/theme/beyondadmin-adminapp-angularjs-mvc-WB06R48S4
-->

<html xmlns="http://www.w3.org/1999/xhtml" ng-app="app" ng-controller="AppCtrl">
	
    <!-- Head -->
    <head>
        <meta charset="utf-8" />
        <title page-title></title>
        
        
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
		<!-- styles -->
		 <link href="resource/others/BeyondAdmin/lib/modules/angularjs-toaster/toaster.css" rel="stylesheet" />
		 
</head>
<body>
<div class="login-container animated fadeInDown">
    <div class="loginbox bg-white">
        <div class="loginbox-title">登陆</div>
        <div class="loginbox-or">
            <div class="or-line"></div>
        </div>
        <div class="loginbox-textbox">
            <input type="text" ng-model="user.account" class="form-control" placeholder="账号" />
        </div>
        <div class="loginbox-textbox">
            <input type="password" ng-model="user.password" class="form-control" placeholder="密码" />
        </div>
        <div class="loginbox-forgot">
            <a href=""></a>
        </div>
        <div class="loginbox-submit">
            <input type="button" ng-click="login()" class="btn btn-primary btn-block" value="登陆">
        </div>
        <div class="loginbox-signup">
            <a href="register.html"></a>
        </div>
    </div>
    <div class="logobox">
    </div>
</div>
<toaster-container toaster-options="{'position-class': 'toast-center', 'close-button':true,'limit':1}"></toaster-container>
<!-- Scripts -->
<script src="resource/others/BeyondAdmin/lib/jquery/jquery.min.js"></script>
<script src="resource/others/BeyondAdmin/lib/angular/angular.js"></script>
<script src="resource/others/BeyondAdmin/lib/angular/angular-touch/angular-touch.js"></script>
<script src="resource/others/BeyondAdmin/lib/angular/angular-animate/angular-animate.js"></script>
<script src="resource/others/BeyondAdmin/lib/modules/angularjs-toaster/toaster.js"></script>

<script src="resource/js/common/common.js"></script>
<script src="resource/js/login.js"></script>
	<script type="text/javascript">
	// 在被嵌套时就刷新上级窗口
	if(window.parent != window ){
		window.parent.location.reload(true);
	}else if( window.location.href.indexOf("login.jsp") < 0 ){
		window.location.reload(true);
	}
</script>
</body>
</html>