<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='apiLogs'>
<%=JSONObject.toJSONString( request.getAttribute( "apiLogs" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<input type="hidden" ng-model="user.userId">
<div class="row">

	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求url</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.requestUrl }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求时间</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.requestTime | date:'yyyy-MM-dd HH:mm:ss' }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求参数</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.agreementParams }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求头</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.requestHead }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求体</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.requestBody }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求方法</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.requestMethod }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">请求ip</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.requestIp }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">错误码</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.errorCode }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">错误消息</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.errorMsg }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">响应时间</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.responseTime | date:'yyyy-MM-dd HH:mm:ss' }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">响应内容</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.responseBody }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-3 no-padding-right" style="text-align: right;">响应码</label>
	   <div class="col-sm-9">
	   		{{ apiLogs.responseCode }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">消息id</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.msgId }}
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-2 no-padding-right" style="text-align: right;">异常</label>
	   <div class="col-sm-10">
	   		{{ apiLogs.exception }}
	   </div>
	</div>

</div>
</form>