<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='brand'>
<%=JSONObject.toJSONString( request.getAttribute( "brand" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="brand.brandId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12" ng-hide="true">
	   <label  class="col-sm-2 control-label no-padding-right">品牌ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="brand.brandId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">名称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="brand.name" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">品牌LOGO</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="brand.logo" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">介绍</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="brand.content" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">排序</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="brand.rank" class="form-control">
	   </div>
	</div>

</div>
</form>

