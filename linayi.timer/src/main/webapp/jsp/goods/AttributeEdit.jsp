<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='attribute'>
<%=JSONObject.toJSONString( request.getAttribute( "attribute" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="attribute.attributeId"/>
<div class="row" ng-model="attribute" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">名称</label>
	   <div class="col-sm-4">
	       <input ng-required="required" type="text" ng-model="attribute.name" class="form-control">
	   </div>
	</div>
	<div ng-repeat="attributeValue in attribute.attributeValueList track by $index " class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">
	   	<span ng-if="$index == 0">规格值</span>
	   </label>
	   <div class="col-sm-4">
	       <input ng-required="required" type="text" ng-model="attributeValue.value" class="form-control">
	   </div>
		<%--删除功能没做--%>
	   <%--<div class="col-sm-2" ng-if="attribute.attributeValueList.length > 1">--%>
			<%--<a ng-click="removeAttrValue( $index )" class="btn btn-primary" href="javascript:void(0);">--%>
				<%--<i class="fa fa-close"></i>删除--%>
			<%--</a>--%>
		<%--</div>--%>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
		<label for="inputEmail3" class="col-sm-2 control-label no-padding-right"></label>
		<div class="col-sm-2">
			<a ng-click="addAttrValue( )" ng-show="attribute.attributeId" class="btn btn-primary" href="javascript:void(0);">
				<i class="fa fa-close"></i>添加
			</a>
		</div>
	</div>

</div>
</form>

