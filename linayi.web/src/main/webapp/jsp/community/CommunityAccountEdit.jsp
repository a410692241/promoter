<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div init-model='communityAccount'>
	<%=JSONObject.toJSONString(request.getAttribute("communityAccount"))%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input type="hidden" ng-model="communityAccount.communityAccountId" />
	<div class="row" style="max-height: 500px; overflow: auto;">
		
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">登陆账号</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text" disabled 
					ng-model="communityAccount.account" class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">原密码</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text" ng-model="communityAccount.dbPSW"
					 class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">修改密码</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text" ng-model="communityAccount.toChangePSW"
					 class="form-control">
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">确认修改密码</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text" ng-model="communityAccount.affirmPSD"
					 class="form-control">
			</div>
		</div>
	</div>
</form>

