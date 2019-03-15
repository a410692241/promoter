<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<form role="form" id="supermarketInfoForm" role="form" method="post" enctype="multipart/form-data">
<input type="hidden" ng-model="supermarket.supermarketId"/>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">编辑超市信息</span>
        </div>
        <div id="registration-form">

                <div class="form-title">
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>名称</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="supermarket.name"
                                       class="form-control">
                                <i class="fa fa-bookmark blue"></i>
                            </span>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label>超市LOGO</label>
                        <div  name="logoFile" style="width: 100%;height:100%;border: 1px solid #D5D5D5;" ui-imagebox ng-model="supermarket.logo"></div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>选择街道</label>
                        <span class="input-icon icon-right">
                                    <input ng-required="required" type="text" readonly="readonly" ng-click="selectArea()" ng-model="supermarket.areaName" class="form-control">
                    <input ng-required="required" type="hidden" readonly="readonly" ng-click="selectArea()" ng-model="supermarket.areaCode" class="form-control">
                                    <i class="glyphicon glyphicon-map-marker blue"></i>
                                </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>地址细节</label>
                        <span class="input-icon icon-right">
                                <input type="text"
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
                                    <input type="text"
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
                                        <input type="text"
                                               ng-model="supermarket.userId"
                                               class="form-control">
                                        <i class="fa fa-bookmark blue"></i>
                                    </span>
                    </div>
                </div>

        </div>

    </div>
</div>
</form>