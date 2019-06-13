<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.lay.com/option" prefix="cl" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript"
            src="resource/js/goods/services/GoodsEffectService.js"></script>
    <script type="text/javascript"
            src="resource/js/goods/controllers/GoodsEffectController.js"></script>
</head>
<body>
<div class="row" ng-controller="goodsEffectCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a> <a href="" widget-maximize></a>
                </div>
            </div>
            <div class="widget-body">
                <form class="form-inline">
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">商品名称</span> <input
                            ng-model="search.fullName" type="text" class="form-control">
                    </div>
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">类别</span>
                        <select ng-model="search.categoryId" ng-init="">
                            <option value="">全部</option>
                            <c:forEach items="${categorys}" var="category">
                                <option value="${category.categoryId}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">品牌</span>
                        <select ng-model="search.brandId" ng-init="">
                            <option value="">全部</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.brandId}">${brand.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">条形码</span> <input
                            ng-model="search.barcode" type="text" class="form-control">
                    </div>

                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">创建时间</span> <input type="text"
                                                                               class="form-control" datetimepicker
                                                                               ng-model="search.createTimeStart"/>
                        </div>
                        至
                        <div class="input-group">
                            <input type="text" class="form-control" datetimepicker
                                   ng-model="search.createTimeEnd"/>
                        </div>
                    </div>
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">添加人</span> <input
                            ng-model="search.createName" type="text" class="form-control">
                    </div>
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">创建者账号</span> <input
                            ng-model="search.userName" type="text" class="form-control">
                    </div>
                    <div class="input-group form-group form-group-margin">
                        <span class="input-group-addon">是否推荐商品</span>
                        <ui-select
                                on-select="search.isRecommend = $item.code"
                                ng-init='options=<cl:getByEnum enumName="com.linayi.enums.IsRecommendStatus" nameWithCode="getIsRecommend;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
                                ng-model="temp" style="min-width: 200px">
                            <ui-select-match>{{$select.selected.name}}</ui-select-match>
                            <ui-select-choices repeat="item in options | filter:{name: $select.search}">
                                <span ng-bind-html="item.name | highlight: $select.search"></span>
                            </ui-select-choices>
                        </ui-select>
                    </div>
                    <div>
                        <a ng-click="list()" class="btn btn-primary form-group-margin"
                           href="javascript:void(0);"> <i class="fa fa-search"></i>搜索
                        </a>
                        <a ng-click="exportData()" class="btn btn-primary form-group-margin"
                           href="javascript:void(0);"></i>导出列表
                        </a>
                    </div>

                </form>

                <!-- 表格 -->
                <table id="goodsList" style="width: 100%"></table>
                <!-- 分页 -->
                <div id="goodsPager"></div>

            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container
        toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>