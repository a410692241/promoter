<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript"
	src="resource/js/goods/services/GoodsSku1Service.js"></script>
<script type="text/javascript"
	src="resource/js/goods/controllers/GoodsSku1Controller.js"></script>
</head>
<body>
	<div class="row" ng-controller="userCtrl">
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
								<span class="input-group-addon">超市名称</span> <input
									ng-model="search.supermarketName" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">商品名称</span> <input
									ng-model="search.name" type="text" class="form-control">
							</div>
						</div>
<!-- 						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">创建时间</span> <input type="text"
									class="form-control" datetimepicker
									ng-model="search.createTimeStart" />
							</div>
							至
							<div class="input-group">
								<input type="text" class="form-control" datetimepicker
									ng-model="search.createTimeEnd" />
							</div>
						</div> -->
						<a ng-click="list()" class="btn btn-primary form-group-margin"
							href="javascript:void(0);"> <i class="fa fa-search"></i>搜索
						</a>
					</form>
					<!-- 表格 -->
					<table id="GoodsSku1List" style="width: 100%"></table>
					<!-- 分页 -->
					<div id="GoodsSku1Pager"></div>
				</div>
			</div>
		</div>
	</div>
	<!--消息框 -->
	<toaster-container
		toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>