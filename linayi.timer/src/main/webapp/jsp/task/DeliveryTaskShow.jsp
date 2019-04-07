<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='list'>
    <%=JSONObject.toJSONString(request.getAttribute("list"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">任务详情</span>
        </div>
        <div id="registration-form">
            <form role="form">
                <div class="form-title">
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>收货人</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.customerName"
                                       class="form-control">
                                <i class="fa fa-smile-o blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>收货人联系电话</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.mobile"
                                       class="form-control">
                                <i class="fa fa-phone blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>收货人联系电话</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.mobile"
                                       class="form-control">
                                <i class="fa fa-phone blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>收货人地址</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.address"
                                       class="form-control">
                                <i class="glyphicon glyphicon-globe blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>接单人</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.receiverName"
                                       class="form-control">
                                <i class="fa fa-smile-o blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>接单人联系电话</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.deliveryMobile"
                                       class="form-control">
                                <i class="fa  fa-phone blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>任务编号</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="list.deliveryTaskId"
                                       class="form-control">
                                <i class="glyphicon glyphicon-fire blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>金额</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       value="  {{ list.amount / 100 }}元"
                                       class="form-control">
                                <i class="fa  fa-money blue"></i>
                            </span>
                    </div>
                </div>


            </form>
        </div>
    </div>
</div>