<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='smallCommunityReq'>
    <%=JSONObject.toJSONString(request.getAttribute("smallCommunityReq"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">待添加小区信息</span>
        </div>
        <div id="registration-form">
            <form role="form">
                <div class="form-title">
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>小区</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="smallCommunityReq.smallCommunity"
                                       class="form-control">
                                <i class="fa fa-bookmark blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>申请人昵称</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="smallCommunityReq.nickname"
                                       class="form-control">
                                <i class="fa fa-bookmark blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>申请人手机号</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="smallCommunityReq.mobile"
                                       class="form-control">
                                <i class="fa fa-bookmark blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>查看状态</label>
                        <span class="input-icon icon-right" ng-switch on="smallCommunityReq.status">

                            <input type="text" ng-switch-when="PROCESSED" disabled
                                   value="已查看"
                                   class="form-control">

                            <input type="text" ng-switch-when="NOTVIEWED" disabled
                                   value="未查看"
                                   class="form-control">
                            <i class="glyphicon  glyphicon-th-list blue"></i>
                                </span>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>创建时间</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       value="{{smallCommunityReq.createTime | date:'yyyy-MM-dd HH:mm:ss'}}"
                                       class="form-control">
                                <i class="glyphicon glyphicon-time blue"></i>
                            </span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>