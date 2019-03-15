<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='categoryBrand'>
<%=JSONObject.toJSONString( request.getAttribute( "categoryBrand" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="categoryBrand.categoryBrandId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="categoryBrand.categoryBrandId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">品牌ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="categoryBrand.brandId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">类别ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="categoryBrand.categoryId" class="form-control">
	   </div>
	</div>

</div>
</form>

