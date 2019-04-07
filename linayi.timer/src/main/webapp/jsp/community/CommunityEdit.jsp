<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div init-model='community'>
	<%=JSONObject.toJSONString(request.getAttribute("community"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
	<div class="widget flat radius-bordered">
		<div class="widget-header bg-danger">
			<span class="widget-caption">社区编辑</span>
		</div>
		<div id="registration-form">
			<form role="form" id="supermarketInfoForm" >
				<div class="form-title">
				</div>
				<input type="hidden" ng-model="community.communityId" />
				<div class="col-sm-6">
					<div class="form-group">
						<label>社区名称</label>
						<span class="input-icon icon-right">
                                <input type="text" 
									   ng-model="community.name"
									   class="form-control">
                                <i class="glyphicon glyphicon-fire blue"></i>
                            </span>
					</div>
				</div>


				<div class="col-sm-6">
					<div class="form-group">
						<label>手机号</label>
						<span class="input-icon icon-right">
                                <input type="text" 
									   ng-model="community.mobile"
									   class="form-control">
                                <i class="fa fa-mobile-phone blue"></i>
                            </span>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label>邮箱</label>
						<span class="input-icon icon-right">
                                <input type="text" 
									   ng-model="community.email"
									   class="form-control">
                                <i class="fa fa fa-envelope blue"></i>
                            </span>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label>QQ号</label>
						<span class="input-icon icon-right">
                                <input type="text" 
									   ng-model="community.qq"
									   class="form-control">
                                <i class="glyphicon glyphicon-comment blue"></i>
                            </span>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label>选择街道</label>
						<span class="input-icon icon-right">
                                    <input ng-required="required" type="text" readonly="readonly" ng-click="selectArea()" ng-model="community.areaName" class="form-control">
                    <input ng-required="required" type="hidden" readonly="readonly" ng-click="selectArea()" ng-model="community.areaCode" class="form-control">
                                    <i class="glyphicon glyphicon-map-marker blue"></i>
                                </span>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label>地址</label>
						<span class="input-icon icon-right">
                                <input type="text" 
									   ng-model="community.address"
									   class="form-control">
                                <i class="glyphicon glyphicon-map-marker blue"></i>
                            </span>
					</div>
				</div>


			</form>
		</div>
	</div>
</div>

</form>

