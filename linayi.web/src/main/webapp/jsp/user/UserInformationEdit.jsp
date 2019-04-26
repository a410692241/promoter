<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='user'>
	<%=JSONObject.toJSONString(request.getAttribute("user"))%>
</div>

<div class="col-lg-12 col-sm-12 col-xs-12">
	<div class="widget flat radius-bordered">
		<div class="widget-header bg-danger">
			<span class="widget-caption">编辑</span>
		</div>
		<div id="registration-form">
			<form role="form">
				<div class="form-title">
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>用户ID</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   ng-model="user.userId"
									   class="form-control">
                                <i class="glyphicon glyphicon-fire blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label >昵称</label>
						<span class="input-icon icon-right">
                                <input type="text"
									   ng-model="user.nickname"
									   class="form-control">
                                <i class="glyphicon glyphicon-user blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label >真实姓名</label>
						<span class="input-icon icon-right">
                                <input type="text"
									   ng-model="user.realName"
									   class="form-control">
                                <i class="glyphicon glyphicon-user blue"></i>
                            </span>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

