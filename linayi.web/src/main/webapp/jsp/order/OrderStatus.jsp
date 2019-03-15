<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row">
	
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">状态</label>
	   <div class="col-sm-10">
		   <ui-select
				   on-select="search.communityStatus = $item.code"
				   ng-init='options=<cl:getByEnum enumName="com.linayi.enums.OrderStatus" nameWithCode="getOrderTypeName;name"/>;
                                   options.splice(0,0,{"name":"","code":""});'
				   ng-model="temp" style="min-width: 200px">
			   <ui-select-match>{{$select.selected.name}}</ui-select-match>
			   <ui-select-choices repeat="item in options | filter:{name: $select.search}">
				   <span ng-bind-html="item.name | highlight: $select.search"></span>
			   </ui-select-choices>
		   </ui-select>
		   <%--<ui-select on-select="search.categoryId=$item.code;"
					  ng-init='options=<cl:selectBySQL sqlId="categoryList" param="{level:4}"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
					  ng-model="temp" style="min-width: 230px">
			   <ui-select-match>{{$select.selected.name}}</ui-select-match>
			   <ui-select-choices
					   repeat="item in options | filter:{name: $select.search}">
				   <span ng-bind-html="item.name | highlight: $select.search"></span>
			   </ui-select-choices>
		   </ui-select>--%>
	   </div>
	</div>
</div>
</form>

