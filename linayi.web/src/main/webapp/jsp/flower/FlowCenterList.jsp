<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="resource/js/flower/services/FlowCenterService.js"></script>
    <script type="text/javascript" src="resource/js/flower/controllers/FlowCenterController.js"></script>
</head>
<body>
<div class="row" ng-controller="flowCenterCtrl">
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
                <form class="form-inline" id="correct">
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">配送波次</span>
                            <select ng-model="search.deliveryWaveTime" ng-change="getType(search.deliveryWaveTime)" ng-init="search.deliveryWaveTime='showAll'">
                                <option value="showAll">全部</option>
                                <option value="one">第一波次</option>
                                <option value="two">第二波次</option>
                                <option value="three">第三波次</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">订单状态</span>
                            <select ng-model="search.receiveStatus" ng-change="getType(search.receiveStatus)" ng-init="search.receiveStatus='showAll'">
                                <option value="showAll">全部</option>
                                <option value="PROCURING">采买中</option>
                                <option value="BOUGHT">已采买</option>
                                <option value="OUTED">已提货</option>
                                <option value="RECEIVED_FLOW">待发货</option>
                                <option value="WAIT_RECEIVE">已发货</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">开始时间</span>
                            <input ng-model="search.startTime" datetimepicker type="text" class="form-control" style="min-width: 150px">
                        </div>
                    </div>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">结束时间</span>
                            <input ng-model="search.endTime" datetimepicker type="text" class="form-control" style="min-width: 150px">
                        </div>
                    </div>

                    <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
                        <i class="fa fa-search"></i>搜索
                    </a>
                    <a ng-click="batchDelivery()" class="btn btn-primary form-group-margin" href="javascript:void(0);"></i>发货</a>
                    <a ng-click="exportData()" class="btn btn-primary form-group-margin" href="javascript:void(0);"></i>导出列表</a>
                    <%--<a class="btn btn-primary form-group-margin" href="/web/correct/correct/exportShareRecord.do"></i>导出列表</a>--%>
                </form>

                <!-- 表格 -->
                <table id="correctList" style="width: 100%"></table>
                <!-- 分页 -->
                <div id="correctPager"></div>

            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container
        toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>