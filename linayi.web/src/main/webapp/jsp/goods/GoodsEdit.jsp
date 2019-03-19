<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form role="form" id="goodsSkuInfoForm" role="form" method="post" enctype="multipart/form-data">
	<div class="row" style="max-height: 500px;overflow: auto;">
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">商品名称</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text"
					   ng-model="goods.fullName" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">分类</label>
			<div class="col-sm-10">
				<ui-select on-select="goods.categoryId=$item.code;goods.categoryName=$item.name;"
						   ng-init='options=<cl:selectBySQL sqlId="categoryList" param="{'level':4}"/>;
						   tempCategoryName={"name":"${goodsSku.categoryName}","code":"${goodsSku.categoryId}"};'
						   ng-model="tempCategoryName" style="min-width: 230px">
					<ui-select-match>{{$select.selected.name}}</ui-select-match>
					<ui-select-choices
							repeat="item in options | filter:{name: $select.search}">
						<span ng-bind-html="item.name | highlight: $select.search"></span>
					</ui-select-choices>
				</ui-select>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">品牌</label>
			<div class="col-sm-10">
				<ui-select on-select="goods.brandId=$item.code;goods.brandName=$item.name;"
						   ng-init='options=<cl:selectBySQL sqlId="brandList"/>;
						   tempBrandName={"name":"${goodsSku.brandName}","code":"${goodsSku.brandId}"};'
						   ng-model="tempBrandName" style="min-width: 230px">
					<ui-select-match>{{$select.selected.name}}</ui-select-match>
					<ui-select-choices
							repeat="item in options | filter:{name: $select.search}">
						<span ng-bind-html="item.name | highlight: $select.search"></span>
					</ui-select-choices>
				</ui-select>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right"  style="text-align: right;">型号</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text"
					   ng-model="goods.model" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">生产日期</label>
			<div class="col-sm-10">
				<input ng-model="goods.createTimeStart" datetimepicker type="text" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">有效期</label>
			<div class="col-sm-10">
				<input ng-model="goods.createTimeEnd" datetimepicker type="text" class="form-control"/>
			</div>
		</div>

		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">规格</label>
			<div class="col-sm-10">
				<input ng-required="required" id="attrValues" type="text" readonly ng-click="editeAttribute(goods.goodsSkuId);"
					   ng-model="goods.attrValues" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">其他属性</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text"
					   ng-model="goods.otherAttribute" class="form-control"/>
			</div>
		</div>

		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">条形码</label>
			<div class="col-sm-10">
				<input ng-required="required" type="text"
					   ng-model="goods.barcode" class="form-control"/>
			</div>
		</div>

		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">图片</label>
			<div class="col-sm-10">
				<%--<img style="height: 100px;width: 300px;" src="{{goods.image}}"/>--%>
				<div  name="goodsImage" style="width: 100%;height:50%;border: 1px solid #D5D5D5;" ui-imagebox ng-model="goods.image"></div>
			</div>
		</div>
	</div>
</form>