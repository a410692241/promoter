<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/task/services/ProcurementTaskService.js"></script>
<script type="text/javascript" src="resource/js/task/controllers/ProcurementTaskController.js"></script>
<link rel="stylesheet" type="text/css" href="resource/css/common/window-edit.css">
</head>
<body>
<div class="row" ng-controller="procurementTaskCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header">
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
                          <span class="input-group-addon">社区</span>
            		<ui-select
					   	on-select="search.communityId = $item.code" 
					   	ng-init='
						   	options=<cx:out-sql sqlId="options.listCommunity"></cx:out-sql>;
						   	temp=options[-1]'
						   ng-model="temp" style="min-width: 230px">
				           <ui-select-match>{{$select.selected.name}}</ui-select-match>
				           <ui-select-choices repeat="item in options | filter:{name: $select.search}">
				               <span ng-bind-html="item.name | highlight: $select.search"></span>
				           </ui-select-choices>
				     </ui-select>
                       </div>
                   </div>
				<div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">任务编号</span>
		                   <input ng-model="search.taskNo" type="text" class="form-control">
                       </div>
                </div>
				<div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">任务状态</span>
                           <ui-select
							   	on-select="search.status = $item.code" 
							   	ng-init='
								   	options=<cx:out-options dictMapCd="procurementTaskStatus"></cx:out-options>;
								   	options.splice(0,0,{"name":"全部","code":""});
								   	temp=options[0]'
								   ng-model="temp" style="min-width: 200px">
						           <ui-select-match>{{$select.selected.name}}</ui-select-match>
						           <ui-select-choices repeat="item in options | filter:{name: $select.search}">
						               <span ng-bind-html="item.name | highlight: $select.search"></span>
						           </ui-select-choices>
						     </ui-select>
                       </div>
                   </div>
                   <div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">订单编号</span>
		                   <input number-box ng-model="search.orderId" type="text" class="form-control">
                       </div>
                   </div>
				  <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>
				  
				  <a ng-click="edit()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-plus"></i>采购
				  </a>
				  
				</form>
				
				<!-- 表格 -->
                <table id="procurementTaskList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="procurementTaskPager"></div>
				
            </div>
        </div>
    </div>
</div>
<table>
	<thead></thead>
	<tbody></tbody>
</table>
<script id="template-goods-list" type="text/template">
	<div style="padding-bottom:30px;">
        <tabset>
             <tab heading="任务商品">
					<table class="goods-list"></table>
             </tab>
             <tab heading="任务跟踪">
					<table class="goods-list-hist"></table>
             </tab>
         </tabset>
         <div class="horizontal-space"></div>
     </div>
</script>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>