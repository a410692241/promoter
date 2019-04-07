<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://www.lay.com/option" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/task/services/DeliveryTaskService.js"></script>
<script type="text/javascript" src="resource/js/task/controllers/DeliveryTaskController.js"></script>
</head>
<body>
<div class="row" ng-controller="deliveryTaskCtrl">
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
                    <span class="input-group-addon">社区</span>
            		<ui-select
					   	on-select="search.communityId = $item.code"
						ng-init='options=<c:selectBySQL sqlId="selectCommunityList"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
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
                  <input ng-model="search.deliveryTaskId" type="text" class="form-control">
                    </div>
                </div>
				<div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">任务状态</span>
                           <ui-select
							   	on-select="search.status = $item.code"
								ng-init='options=<cl:getByEnum enumName="com.linayi.enums.ProcureStatus" nameWithCode="getProcureStatus;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
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
	                        <span class="input-group-addon">顾客</span>
	                  		<input ng-model="search.receiverName" type="text" class="form-control">
	                    </div>
	                </div>
                   <div class="form-group form-group-margin">
	                    <div class="input-group">
	                        <span class="input-group-addon">下单时间</span>
	   						<input type="text" class="form-control" datetimepicker ng-model="search.createTimeStart"/>
	                        <span class="input-group-addon">至</span>
	   						<input type="text" class="form-control" datetimepicker ng-model="search.createTimeEnd"/>
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
				</form>
				
				<!-- 表格 -->
                <table id="deliveryTaskList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="deliveryTaskPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>