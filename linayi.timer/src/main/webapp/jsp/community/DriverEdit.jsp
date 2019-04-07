<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='driver'>
<%=JSONObject.toJSONString( request.getAttribute( "driver" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="driver.driverId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">昵称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.nickname" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">手机号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.mobile" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">账号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.account" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">车辆编号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.carNo" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">车牌号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.plate" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">密码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.password" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">地址</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.address" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">备注</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="driver.remark" class="form-control">
	   </div>
	</div>

</div>
</form>

