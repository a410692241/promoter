<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/community/services/CommunityService.js"></script>
<script type="text/javascript" src="resource/js/community/controllers/CommunityController.js"></script>
</head>
<body>
<div class="row" ng-controller="communityCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a>
                    <a href="" widget-maximize></a>
                </div>
            </div>
            <div class="widget-body">
            	<form class="form-inline">
				 <div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">社区名称</span>
                           <input ng-model="search.name" type="text" class="form-control">
                       </div>
                 </div>
<!-- 				 <div class="form-group form-group-margin"> -->
<!--                        <div class="input-group"> -->
<!--                            <span class="input-group-addon">负责人</span> -->
<!--                            <input ng-model="search.nickname" type="text" class="form-control"> -->
<!--                        </div> -->
<!--                  </div> -->
				 <div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">手机号码</span>
                           <input ng-model="search.mobile" type="text" class="form-control">
                       </div>
                 </div>
				 <div class="form-group form-group-margin">
                       <div class="input-group">
                           <span class="input-group-addon">地址</span>
                           <input ng-model="search.address" type="text" class="form-control">
                       </div>
                 </div>
				  <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>
				  
				  <a ng-click="edit()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-plus"></i>新增
				  </a>
				  
				</form>
				
				<!-- 表格 -->
                <table id="communityList" style="width: 100%"></table>
                <table id="communityInfo" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="communityPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>