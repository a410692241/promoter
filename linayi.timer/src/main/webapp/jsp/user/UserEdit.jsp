<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" role="form" id="userInfoForm" method="post" enctype="multipart/form-data">
	<input  type="hidden" ng-model="user.userId"/>
<div class="row">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label class="col-sm-2 control-label no-padding-right">姓名</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="user.nickname" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">账号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" readonly="readonly" ng-model="user.account" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">头像</label>
	   <div class="col-sm-10">
<!-- 	       <input ng-required="required" type="text" ng-model="user.headImage" class="form-control"> -->
			<div  name="imageFile" style="width: 100%;" ui-imagebox ng-model="user.headImage"></div>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">用户类型</label>
	   <div class="col-sm-10">
		   <ui-select
		   		on-select="search.userType=$item.code"
		   		ng-init='
		   			options=<cx:out-options dictMapCd="userUserType" />;
		   		'
			   	ng-model="user.userType" style="min-width: 200px">
	            <ui-select-match>{{$select.selected.name}}</ui-select-match>
	            <ui-select-choices repeat="item in options | filter: {name:$select.search}">
	                <span ng-bind-html="item.name | highlight: $select.search"></span>
	            </ui-select-choices>
	        </ui-select>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">邮件</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="user.email" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">手机号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="user.mobile" class="form-control">
	   </div>
	</div>
	

</div>
</form>

