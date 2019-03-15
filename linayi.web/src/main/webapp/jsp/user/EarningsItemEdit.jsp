<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='earningsItem'>
<%=JSONObject.toJSONString( request.getAttribute( "earningsItem" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="earningsItem.earningsItemId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="earningsItem.earningsItemId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">收益ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="earningsItem.earningsId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">收益金额</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="earningsItem.amount" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">收益类型</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="earningsItem.type" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">对象ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="earningsItem.objectId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">任务社区ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="earningsItem.communityId" class="form-control">
	   </div>
	</div>

</div>
</form>

