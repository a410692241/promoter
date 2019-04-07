<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript"
	src="resource/js/community/services/CommunityAccountService.js"></script>
<script type="text/javascript"
	src="resource/js/community/controllers/CommunityAccountController.js"></script>
</head>
<body>
	<div class="row" ng-controller="communityAccountCtrl">
		<div class="col-xs-12 col-md-12">
			<div class="widget">
				<div class="widget-header ">
					<span class="widget-caption"></span>
					<div class="widget-buttons">
						<a href="" widget-collapse></a> <a href="" widget-maximize></a>
					</div>
				</div>
				<div class="widget-body">
					<form class="form-inline">
						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">登陆账号</span> <input
									ng-model="search.account" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">地区编码</span> <input
									ng-model="search.areaCode" type="text" class="form-control">
							</div>
						</div>

						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">社区网点ID</span> <input
									ng-model="search.communityId" type="text" class="form-control">
							</div>
						</div>
						<a ng-click="list()" class="btn btn-primary form-group-margin"
							href="javascript:void(0);"> <i class="fa fa-search"></i>搜索
						</a> 

					</form>

					<!-- 表格 -->
					<table id="communityAccountList" style="width: 100%"></table>
					<!-- 分页 -->
					<div id="communityAccountPager"></div>

				</div>
			</div>
		</div>
	</div>
	<!--消息框 -->
	<toaster-container
		toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>