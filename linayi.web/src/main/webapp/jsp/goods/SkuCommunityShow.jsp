<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='skuCommunity'>
<%=JSONObject.toJSONString( request.getAttribute( "skuCommunity" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">主键</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.skuCommunityId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">商品ID</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.goodsSkuId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">社区网点ID</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.communityId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">最低价超市ID</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.minSupermarketId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">最高价超市ID</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.maxSupermarketId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">最低价超市价格</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.minPrice}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">最高价超市价格</label>
	   <div class="col-sm-10">
	   		{{skuCommunity.maxPrice}}
	   </div>
	</div>

</div>
</form>