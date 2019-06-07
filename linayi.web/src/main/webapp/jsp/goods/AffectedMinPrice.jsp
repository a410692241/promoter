<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="resource/js/goods/services/CorrectService.js"></script>
    <script type="text/javascript" src="resource/js/goods/controllers/AffectedMinPrice.js"></script>
</head>
<body>
<div class="row" ng-controller="correctCtrl">
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
                            <span class="input-group-addon">商品ID</span>
                            <input ng-model="search.goodsSkuId" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group form-group-margin">
                    <div class="input-group">
                        <span class="input-group-addon">商品名</span>
                        <input ng-model="search.fullName" type="text" class="form-control">
                    </div>
                </div>

                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">超市名</span>
                            <input ng-model="search.name" type="text" class="form-control">
                        </div>
                    </div>

                    <%--<div class="form-group form-group-margin">--%>
                        <%--<div class="input-group">--%>
                            <%--<span class="input-group-addon">实际开始时间</span> <input type="text"--%>
                                                                               <%--class="form-control" datetimepicker--%>
                                                                               <%--ng-model="search.createTimeStart"/>--%>
                        <%--</div>--%>

                        <%--至--%>
                        <%--<div class="input-group">--%>
                            <%--<input type="text" class="form-control" datetimepicker--%>
                                   <%--ng-model="search.createTimeEnd"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>



                    <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
                        <i class="fa fa-search"></i>搜索
                    </a>

                    <a ng-click="exportData()" class="btn btn-primary form-group-margin" href="javascript:void(0);"></i>导出列表</a>
                    <%--<a class="btn btn-primary form-group-margin" href="/web/correct/correct/exportShareRecord.do"></i>导出列表</a>--%>
                </form>

                <!-- 表格 -->
                <table id="affectedMinPirceList" style="width: 100%"></table>
                <!-- 分页 -->
                <div id="affectedMinPircePager"></div>

            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container
        toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>