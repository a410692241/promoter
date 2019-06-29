<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<script type="text/javascript" src="resource/js/user/services/OrderManService.js"></script>
	<script type="text/javascript" src="resource/js/user/controllers/OrderManController.js"></script>
</head>
<body>
<div class="row" ng-controller="orderManCtrl">
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
				<form class="form-inline" id="correct">
					<div class="form-group form-group-margin">
						<div class="input-group">
							<span class="input-group-addon">家庭服务师昵称</span>
							<input ng-model="search.nickName" type="text" class="form-control">
						</div>
					</div>

					<div class="form-group form-group-margin">
						<div class="input-group">
							<span class="input-group-addon">绑定手机号</span>
							<input ng-model="search.mobile" type="text" class="form-control">
						</div>
					</div>

					<div class="form-group form-group-margin">
						<div class="input-group">
							<span class="input-group-addon">家庭服务师姓名</span>
							<input ng-model="search.realName" type="text" class="form-control">
						</div>
					</div>
					<div class="form-group form-group-margin">
						<div class="input-group">
							<span class="input-group-addon">家庭服务师ID</span>
							<input ng-model="search.orderManId" type="text" class="form-control">
						</div>
					</div>
					<div class="form-group form-group-margin">
						<div class="input-group">
							<%-- <span class="input-group-addon">用户类型</span>
                              <ui-select
                    on-select="search.userType=$item.code"
                    ng-init='
                      options=<cx:out-options dictMapCd="userUserType" />;
                      options.splice(0,0,{name:"全部",code:""});
                    '
                    ng-model="temp" style="min-width: 200px">
                       <ui-select-match>{{$select.selected.name}}</ui-select-match>
                       <ui-select-choices repeat="item in options | filter: {name:$select.search}">
                           <span ng-bind-html="item.name | highlight: $select.search"></span>
                       </ui-select-choices>
                   </ui-select> --%>
						</div>
					</div>
					<%-- <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">性别</span>
                            <ui-select
                   on-select="search.sex=$item.code"
                   ng-init='
                     options=<cx:out-options dictMapCd="userSex" />;
                     options.splice(0,0,{name:"全部",code:""});
                   '
                   ng-model="temp" style="min-width: 200px">
                      <ui-select-match>{{$select.selected.name}}</ui-select-match>
                      <ui-select-choices repeat="item in options | filter: {name:$select.search}">
                          <span ng-bind-html="item.name | highlight: $select.search"></span>
                      </ui-select-choices>
                  </ui-select>
                        </div>
                    </div> --%>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">上级家庭服务师姓名</span>
                            <input ng-model="search.salesName" type="text" class="form-control">
                        </div>
                    </div>
					<div class="form-group form-group-margin">
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
					</div>
					<a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
						<i class="fa fa-search"></i>搜索
					</a>
					<%--<a class="btn btn-primary form-group-margin" href="/web/correct/correct/exportShareRecord.do"></i>导出列表</a>--%>
				</form>

				<!-- 表格 -->
				<table id="orderManList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="orderManPager"></div>

			</div>
		</div>
	</div>
</div>
<!--消息框 -->
<toaster-container
		toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>