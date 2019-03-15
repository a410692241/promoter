<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='communitySupermarket'>
<%=JSONObject.toJSONString( request.getAttribute( "communitySupermarket" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="communitySupermarket.communitySupermarketId"/>
<div class="row" style="max-height: 300px">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12" ng-hide="supermarket.supermarketId">
	<label class="col-sm-2 control-label no-padding-right">换绑的新社区</label>
	<div class="col-sm-8">
         <ui-select
			on-select="search.communityId = $item.code" 
			ng-init='
			options=<cx:out-sql sqlId="options.listCommunity"></cx:out-sql>;'
			ng-model="communitySupermarket.community" style="min-width: 230px">
			<ui-select-match>{{$select.selected.name}}</ui-select-match>
			<ui-select-choices repeat="item in options | filter:{name: $select.search}">
				<span ng-bind-html="item.name | highlight: $select.search"></span>
			</ui-select-choices>
			</ui-select>
			</div>
	</div>
</div>
</form>

