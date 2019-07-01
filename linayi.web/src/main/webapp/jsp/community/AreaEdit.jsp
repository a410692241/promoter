<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='smallCommunityReq'>
    <%=JSONObject.toJSONString(request.getAttribute("smallCommunityReq"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">街道添加</span>
        </div>
        <div id="registration-form">
            <form role="form">
                <div class="form-title">
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>省</label>
                        <ui-select
                                on-select="selectedArea( $item,2 )" ng-model="areaCtrl.city" style="min-width: 200px">
                            <ui-select-match>{{$select.selected.name}}</ui-select-match>
                            <ui-select-choices repeat="item in provinceList | filter:{name: $select.search}">
                                <span ng-bind-html="item.name | highlight: $select.search"></span>
                            </ui-select-choices>
                        </ui-select>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>市</label>
                        <ui-select
                                on-select="selectedArea( $item,2 )" ng-model="areaCtrl.city" style="min-width: 200px">
                            <ui-select-match>{{$select.selected.name}}</ui-select-match>
                            <ui-select-choices repeat="item in areaCtrl.cityList | filter:{name: $select.search}">
                                <span ng-bind-html="item.name | highlight: $select.search"></span>
                            </ui-select-choices>
                        </ui-select>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="form-group">
                        <label>区</label>
                        <ui-select
                                on-select="selectedArea( $item,2 )" ng-model="areaCtrl.city" style="min-width: 200px">
                            <ui-select-match>{{$select.selected.name}}</ui-select-match>
                            <ui-select-choices repeat="item in areaCtrl.cityList | filter:{name: $select.search}">
                                <span ng-bind-html="item.name | highlight: $select.search"></span>
                            </ui-select-choices>
                        </ui-select>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="form-group">
                        <label>街道</label>
                        <span class="input-icon icon-right">
                                <input type="text"
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