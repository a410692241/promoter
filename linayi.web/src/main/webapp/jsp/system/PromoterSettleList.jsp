<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="resource/js/system/services/PromoterSettleService.js"></script>
    <script type="text/javascript" src="resource/js/system/controllers/PromoterSettleController.js"></script>
</head>
<body>
<div class="row" ng-controller="promoterCtrl">
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
                <form class="form-inline" id="promoter">
                    <%--<div class="input-group form-group form-group-margin">--%>
                    <%--<span class="input-group-addon">推广商等级</span>--%>
                    <%--<ui-select on-select="search.categoryId=$item.code;"--%>
                    <%--ng-init='options=<cl:selectBySQL sqlId="categoryList" param="{level:4}"/>;--%>
                    <%--options.splice(0,0,{"name":"全部","code":""});'--%>
                    <%--ng-model="temp" style="min-width: 230px">--%>
                    <%--<ui-select-match>{{$select.selected.name}}</ui-select-match>--%>
                    <%--<ui-select-choices--%>
                    <%--repeat="item in options | filter:{name: $select.search}">--%>
                    <%--<span ng-bind-html="item.name | highlight: $select.search"></span>--%>
                    <%--</ui-select-choices>--%>
                    <%--</ui-select>--%>
                    <%--</div>--%>
                    <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
                        <i class="fa fa-search"></i>搜索
                    </a>
                </form>

                <!-- 表格 -->
                <table id="promoterList" style="width: 100%"></table>
                <!-- 分页 -->
                <div id="promoterPager"></div>

            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container
        toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>