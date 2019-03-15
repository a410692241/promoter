<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div init-model="apply1" hidden>
	<%=JSONObject.toJSON(request.getAttribute("apply1"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
	<div class="widget flat radius-bordered">
		<div class="widget-header bg-danger">
			<span class="widget-caption">用户详情</span>
		</div>
		<div id="registration-form">
			<form role="form">
				<div class="form-title"></div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>真实姓名</label> <span class="input-icon icon-right"> <input
							type="text" disabled ng-model="apply1.realName"
							class="form-control">
						</span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>联系电话</label> <span class="input-icon icon-right"> <input
							type="text" disabled ng-model="apply1.mobile"
							class="form-control">
						</span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>身份证正面</label> <span class="input-icon icon-right"> <img
							style="width: 100%; height: 350px"
							ng-src="{{apply1.idCardFront}}">
						</span>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label>身份证反面</label> <span class="input-icon icon-right"> <img
							style="width: 100%; height: 350px" ng-src="{{apply1.idCardBack}}">
						</span>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
