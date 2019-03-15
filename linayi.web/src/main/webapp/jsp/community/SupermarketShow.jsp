<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='supermarket'>
<%=JSONObject.toJSONString( request.getAttribute( "supermarket" ) )%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
	<div class="widget flat radius-bordered">
		<div class="widget-header bg-danger">
			<span class="widget-caption">超市信息</span>
		</div>
		<div id="registration-form">
			<form role="form">
				<div class="form-title">
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<label>名称</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   ng-model="supermarket.name"
									   class="form-control">
                                <i class="glyphicon glyphicon-shopping-cart blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>超市图标</label>
						<span class="input-icon icon-right">
                                <img style="width: 100%;height:250px"  ng-src="{{supermarket.logo}}">
						</span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>地址</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   ng-model="supermarket.areaName"
									   class="form-control">
                                <i class="glyphicon glyphicon-map-marker blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>地址细节</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   ng-model="supermarket.address"
									   class="form-control">
                                <i class="glyphicon glyphicon-map-marker blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>绑定采买员</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   ng-model="supermarket.procurerId"
									   class="form-control">
                                <i class="fa fa-bookmark blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<label>绑定用户</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   ng-model="supermarket.userId"
									   class="form-control">
                                <i class="fa fa-bookmark blue"></i>
                            </span>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label>创建时间</label>
						<span class="input-icon icon-right">
                                <input type="text" disabled
									   value="{{supermarket.createTime | date:'yyyy-MM-dd HH:mm:ss'}}"
									   class="form-control">
                                <i class="glyphicon glyphicon-time blue"></i>
                            </span>
					</div>
				</div>
				<div class="col-sm-6">
				<div class="form-group">
					<label>最后修改时间</label>
					<span class="input-icon icon-right">
                                <input type="text" disabled
									   value="{{supermarket.updateTime | date:'yyyy-MM-dd HH:mm:ss'}}"
									   class="form-control">
                                <i class="glyphicon glyphicon-time green"></i>
                            </span>
				</div>
			</div>
			</form>
		</div>
	</div>
</div>