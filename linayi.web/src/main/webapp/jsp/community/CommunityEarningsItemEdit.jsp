<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='communityEarningsItem'>
<%=JSONObject.toJSONString( request.getAttribute( "communityEarningsItem" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="communityEarningsItem.communityEarningsItemId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="communityEarningsItem.communityEarningsItemId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">收益ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="communityEarningsItem.communityEarningsId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">金额</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="communityEarningsItem.amount" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">收益类型</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="communityEarningsItem.type" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">对象ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="communityEarningsItem.objectId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">配送分成的社区IID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="communityEarningsItem.communityId" class="form-control">
	   </div>
	</div>

</div>
</form>

