<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='procurementSku'>
<%=JSONObject.toJSONString( request.getAttribute( "procurementSku" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="procurementSku.procurementSkuId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.procurementSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">任务ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.taskId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">价格</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.price" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">总需购买商品数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.quantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.buyQuantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">买手操作</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.buyOption" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">真实价格</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.buyPrice" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">价高图片</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.buyPriceImage" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">验货数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.receiveQuantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">验货操作</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.receiveOption" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">位置编号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementSku.receiveSite" class="form-control">
	   </div>
	</div>

</div>
</form>

