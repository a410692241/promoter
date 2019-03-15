<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='box'>
<%=JSONObject.toJSONString( request.getAttribute( "box" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="box.boxId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.boxId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">箱子编号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.boxNo" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">重量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.weight" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">是否开箱验货</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.openBox" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">箱子类型</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.type" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">封箱图片</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.imagePath" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">车辆ID/订单ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.objectId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">装箱时间</label>
	   <div class="col-sm-10">
	   		<input type="text" class="form-control" datetimepicker ng-model="box.createTime"/>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">分拣类别ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="box.sortingCategoryId" class="form-control">
	   </div>
	</div>

</div>
</form>

