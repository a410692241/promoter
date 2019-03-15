<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" id="advertForm" enctype="multipart/form-data" method="post" role="form">
	<input  type="hidden" name="advertId" value="{{advert.advertId}}"/>
<div class="row" >
	<div class="form-group">
	   <label class="col-sm-2 control-label no-padding-right">标题</label>
	   <div class="col-sm-8">
	       <input ng-required="required" type="text" name="title" ng-model="advert.title" class="form-control">
	   </div>
	</div>
	<div class="form-group">
	   <label class="col-sm-2 control-label no-padding-right">有效开始时间</label>
	   <div class="col-sm-3">
	   		<input type="text" class="form-control" name="validStart" datetimepicker ng-model="advert.validStart"/>
	   </div>
	   <label class="col-sm-2 control-label" style="text-align: center;">至</label>
	   <div class="col-sm-3">
	   		<input type="text" class="form-control" name="validEnd" datetimepicker ng-model="advert.validEnd"/>
	   </div>
	</div>
	<div class="form-group" style="display: flex;align-items:center">
	   <label class="col-sm-2 control-label no-padding-right">模板</label>
	   <input type="hidden" name="template" value="{{advert.template}}" /> 
	   <div class="col-sm-4">
		   <div class="checkbox">
	           <label>
	               <input type="radio" name="templateRadio" ng-click="listenTemplate(1000)" class="colored-danger" ng-checked="advert.template == 1000">
	               <span class="text">
	               		<img src="resource/image/AdTemplate1.png" width="172px">
	               </span>
	           </label>
	       </div>
	   </div>
	   <div class="col-sm-4">
		   <div class="checkbox">
	           <label>
	               <input type="radio" name="templateRadio" ng-click="listenTemplate(5000)" class="colored-danger" ng-checked="advert.template == 5000">
	               <span class="text">
	               		<img src="resource/image/AdTemplate2.png" width="172px">
	               </span>
	           </label>
	       </div>
	   </div>
	</div>
	<div style="padding:30px 0px;border-top: 2px solid #2dc3e8" ng-repeat="adItem in advertItemList track by $index">
	<label class="control-label" style="width: 100%;text-align: left;display: none">{{adItem.advertItemId}}</label>
	<div class="form-group">
	   <label class="col-sm-2 control-label no-padding-right">广告图片</label>
	   <div class="col-sm-8">
	       <div name="imageFile" style="width: 100%;" ui-imagebox ng-model="adItem.imagePath"></div>
	   </div>
	</div>
	<div class="form-group">
	   <label class="col-sm-2 control-label no-padding-right">广告类型</label>
	   <div class="col-sm-2">
		   <div class="checkbox">
	           <label>
	               <input type="radio" ng-click="listenOpenType($index+1,10)" name="openType_{{$index+1}}" class="colored-danger" value="10" ng-checked="adItem.openType == 10">
	               <span class="text">图文详情</span>
	           </label>
	       </div>
	   </div>
	   <div class="col-sm-2">
		   <div class="checkbox">
	           <label>
	               <input type="radio" ng-click="listenOpenType($index+1,20)" name="openType_{{$index+1}}" class="colored-danger" value="20" ng-checked="adItem.openType == 20">
	               <span class="text">超链接</span>
	           </label>
	       </div>
	   </div>
	   <div class="col-sm-2">
		   <div class="checkbox">
	           <label>
	               <input type="radio" ng-click="listenOpenType($index+1,30)" name="openType_{{$index+1}}" class="colored-danger" value="30" ng-checked="adItem.openType == 30">
	               <span class="text">商品详情</span>
	           </label>
	       </div>
	   </div>
	   <div class="col-sm-2">
		   <div class="checkbox">
	           <label>
	               <input type="radio" ng-click="listenOpenType($index+1,40)" name="openType_{{$index+1}}" class="colored-danger" value="40" ng-checked="adItem.openType == 40">
	               <span class="text">类别商品</span>
	           </label>
	       </div>
	   </div>
	</div>
	<div class="form-group" ng-show="adItem.openType == 10">
	   <label class="col-sm-2 control-label no-padding-right">图文详情</label>
	   <div class="col-sm-8">
	      	<textarea  ng-model="adItem.imageTextValue" kindeditor style="width: 100%"></textarea>
	   </div>
	</div>
	<div class="form-group" ng-show="adItem.openType == 20">
	   <label class="col-sm-2 control-label no-padding-right">超链接</label>
	   <div class="col-sm-8">
	      	<input type="text" class="form-control" ng-model="adItem.hrefValue"/>
	   </div>
	</div>
	<div class="form-group" ng-show="adItem.openType == 30">
	   <label class="col-sm-2 control-label no-padding-right">商品详情</label>
	   <div class="col-sm-2">
	      	<a ng-click="getGoodsSku($index)" href="javascript:void(0);" class="btn btn-primary fa fa-plus btn-sm td-compile">
				选择商品
			</a>
	   </div>
	   <div class="col-sm-6">
	   		<label class="control-label" style="width: 100%;text-align: left;">{{adItem.goodsName}}</label>
	   		<label class="control-label" style="width: 100%;text-align: left;display: none">{{adItem.goodsId}}</label>
	   </div>
	</div>
	<div class="form-group" ng-show="adItem.openType == 40">
	   <label class="col-sm-2 control-label no-padding-right">类别商品</label>
	   <div class="col-sm-2">
	      	<a ng-click="getCategory($index)" href="javascript:void(0);" class="btn btn-primary fa fa-plus btn-sm td-compile">
				选择类别
			</a>
	   </div>
	   <div class="col-sm-6">
	   		<label class="control-label" style="width: 100%;text-align: left;">{{adItem.categoryName}}</label>
	   		<label class="control-label" style="width: 100%;text-align: left;display: none">{{adItem.categoryId}}</label>
	   </div>
	</div>
	</div>
	
</div>
</form>

