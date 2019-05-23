<%-- <%@page import="com.alibaba.fastjson.JSONObject"%>
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
 --%>

<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<div class="row">
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label class="col-sm-4 no-padding-right" style="text-align: right;">真实姓名：</label>
			<div class="col-sm-8">{{apply1.realName}}</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label class="col-sm-4 no-padding-right" style="text-align: right;">联系电话：</label>
			<div class="col-sm-8">{{apply1.mobile}}</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12"
			ng-show="apply1.address">
			<label class="col-sm-4 no-padding-right" style="text-align: right;">详细地址：</label>
			<div class="col-sm-8">{{apply1.address}}</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12"
			ng-show="apply1.supermarketName">
			<label class="col-sm-4 no-padding-right" style="text-align: right;">超市名称：</label>
			<div class="col-sm-8">{{apply1.supermarketName}}</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label class="col-sm-4 no-padding-right" style="text-align: right;">身份证正面：</label>
			<div class="col-sm-8">
				<span class="input-icon icon-right"> <img
					style="width: 100%; height: 240px" ng-src="{{apply1.idCardFront}}">
				</span>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label class="col-sm-4 no-padding-right" style="text-align: right;">身份证反面：</label>
			<div class="col-sm-8">
				<span class="input-icon icon-right"> <img
					style="width: 100%; height: 240px" ng-src="{{apply1.idCardBack}}">
				</span>
			</div>
		</div>
		<div class="form-group col-lg-12 col-xs-12 col-sm-12"
			ng-show="apply1.orderMan">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">部门类型:</label>
			<div class="col-sm-10">
				<select id="promoterType" ng-click="promoterType()">
					<option value="">请选择部门类型</option>
					<option value="PROMOTER">推广商</option>
					<option value="COMMUNITY">服务商</option>
				</select>

			</div>
		</div>
		<div class="form-group col-lg-12 col-xs-12 col-sm-12" id="partDiv"
			hidden="true">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">部门:</label>
			<div class="col-sm-10">
				<select id="part">
				</select>
			</div>
		</div>
		<div class="form-group col-lg-12 col-xs-12 col-sm-12"
			ng-show="apply1.orderMan">
			<label for="inputEmail3"
				class="col-sm-2 control-label no-padding-right">家庭服务师级别:</label>
			<div class="col-sm-10">
				<select id="identity" ng-click="identity()">
					<option value="">请选择家庭服务师级别</option>
					<option value="ORDER_MAN">普通级别</option>
					<option value="LEGAL_MAN">主管级别</option>
				</select>
			</div>
		</div>
</form>