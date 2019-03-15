<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	isELIgnored="false" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div init-model="category">
	<%=JSONObject.toJSONString(request.getAttribute("category"))%>
</div>
<div init-model="categoryUpdateKey">
	<%=request.getAttribute("categoryUpdateKey")%>
</div>
<form id="category-edit-form" method="post"
	enctype="multipart/form-data"
	class="form-horizontal ng-pristine ng-valid" role="form">
	<div class="row" style="max-height: 500px; overflow: auto;">
		<div ng-show="categoryUpdateKey == 2"
			class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label for="inputEmail3" class="col-sm-2 no-padding-right"
				style="text-align: right;">父类别名称</label>
			<div class="col-sm-10">{{category.parentName}}</div>
		</div>
		<div ng-show="categoryUpdateKey == 2"
			class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">类别名称</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text" ng-model="category.name"
					class="form-control">
			</div>
		</div>
		<div ng-show="categoryUpdateKey == 1"
			class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">类别名称</label>
			<div class="col-sm-10">
				<!-- 编辑页面不允许修改类别名称 -->
				<!-- <input ng-required="required"  type="text" ng-model="category.name" class="form-control"> -->
				{{category.name}}
			</div>
		</div>
		<div class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">类别图</label>
			<div class="col-sm-10">
				<div ng-model="category.logo" ui-imagebox name="logo"
					data-options=" width:'300px'"></div>
			</div>
		</div>
		
		<%-- 		<div class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">类别图</label>
			<div class="col-sm-10">
			<c:if test="${imageShow!=1}">
				<div ng-model="category.logo" ui-imagebox name="logo"
					data-options=" width:'300px'"></div>
			</c:if>
			<c:if test="${imageShow==1}">
				<div  ui-imagebox name="logo"
					data-options=" width:'300px'"></div>
			</c:if>
			</div>
		</div> --%>
	</div>
</form>