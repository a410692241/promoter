<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='skuAttribute'>
<%=JSONObject.toJSONString( request.getAttribute( "skuAttribute" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="skuAttribute.skuAttributeId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuAttribute.skuAttributeId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">货品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuAttribute.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">属性ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuAttribute.attributeId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">属性值ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuAttribute.attrValueId" class="form-control">
	   </div>
	</div>

</div>
</form>

