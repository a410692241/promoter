<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='procurementTask'>
<%=JSONObject.toJSONString( request.getAttribute( "procurementTask" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
	   <label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 no-padding-right" style="text-align: right;">接单人</label>
	   <div class="col-lg-4 col-md-4 col-sm-9 col-xs-8">
	   		{{procurementTask.nickname}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
	   <label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 no-padding-right" style="text-align: right;">联系电话</label>
	   <div class="col-lg-4 col-md-4 col-sm-9 col-xs-8">
	   		{{procurementTask.mobile}}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
	   <label class="col-lg-1 col-md-1 col-sm-3 col-xs-4 no-padding-right"  style="text-align: right;">地址</label>
	   <div class="col-lg-11 col-md-11 col-sm-9 col-xs-8" style="word-break: break-all;">
	   		{{procurementTask.address}}
	   </div>
	</div>
	 <div class="clearfix"></div>
	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
	   <label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 no-padding-right"  style="text-align: right;">任务编号</label>
	   <div class="col-lg-4 col-md-4 col-sm-9 col-xs-8">
	   		{{procurementTask.taskNo}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
	   <label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 no-padding-right"  style="text-align: right;">任务状态</label>
	   <div class="col-lg-4 col-md-4 col-sm-9 col-xs-8">
	   		{{procurementTask.statusName}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
	   <label class="col-lg-2 col-md-2 col-sm-3 col-xs-4 no-padding-right"  style="text-align: right;">创建时间</label>
	   <div class="col-lg-10 col-md-10 col-sm-9 col-xs-8">
	   		{{procurementTask.createTime| date:'yyyy-MM-dd HH:mm:ss'}}
	   </div>
	</div>
</div>
</form>