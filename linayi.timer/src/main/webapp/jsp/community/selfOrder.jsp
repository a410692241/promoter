<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <script type="text/javascript" src="resource/js/community/services/SelfOrderService.js"></script>
    <script type="text/javascript" src="resource/js/community/controllers/SelfOrderController.js"></script>
</head>
<body>
<div class="row" ng-controller="orderCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a>
                    <a href="" widget-maximize></a>
                </div>
            </div>
            <div class="widget-body">
                <form class="form-inline">
                  <%--   <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">商品名称</span>
                            <ui-select
                                    on-select="search.communityId = $item.code"
                                    ng-init='options=<cl:selectBySQL sqlId="selectCommunityList"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                    ng-model="temp" style="min-width: 230px">
                                <ui-select-match>{{$select.selected.name}}</ui-select-match>
                                <ui-select-choices repeat="item in options | filter:{name: $select.search}">
                                    <span ng-bind-html="item.name | highlight: $select.search"></span>
                                </ui-select-choices>
                            </ui-select>
                        </div>
                    </div> --%>
                   <%--  <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">采买状态</span>
                            <ui-select
                                    on-select="search.communityStatus = $item.code"
                                    ng-init='options=<cl:getByEnum enumName="com.linayi.enums.OrderStatus" nameWithCode="getOrderTypeName;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                    ng-model="temp" style="min-width: 200px">
                                <ui-select-match>{{$select.selected.name}}</ui-select-match>
                                <ui-select-choices repeat="item in options | filter:{name: $select.search}">
                                    <span ng-bind-html="item.name | highlight: $select.search"></span>
                                </ui-select-choices>
                            </ui-select>
                        </div>
                    </div> --%>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">商品名称</span>
                            <input ng-model="search.goodsName" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">商品品牌</span>
                            <input ng-model="search.brandName" type="text" class="form-control">
                        </div>
                    </div>
                      <div class="form-group form-group-margin">
                          <div class="input-group">
                              <span class="input-group-addon">询价状态</span>
                              <ui-select
                                      on-select="search.status = $item.code"
                                      ng-init='options=<cl:getByEnum enumName="com.linayi.enums.SelfOrderStatus" nameWithCode="getSelfOrderStatus;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                      ng-model="temp" style="min-width: 200px">
                                  <ui-select-match>{{$select.selected.name}}</ui-select-match>
                                  <ui-select-choices repeat="item in options | filter:{name: $select.search}">
                                      <span ng-bind-html="item.name | highlight: $select.search"></span>
                                  </ui-select-choices>
                              </ui-select>
                          </div>
                      </div>
                   <!--  <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">用户名</span>
                            <input ng-model="search.mobile" type="text" class="form-control">
                        </div>
                    </div> -->
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">创建时间</span>
                            <input type="text" class="form-control" datetimepicker ng-model="search.createTimeStart"/>
                        </div>
                        至
                        <div class="input-group">
                            <input type="text" class="form-control" datetimepicker ng-model="search.createTimeEnd"/>
                        </div>
                    </div>
                    <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
                        <i class="fa fa-search"></i>搜索
                    </a>
                </form>

                <!-- 表格 -->
                <table id="selfOrderList" style="width: 100%"></table>
                <!-- 分页 -->
                <div id="selfOrderPager"></div>

            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>