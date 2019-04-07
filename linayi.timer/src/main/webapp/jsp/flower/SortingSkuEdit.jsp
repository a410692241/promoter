<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='sortingSku'>
<%=JSONObject.toJSONString( request.getAttribute( "sortingSku" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="sortingSku.sortingSkuId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.sortingSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">任务ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.taskId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">社区ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.taskCommunityId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">订单ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.orderId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.supermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.sortingQuantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">操作</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.sortingOption" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">流转箱ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.boxId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">流转收货数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.flowerQuantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">流转收货操作</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.flowerOption" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">状态</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="sortingSku.status" class="form-control">
	   </div>
	</div>

</div>
</form>

