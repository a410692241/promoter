<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/order/services/EvaluateService.js"></script>
<script type="text/javascript" src="resource/js/order/controllers/EvaluateController.js"></script>
</head>
<body>
<div class="row" ng-controller="evaluateCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a>
                    <a href="" widget-maximize></a>
                </div>
            </div>
            <div class="widget-body">
            	<form class="form-inline">
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">主键</span>
		                            <input ng-model="search.evaluateId" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">订单ID</span>
		                            <input ng-model="search.orderId" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">评价内容</span>
		                            <input ng-model="search.content" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">配送服务</span>
		                            <input ng-model="search.deliverScore" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">平台服务</span>
		                            <input ng-model="search.platformScore" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">社区ID</span>
		                            <input ng-model="search.communityId" type="text" class="form-control">
		                        </div>
		                    </div>
				  <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>

				  <a ng-click="edit()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-plus"></i>新增
				  </a>

				</form>

				<!-- 表格 -->
                <table id="evaluateList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="evaluatePager"></div>

            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>