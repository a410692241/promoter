<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='car'>
<%=JSONObject.toJSONString( request.getAttribute( "car" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="car.carId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="car.carId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">司机ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="car.driverId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">车牌号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="car.plate" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">创建时间</label>
	   <div class="col-sm-10">
	   		<input type="text" class="form-control" datetimepicker ng-model="car.createTime"/>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">车辆编号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="car.carNo" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">是否已删除</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="car.removed" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">流转路线</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="car.routeId" class="form-control">
	   </div>
	</div>

</div>
</form>

