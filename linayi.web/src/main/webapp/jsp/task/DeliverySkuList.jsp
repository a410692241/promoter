<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/task/services/DeliverySkuService.js"></script>
<script type="text/javascript" src="resource/js/task/controllers/DeliverySkuController.js"></script>
</head>
<body>
<div class="row" ng-controller="deliverySkuCtrl">
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
		                            <input ng-model="search.deliverySkuId" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">任务ID</span>
		                            <input ng-model="search.taskId" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">商品ID</span>
		                            <input ng-model="search.goodsSkuId" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">数量</span>
		                            <input ng-model="search.quantity" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">操作</span>
		                            <input ng-model="search.option" type="text" class="form-control">
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
                <table id="deliverySkuList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="deliverySkuPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>