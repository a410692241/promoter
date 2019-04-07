<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='user'>
<%=JSONObject.toJSONString( request.getAttribute( "user" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">昵称</label>
	   <div class="col-sm-10">
	   		{{user.nickname}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">性别</label>
	   <div class="col-sm-10">
	   		{{user.sexName}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">账号</label>
	   <div class="col-sm-10">
	   		{{user.account}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">手机号</label>
	   <div class="col-sm-10">
	   		{{user.mobile}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">住址</label>
	   <div class="col-sm-10">
	   		{{user.address}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">头像</label>
	   <div class="col-sm-10">
	   		<img ng-src="{{user.headImage}}" width="172px"> 		
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">认证状态</label>
	   <div class="col-sm-10">
	   		{{user.authenticationName}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">账户余额</label>
	   <div class="col-sm-10">
	   		{{user.amount}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">禁用状态</label>
	   <div class="col-sm-10">
	   		{{user.validName}}
	   </div>
	</div>

</div>
</form>