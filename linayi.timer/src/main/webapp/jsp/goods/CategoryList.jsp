<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/goods/services/CategoryService.js"></script>
<script type="text/javascript" src="resource/js/goods/controllers/CategoryController.js"></script>
<style type="text/css">
.form-group-margin{
	margin-top: 15px !important;	
}
.unitColor{
	background-color:yellow;
}
</style>
</head>
<body>
<div class="row" ng-controller="categoryCtrl">
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
            <div class="row">
            	<div class="col-lg-4 col-xs-4 col-sm-6">
            	<div  ui-tree-nestable="options" ng-model="categroyList">
            		<span>{{node.name}}</span>
					<div style="text-align: right;float: right;">
					<a ng-show="!node.parentId" href="javascript:void(0);" ng-click="collapseAll()" class="btn btn-primary fa fa-minus btn-sm td-compile ng-scope">折叠菜单</a>
								<a ng-show="!node.parentId" href="javascript:void(0);" ng-click="expandAll()" class="btn btn-primary fa fa-plus btn-sm td-compile ng-scope">展开菜单</a>	
						<a href="javascript:void(0);" ng-click="add( $event,node,1 )" class="btn btn-primary fa fa-plus btn-sm td-compile ng-scope">添加</a>
						<a ng-click="edit( $event,node,2 )" ng-style="{visibility: node.categoryId ? 'visible':'hidden'}" href="javascript:void(0);" class="btn btn-primary fa fa-edit btn-sm td-compile ng-scope">编辑</a>
						<!-- 去掉删除控件 -->
						<!-- <a ng-click="removes( $event,node )" ng-style="{visibility: node.categoryId ? 'visible':'hidden'}" href="javascript:void(0);" class="btn btn-primary fa fa-remove btn-sm td-compile ng-scope">删除</a> -->
					</div>
				</div>
				</div>
			</div>
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>