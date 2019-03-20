<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='goods'>
<%=JSONObject.toJSONString( request.getAttribute( "goodsSku" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">商品名称</label>
	   <div class="col-sm-10">
		   <input ng-required="required" type="text" disabled
				  ng-model="goods.fullName" class="form-control"/>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">商品名</label>
		<div class="col-sm-10">
			<input ng-required="required" id="name" type="text" disabled
				   ng-model="goods.name" class="form-control"/>
		</div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">分类</label>
	   <div class="col-sm-10">
		   <input ng-required="required" type="text" disabled
				  ng-model="goods.categoryName" class="form-control"/>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">品牌</label>
	<div class="col-sm-10">
		<input ng-required="required" type="text" disabled
			   ng-model="goods.brandName" class="form-control"/>
	</div>
</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right"  style="text-align: right;">型号</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" disabled
				   ng-model="goods.model" class="form-control"/>
		</div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">生产日期</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" disabled
				   ng-model="goods.produceDate | date:'yyyy-MM-dd'" class="form-control"/>
		</div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">有效期</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" disabled
				   ng-model="goods.validDate | date:'yyyy-MM-dd'" class="form-control"/>
		</div>
	</div>

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">规格</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" disabled
				   ng-model="goods.attrValues" class="form-control"/>
		</div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">其他属性</label>
	   <div class="col-sm-10">
		   <input ng-required="required" type="text" disabled
				  ng-model="goods.otherAttribute" class="form-control"/>
	   </div>
	</div>

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">条形码</label>
	   <div class="col-sm-10">
		   <input ng-required="required" type="text" disabled
				  ng-model="goods.barcode" class="form-control"/>
	   </div>
	</div>

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">图片</label>
	   <div class="col-sm-10">
	   		<img style="height: 100px;width: 300px;" src="{{goods.image}}"/>
	   </div>
	</div>



</div>
</form>