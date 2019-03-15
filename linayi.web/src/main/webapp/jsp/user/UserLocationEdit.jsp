<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='userLocation'>
<%=JSONObject.toJSONString( request.getAttribute( "userLocation" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="userLocation.userLocationId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="userLocation.userLocationId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">详细地址</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="userLocation.address" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">地区编码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="userLocation.areaCode" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">用户ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="userLocation.userId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">联系人昵称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="userLocation.nickname" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">联系电话</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="userLocation.mobile" class="form-control">
	   </div>
	</div>

</div>
</form>

