<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model="correct" hidden>
    <%=JSONObject.toJSON(request.getAttribute("correct"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">纠错查看</span>
        </div>
        <div id="registration-form">
            <form role="form">
                <div class="form-title">
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label>商品</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="correct.goodsSku.name"
                                       class="form-control">
                                <i class="glyphicon glyphicon-fire blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>超市</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="correct.supermarket.name"
                                       class="form-control">
                                <i class="glyphicon glyphicon-shopping-cart palegreen"></i>
                            </span>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">纠错</label><label>员</label>
                        <span class="input-icon icon-right">
                                <input type="text" disabled
                                       ng-model="correct.user.nickname"
                                       class="form-control">
                                <i class="glyphicon glyphicon-user blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">纠错</label><label>价格(元)</label>
                        <span class="input-icon icon-right">
                                <input type="text" value="{{correct.price/100}}" disabled
                                       class="form-control">
                                <i class="fa fa-money palegreen"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">纠错</label><label>价格类型</label>
                        <span class="input-icon icon-right" ng-switch on="correct.priceType">

                            <input ng-switch-when="NORMAL" type="text" disabled
                                   value="正常价"
                                   class="form-control">

                            <input type="text" ng-switch-when="PROMOTION" disabled
                                   value="促销价"
                                   class="form-control">
                            <input type="text" ng-switch-when="DEAL" disabled
                                   value="处理价"
                                   class="form-control">
                            <input type="text" ng-switch-when="MEMBER" disabled
                                   value="会员价"
                                   class="form-control">

                             <input ng-switch-default="" type="text" disabled
                                    value=""
                                    class="form-control">
                                <i class="glyphicon  glyphicon-th-list blue"></i>
                                </span>
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">纠错</label><label>价格有效期</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       value="{{correct.startTime|date:'yyyy-MM-dd HH:mm:ss'}} 至 {{correct.endTime|date:'yyyy-MM-dd HH:mm:ss'}}"
                                       disabled
                                       class="form-control">
                                <i class="glyphicon glyphicon-time palegreen"></i>
                            </span>
                    </div>
                </div>
                <div ng-show="correct.type!=='SHARE'">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label ng-show="correct.type==='SHARE'">分享</label><label
                            ng-show="correct.type!=='SHARE'">纠错</label><label>上传图片</label>
                        <span class="input-icon icon-right">
                               <img style="width: 100%;height:230px" ng-src="{{correct.parentImage}}">
                            </span>
                    </div>
                </div>
            </div>
                <div ng-show="correct.type==='SHARE'">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label ng-show="correct.type==='SHARE'">分享</label><label
                                ng-show="correct.type!=='SHARE'">纠错</label><label>上传图片</label>
                            <span class="input-icon icon-right">
                               <img style="width: 100%;height:460px" ng-src="{{correct.image}}">
                            </span>
                        </div>
                    </div>
                </div>

                <div class="col-sm-6" ng-show="correct.type!=='SHARE'">
                    <div class="form-group">
                        <label>证据图片</label>
                        <span class="input-icon icon-right">
                                <img style="width: 100%;height:230px" ng-src="{{correct.image}}">
                            </span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
