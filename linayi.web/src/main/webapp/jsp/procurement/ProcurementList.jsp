<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.lay.com/option" prefix="cl" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <script type="text/javascript"
            src="resource/js/procurement/controllers/ProcurementController.js"></script>
</head>
<body>
<div class="row" ng-controller="procurementCtrl">
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
                    <div>
                        <a ng-click="list()" class="btn btn-primary form-group-margin"
                           href="javascript:void(0);"> <i class="fa fa-search"></i>搜索
                        </a><%-- <a ng-click="addUI()" class="btn btn-primary form-group-margin"
								href="javascript:void(0);"> <i class="fa fa-plus"></i>新增
							</a> <a ng-click="importUI()"
								class="btn btn-primary form-group-margin"
								href="javascript:void(0);"> <i
								class="glyphicon glyphicon-import"></i>导入
							</a> <a ng-click="changeStatusList(3)"
								class="btn btn-primary form-group-margin"
								href="javascript:void(0);"> <i class="fa fa-eye"></i>批量上架
							</a> <a ng-click="changeStatusList(2)"
								class="btn btn-primary form-group-margin"
								href="javascript:void(0);"> <i class=" fa fa-eye"></i>批量下架
							</a> <a ng-click=removeList()
								class="btn btn-primary form-group-margin"
								href="javascript:void(0);"> <i class=" fa fa-remove"></i>批量刪除
							</a>--%>
                        <a ng-click="exportData()" class="btn btn-primary form-group-margin"
                           href="javascript:void(0);"></i>导出列表
                        </a>
                    </div>

                </form>

                <!-- 表格 -->
                <table id="procureList" style="width: 100%"></table>
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
