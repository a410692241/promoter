<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='goodsSku'>
<%=JSONObject.toJSONString( request.getAttribute( "goodsSku" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="goodsSku.goodsSkuId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">货品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.goodsId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品名称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.goodsName" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">库存量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.quantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">单位编码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.unit" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品条码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.barcode" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">货号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.skuNo" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">是否已删除</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.removed" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">采买服务费</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.buyFee" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">配送服务费</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsSku.deliverFee" class="form-control">
	   </div>
	</div>

</div>
</form>

