<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" role="form" action="#" id="apkInfoForm" enctype="multipart/form-data" method="post">
<input type="hidden" ng-model="selectUser.accountId">
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">用户版本号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.android.versionName" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">升级版本号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.android.versionCode" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">download_url</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.android.download_url" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">分享标题</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.share.share_title" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">更改内容</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.android.update_content" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">logo_url</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.share.logo_url" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">share_link</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="apkInfo.share.share_link" class="form-control">
	   </div>
	</div>
</div>
</form>