
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/system/controllers/AdSelectGoodsCategoryController.js"></script>
<style type="text/css">
.form-group-margin{
	margin-top: 15px !important;
}
</style>
</head>
<body>
<div class="row" ng-controller="AdCategoryCtrl" id="AdCategoryCtrl">
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
            		{{node.name}}
            		<div style="text-align: right;float: right;" ng-show="node.level == 3">
            			<div class="checkbox" style="margin-top: 0px;">
				           <label ng-click="selectCategory(node)">
				               <input type="radio"  name="category" class="colored-danger">
				               <span class="text">选择</span>
				           </label>
				        </div>
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