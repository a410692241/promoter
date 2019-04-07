<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/system/controllers/menuListController.js"></script>
<style type="text/css">
	#menuInfo>div>div>label{
		text-align: right;
		display: inline-block;
		width: 100px
	}
	
	#menuInfo>div>div{
		margin-bottom: 5px
	}
	
	#menuInfo>div>div>div>input{
		width: 400px;
	}
</style>
</head>
<body>
<div class="row" ng-controller="menuListCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a>
                    <a href="" widget-maximize></a>
                </div>
            </div>
            <div class="widget-body" >
				<div style="width: 600px;display: inline-block;vertical-align: top;max-height: 600px;overflow: auto;">
					<div data-drag ui-tree-nestable="options" dragMove ng-model="privilageList">
						{{node.text}}
						<div data-nodrag style="text-align: right;float: right;">
								<a ng-show="!node.id" href="javascript:void(0);" ng-click="collapseAll()" class="btn btn-primary fa fa-minus btn-sm td-compile ng-scope">折叠菜单</a>
								<a ng-show="!node.id" href="javascript:void(0);" ng-click="expandAll()" class="btn btn-primary fa fa-plus btn-sm td-compile ng-scope">展开菜单</a>
								<a href="javascript:void(0);"  ng-click="addChild( $event )" class="btn btn-primary fa fa-plus btn-sm td-compile ng-scope">添加子菜单</a>
								<a ng-show="node.id" href="javascript:void(0);" ng-click="edit( $event )" class="btn btn-primary fa fa-edit btn-sm td-compile ng-scope">编辑菜单</a>
								<a ng-show="node.id" href="javascript:void(0);" ng-click="deletePrivilage( {{node}})"  class="btn btn-primary fa fa-remove btn-sm td-compile ng-scope">删除菜单</a>
						</div>
					</div>
				</div>
				<div style="display: inline-block;" id="menuInfo" ng-show="enableView">
					<div >
						<div ng-show="privilage.depth > 2 ">
						   <label class="no-padding-right">父菜单&nbsp;</label>
						   <div style="display: inline-block;">
						   		{{privilage.parentText}}
						   		<span ng-model="privilage.category" style="display:none;">{{privilage.category}}</span>
						   </div>
						</div>
						<div >
						   <label class="no-padding-right">菜单名称&nbsp;</label>
						   <div style="display: inline-block;">
						   		<input ng-model="privilage.text" type="text" class="form-control">
						   </div>
						</div>
						<div ng-show="privilage.depth > 2 ">
						   <label class="no-padding-right">菜单URL&nbsp;</label>
						   <div style="display: inline-block;">
						   		<input ng-model="privilage.url" type="text" class="form-control">
						   </div>
						</div>
						<!--  
						<div >
						   <label class="no-padding-right">权限编码&nbsp;</label>
						   <div style="display: inline-block;">
						   		<input ng-model="privilage.privilegeCode" type="text" class="form-control">
						   </div>
						</div>
						-->
						<div >
						   <label class="no-padding-right">状态&nbsp;</label>
						   <div style="display: inline-block;">
						   		<div class="radio" style="display: inline;" ng-click="changeStatus( 'ENABLED' )">
	                                <label>
	                                    <input name="status" type="radio" ng-checked=" privilage.status == 'ENABLED' ? true: false" class="colored-blue">
	                                    <span class="text">启用</span>
	                                </label>
                            	</div>
                            	&nbsp;&nbsp;
						   		<div class="radio" style="display: inline;" ng-click="changeStatus( 'DISABLED' )">
	                                <label>
	                                    <input name="status" type="radio" ng-checked=" privilage.status != 'ENABLED'  ?  true : false " class="colored-blue">
	                                    <span class="text">禁用</span>
	                                </label>
                            	</div>
						   </div>
						</div>
						<div ng-show="privilage.depth == 3 ">
						   <label class="no-padding-right" >是否显示在首页&nbsp;</label>
						   <div style="display: inline-block;" >
						   		<div class="radio" style="display: inline;" ng-click="changeCommon(1)">
	                                <label>
	                                    <input name="common" type="radio" ng-checked=" privilage.common==1 ?  true : false " class="colored-blue">
	                                    <span class="text">是</span>
	                                </label>
                            	</div>
                            	&nbsp;&nbsp;
						   		<div class="radio" style="display: inline;" ng-click="changeCommon(2)">
	                                <label>
	                                    <input name="common" type="radio" ng-checked="privilage.common != 1 ?  true : false " class="colored-blue">
	                                    <span class="text">否</span>
	                                </label>
                            	</div>
						   </div>
						</div>
						<div ng-show="privilage.depth == 3 && privilage.common==1">
						   <label class="no-padding-right">首页ICON&nbsp;</label>
						   <div style="display: inline-block;">
						   		<div id="menuIcon" ui-imagebox ng-model="privilage.icon" style="max-width: 200px;max-height: 200px;"></div>
						   </div>
						</div>
						</div>
						<div style="padding-left: 105px;">
							<a ng-click="save(privilage.code)" class="btn btn-primary" href="javascript:void(0);"> 
								<i class="fa fa-save"></i>确认
							</a>
							<a ng-click="cancel()" class="btn btn-primary" href="javascript:void(0);"> 
								<i class="fa fa-remove"></i>取消
							</a>
						</div>
					</div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>