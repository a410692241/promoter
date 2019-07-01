
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript"
	src="resource/js/goods/services/GoodsSku1Service.js"></script>
<script type="text/javascript"
	src="resource/js/goods/controllers/TaskList.js"></script>
</head>
<body>
	<div class="row" ng-controller="taskCtrl">
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
					<%--	<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">审核类型</span> <input
									ng-model="search.priceType" type="text" class="form-control">
							</div>
						</div>--%>

                        <div class="form-group form-group-margin">
                            <div class="input-group">
                                <span class="input-group-addon">审核类型</span>
                                <ui-select on-select="search.priceType=$item.code;"
                                           ng-init='options=<cl:getByEnum enumName="com.linayi.enums.AuditType" nameWithCode="getPriceTypeName;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                           ng-model="temp" style="min-width: 178px">
                                    <ui-select-match>{{$select.selected.name}}</ui-select-match>
                                    <ui-select-choices
                                            repeat="item in options | filter:{name: $select.search}">
                                        <span ng-bind-html="item.name | highlight: $select.search"></span>
                                    </ui-select-choices>
                                </ui-select>

                                <%--<ui-select on-select="search.status=$item.code;"--%>
                                           <%--ng-init='options=<cl:getByEnum enumName="com.linayi.enums.CorrectStatus" nameWithCode="getCorrectTypeName;name"/>;--%>
                                   <%--options.splice(0,0,{"name":"全部","code":""});'--%>
                                           <%--ng-model="temp" style="min-width: 178px">--%>
                                    <%--<ui-select-match>{{$select.selected.name}}</ui-select-match>--%>
                                    <%--<ui-select-choices--%>
                                            <%--repeat="item in options | filter:{name: $select.search}">--%>
                                        <%--<span ng-bind-html="item.name | highlight: $select.search"></span>--%>
                                    <%--</ui-select-choices>--%>
                                <%--</ui-select>--%>
                            </div>
                        </div>



						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">超市名称</span> <input
									ng-model="search.supermarkerName" type="text" class="form-control">
							</div>
						</div>
						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">条形码</span> <input
									ng-model="search.barcode" type="text" class="form-control">
							</div>
						</div>
                        <div class="form-group form-group-margin">
                            <div class="input-group">
                                <span class="input-group-addon">审核状态</span>
                                <ui-select on-select="search.manualAuditStatus=$item.code;"
                                           ng-init='options=<cl:getByEnum enumName="com.linayi.enums.AuditStatus" nameWithCode="getPriceTypeName;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                           ng-model="temp" style="min-width: 178px">
                                    <ui-select-match>{{$select.selected.name}}</ui-select-match>
                                    <ui-select-choices
                                            repeat="item in options | filter:{name: $select.search}">
                                        <span ng-bind-html="item.name | highlight: $select.search"></span>
                                    </ui-select-choices>
                                </ui-select>
                            </div>
                        </div>

						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">审核人</span> <input
									ng-model="search.realName" type="text" class="form-control">
							</div>
						</div>

						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">发布开始日期</span> <input type="text"
																				   class="form-control" datetimepicker
																				   ng-model="search.createTimeStart"/>
							</div>

							至
							<div class="input-group">
								<input type="text" class="form-control" datetimepicker
									   ng-model="search.createTimeEnd"/>
							</div>
						</div>


             <%--                   <div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">发布开始日期</span> <input
									ng-model="search.createTimeStart" type="text" class="form-control">
							</div>
						</div>
						至
						<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">发布结束日期</span> <input
									ng-model="search.createTimeEnd" type="text" class="form-control">
							</div>
						</div>--%>

						<a ng-click="list()" class="btn btn-primary form-group-margin"
							href="javascript:void(0);"> <i class="fa fa-search"></i>搜索
						</a>
						<a ng-click="exportData()" class="btn btn-primary form-group-margin" href="javascript:void(0);"></i>导出列表</a>
					</form>
					<!-- 表格 -->
					<table id="AuditTaskList" style="width: 100%"></table>
					<!-- 分页 -->
					<div id="TaskListPager"></div>
				</div>
			</div>
		</div>
	</div>
	<!--消息框 -->
	<toaster-container
		toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>