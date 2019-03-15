<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='correct'>
    <%=JSONObject.toJSONString(request.getAttribute("correct"))%>
</div>

<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">价格分享</span>
        </div>
        <div id="registration-form">
            <form role="form">
                <div class="form-title">
                </div>
                <div class="col-sm-6">
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">超市</span>
                            <ui-select
                                    on-select="correct.supermarketId = $item.code"
                                    ng-init='options=<cl:selectBySQL sqlId="selectSupermarket"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                    ng-model="temp" style="min-width: 230px">
                                <ui-select-match>{{$select.selected.name}}</ui-select-match>
                                <ui-select-choices repeat="item in options | filter:{name: $select.search}">
                                    <span ng-bind-html="item.name | highlight: $select.search"></span>
                                </ui-select-choices>
                            </ui-select>
                        </div>
                    </div>
                </div>
                <%--<div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">纠错</label><label>员</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="correct.user.nickname"
                                       class="form-control">
                                <i class="glyphicon glyphicon-user blue"></i>
                            </span>
                    </div>
                </div>--%>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">分享</label><label>价格(元)</label>
                        <span class="input-icon icon-right">
                                <input type="text" ng-model="correct.price"
                                       class="form-control">
                                <i class="fa fa-money palegreen"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">

                <div class="form-group">
                    <label>价格类型</label>
                    <ui-select
                            on-select="correct.priceType=$item.code"
                            ng-init='options=<cl:getByEnum enumName="com.linayi.enums.PriceType" nameWithCode="getPriceTypeName;name"/>;
                            temp=<cl:valueByKey enumName="com.linayi.enums.PriceType" code="${correct.priceType}" name="getPriceTypeName"/>;'
                            ng-model="temp">
                        <ui-select-match>{{$select.selected.name}}</ui-select-match>
                        <ui-select-choices repeat="item in options | filter: {name:$select.search}">
                            <span ng-bind-html="item.name | highlight: $select.search"></span>
                        </ui-select-choices>
                    </ui-select>
                </div>
            </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label ng-show="correct.type!=='SHARE'">分享</label><label>开始自</label>
                        <span class="input-icon icon-right">
                                    <input ng-model="correct.startTime" datetimepicker type="text" class="form-control"/>
                                <i class="glyphicon glyphicon-time palegreen"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label ng-show="correct.type!=='SHARE'">分享</label><label>结束自</label>
                        <span class="input-icon icon-right">
                            <%--<input ng-model="search.startTime" datetimepicker type="text" class="form-control" style="min-width: 150px">--%>
                                <input type="text" ng-model="correct.endTime" datetimepicker class="form-control">
                                <i class="glyphicon glyphicon-time palegreen"></i>
                            </span>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

