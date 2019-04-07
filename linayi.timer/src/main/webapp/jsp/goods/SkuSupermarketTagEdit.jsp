<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='skuSupermarketTag'>
<%=JSONObject.toJSONString( request.getAttribute( "skuSupermarketTag" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="skuSupermarketTag.skuSupermarketTagId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuSupermarketTag.skuSupermarketTagId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品-超市ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuSupermarketTag.skuSupermarketId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">标签ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="skuSupermarketTag.tagId" class="form-control">
	   </div>
	</div>

</div>
</form>

