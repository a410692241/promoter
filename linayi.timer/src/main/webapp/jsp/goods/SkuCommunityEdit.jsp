<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='skuCommunity'>
<%=JSONObject.toJSONString( request.getAttribute( "skuCommunity" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="skuCommunity.skuCommunityId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.skuCommunityId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">社区网点ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.communityId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">最低价超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.minSupermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">最高价超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.maxSupermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">最低价超市价格</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.minPrice" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">最高价超市价格</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuCommunity.maxPrice" class="form-control">
	   </div>
	</div>

</div>
</form>

