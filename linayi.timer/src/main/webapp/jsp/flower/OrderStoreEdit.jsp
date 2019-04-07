<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='orderStore'>
<%=JSONObject.toJSONString( request.getAttribute( "orderStore" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="orderStore.orderStoreId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.orderStoreId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">订单ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.orderId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.supermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">入库数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.quantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">状态</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.status" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">所在位置</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="orderStore.receiveSite" class="form-control">
	   </div>
	</div>

</div>
</form>

