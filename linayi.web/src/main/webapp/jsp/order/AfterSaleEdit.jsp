<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='afterSale'>
<%=JSONObject.toJSONString( request.getAttribute( "afterSale" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="afterSale.afterSaleId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.afterSaleId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">订单-商品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.orderSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">售后类型编码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.type" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">售后原因</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.cause" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">售后说明</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.content" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">实际退款金额</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.amount" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">处理状态</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.processStatus" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">处理原因</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.processCause" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">处理说明</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.processContent" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">处理时间</label>
	   <div class="col-sm-10">
	   		<input type="text" class="form-control" datetimepicker ng-model="afterSale.processTime"/>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">处理人</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.processUser" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">创建时间</label>
	   <div class="col-sm-10">
	   		<input type="text" class="form-control" datetimepicker ng-model="afterSale.createTime"/>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">社区ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="afterSale.communityId" class="form-control">
	   </div>
	</div>

</div>
</form>

