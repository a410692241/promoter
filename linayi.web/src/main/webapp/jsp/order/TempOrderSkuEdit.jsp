<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='tempOrderSku'>
<%=JSONObject.toJSONString( request.getAttribute( "tempOrderSku" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="tempOrderSku.tempOrderSkuId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.tempOrderSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">订单ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.orderId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品-超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.skuSupermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.supermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">价格</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.price" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.quantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">社区ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.communityId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">采买费</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.buyFee" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">分拣类别</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="tempOrderSku.sortingCategoryId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">创建时间</label>
	   <div class="col-sm-10">
	   		<input type="text" class="form-control" datetimepicker ng-model="tempOrderSku.createTime"/>
	   </div>
	</div>

</div>
</form>

