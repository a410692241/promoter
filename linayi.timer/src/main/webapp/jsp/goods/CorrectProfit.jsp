<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/goods/services/CorrectService.js"></script>
<script type="text/javascript" src="resource/js/goods/controllers/CorrectProfitController.js"></script>
</head>
<body>
<div class="row" ng-controller="correctProfitCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
        		<form class="form-horizontal ng-pristine ng-valid" role="form" id="supermarketInfoForm" method="post" enctype="multipart/form-data">
					<input  type="hidden" ng-model="correct.correctId"/>
				<div class="row">
					<div class="form-group col-lg-6 col-xs-6 col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">分享员占比</span>
								<input class="form-control" style="text-align: right;" ng-required="required" type="text" ng-model="correctProfit.sharerPercent">
								<span class="input-group-addon">%</span>
							</div>
					</div>
					<div class="form-group col-lg-6 col-xs-6 col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">收取供应商销售服务费用占比</span>
								<input class="form-control" style="text-align: right;" ng-required="required" type="text" ng-model="correctProfit.supplierPercent">
								<span class="input-group-addon">%</span>
							</div>
					</div>
					<div class="form-group col-lg-6 col-xs-6 col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">社区网点占比</span>
								<input class="form-control" style="text-align: right;" ng-required="required" type="text" ng-model="correctProfit.communityPercent">
								<span class="input-group-addon">%</span>
							</div>
					</div>
					<div class="form-group col-lg-6 col-xs-6 col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">信用卡刷卡手续费占比</span>
								<input class="form-control" style="text-align: right;" ng-required="required" type="text" ng-model="correctProfit.cardServiesPercent">
								<span class="input-group-addon">%</span>
							</div>
					</div>
				</div>
				</form>
        </div>
        <div class="widget" style="display: flex;justify-content: center;align-items: center;">
				<a href="javascript:void(0);" class="btn btn-default" ng-click="saveProfitRule()">保存</a>
        </div>
     </div>
</div>
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
