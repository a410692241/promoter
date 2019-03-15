<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='selectUser' >
<%=JSONObject.toJSONString( request.getAttribute( "user" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<input type="hidden" ng-model="selectUser.accountId">
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">账号名称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" ng-readonly="selectUser.accountId > 0" type="text" ng-disabled="selectUser.employeeId" ng-model="selectUser.userName" class="form-control">
	   </div>
	</div>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label class="col-sm-2 control-label no-padding-right">性别</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" ng-model="selectUser.sex" class="form-control">
		</div>
	</div>--%>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right">性别</label>
		<div class="col-sm-10">
			<ui-select on-select="selectUser.sex=$item.name" ng-init='options=[{name:"男"},{name:"女"}];temp=options[0]' ng-model="temp" theme="bootstrap" style="min-width: 200px">
				<ui-select-match>{{$select.selected.name}}</ui-select-match>
				<ui-select-choices repeat="item in options | filter: $select.search">
					<span ng-bind-html="item.name | highlight: $select.search"></span>
				</ui-select-choices>
			</ui-select>
		</div>
	</div>--%>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right">用户类型</label>
		<div class="col-sm-10">
			<ui-select on-select="search.status=$item.code" ng-init='options=[{code:system,name:"系统"},{code:user,name:"员工"}];temp=options[0]' ng-model="temp" theme="bootstrap" style="min-width: 200px">
				<ui-select-match>{{$select.selected.name}}</ui-select-match>
				<ui-select-choices repeat="item in options | filter: $select.search">
					<span ng-bind-html="item.name | highlight: $select.search"></span>
				</ui-select-choices>
			</ui-select>
		</div>
	</div>--%>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">密码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-disabled="selectUser.employeeId" ng-model="selectUser.password" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">确认密码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-disabled="selectUser.employeeId" ng-model="selectUser.password1" class="form-control">
	   </div>

	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">真实姓名</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="selectUser.realName" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">联系电话</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="selectUser.mobile" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right">QQ</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" ng-model="selectUser.qq" class="form-control">
		</div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right">电子邮箱</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" ng-model="selectUser.email" class="form-control">
		</div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right">昵称</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" ng-model="selectUser.nickname" class="form-control">
		</div>
	</div>

</div>
</form>

