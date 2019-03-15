<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='shoppingCar'>
<%=JSONObject.toJSONString( request.getAttribute( "shoppingCar" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="shoppingCar.shoppingCarId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="shoppingCar.shoppingCarId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">用户ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="shoppingCar.userId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="shoppingCar.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="shoppingCar.supermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="shoppingCar.quantity" class="form-control">
	   </div>
	</div>

</div>
</form>

