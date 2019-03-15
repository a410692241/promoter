<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;">
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">产品名称</label>
	   <div class="col-sm-10">
	   		{{production.title}}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">类别</label>
	   <div class="col-sm-10">
	   		{{production.categoryName}}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">品牌</label>
	   <div class="col-sm-10">
	   		{{production.brandName}}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">规格属性</label>
	   <div class="col-sm-10">
	   		<ui-select
		   		close-on-select="false"
		   		multiple
		   		ng-disabled="true"
		   		theme="select2" 
		   		ng-init='
			   		attrCtrl.dataList=<cx:out-sql sqlId="options.listAttribute" paramters="{removed:2}"></cx:out-sql>;
			   		initAttrCtrl();
			   		'
			   	ng-model="attrCtrl.attrList">
	            <ui-select-match>{{$item.name}}</ui-select-match>
	            <ui-select-choices repeat="item in attrCtrl.dataList | filter:{name: $select.search}">
	                <span ng-bind-html="item.name | highlight: $select.search"></span>
	            </ui-select-choices>
	        </ui-select>
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">销售属性</label>
	   <div class="col-sm-10">
	   		<ui-select
		   		close-on-select="false"
		   		multiple
		   		ng-disabled="true"
		   		theme="select2" 
		   		ng-init='
			   		salesAttrCtrl.dataList=<cx:out-sql sqlId="options.listAttribute" paramters="{removed:2}"></cx:out-sql>;
			   		initSalesAttrCtrl();
			   		'
			   	ng-model="salesAttrCtrl.attrList">
	            <ui-select-match>{{$item.name}}</ui-select-match>
	            <ui-select-choices repeat="item in salesAttrCtrl.dataList | filter:{name: $select.search}">
	                <span ng-bind-html="item.name | highlight: $select.search"></span>
	            </ui-select-choices>
	        </ui-select>
	   </div>
	</div>

</div>
</form>