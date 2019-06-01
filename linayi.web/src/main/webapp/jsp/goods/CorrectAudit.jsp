<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="correct.correctId"/>
<div class="row">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-4 no-padding-right" style="text-align: right;">商品</label>
	   <div class="col-sm-8">
	   		{{correct.fullName}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-4 no-padding-right" style="text-align: right;">分享员：</label>
	   <div class="col-sm-8">
	   		{{correct.realName}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-4 no-padding-right" style="text-align: right;">分享价格：</label>
	   <div class="col-sm-8">
	   		{{correct.price}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-4 no-padding-right" style="text-align: right;">价格类型：</label>
	   <div class="col-sm-8">
	   		{{correct.priceType}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-4 no-padding-right" style="text-align: right;">价格有效期：</label>
	   <div class="col-sm-8">
	   		{{correct.startTime}} 至  {{correct.endTime}}
	   </div>
	</div>

</div>
</form>

