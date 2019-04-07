<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='skuSupermarket'>
<%=JSONObject.toJSONString( request.getAttribute( "skuSupermarket" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">主键</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.skuSupermarketId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">商品ID</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.goodsSkuId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">超市ID</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.supermarketId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">价格</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.price}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">价格类型编码</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.priceType}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">价格有效开始时间</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.validStart | date:'yyyy-MM-dd HH:mm:ss'}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">价格有效结束时间</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.validEnd | date:'yyyy-MM-dd HH:mm:ss'}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">价格纠错ID</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.correctId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">开始发放收益时间</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.earningsTime | date:'yyyy-MM-dd HH:mm:ss'}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">是否已删除</label>
	   <div class="col-sm-10">
	   		{{skuSupermarket.removed}}
	   </div>
	</div>

</div>
</form>