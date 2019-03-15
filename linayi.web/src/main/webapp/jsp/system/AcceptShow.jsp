<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='accept'>
<%=JSONObject.toJSONString( request.getAttribute( "accept" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">主键</label>
	   <div class="col-sm-10">
	   		{{accept.acceptId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">消息ID</label>
	   <div class="col-sm-10">
	   		{{accept.messageId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">用户ID</label>
	   <div class="col-sm-10">
	   		{{accept.userId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">用户类型</label>
	   <div class="col-sm-10">
	   		{{accept.userType}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">已读状态</label>
	   <div class="col-sm-10">
	   		{{accept.read}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">推送JSON参数</label>
	   <div class="col-sm-10">
	   		{{accept.params}}
	   </div>
	</div>

</div>
</form>