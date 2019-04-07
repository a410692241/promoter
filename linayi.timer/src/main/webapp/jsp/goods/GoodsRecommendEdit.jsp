w﻿﻿<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='goodsRecommend'>
<%=JSONObject.toJSONString( request.getAttribute( "goodsRecommend" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="goodsRecommend.goodsRecommendId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsRecommend.goodsRecommendId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">货品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsRecommend.goodsId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">被推荐的货品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="goodsRecommend.recommendId" class="form-control">
	   </div>
	</div>

</div>
</form>

