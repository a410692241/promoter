<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="resource/js/settlement/controllers/PromoterSettleController.js"></script>
    <script type="text/javascript" src="resource/js/settlement/services/PromoterSettleService.js"></script>
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
                    <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
                        <i class="fa fa-search"></i>搜索
                    </a>
                    <a ng-click="exportData()" class="btn btn-primary form-group-margin"
                       href="javascript:void(0);"></i>导出列表
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
</body>
</html>
