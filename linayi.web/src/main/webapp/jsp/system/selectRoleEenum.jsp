<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<%@ taglib uri="/tags-cx" prefix="cx" %>--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/system/controllers/selectRoleEenumController.js"></script>
<script type="text/javascript" src="resource/js/system/services/selectRoleEenumService.js"></script>
</head>
<body>
<div class="row" ng-controller="selectRoleCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a>
                    <a href="" widget-maximize></a>
                </div>
            </div>
           <%-- <div class="widget-body">
            	<form class="form-inline">
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">角色名</span>
		                            <input ng-model="search.roleName" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">状态</span>
		                            <input ng-model="search.status" type="text" class="form-control">
		                        </div>
		                    </div>--%>
                    <%--<div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">更新时间</span>
                            <input ng-model="search.updateTime" datetimepicker class="form-control">
                        </div>
                    </div>
                    <div class="form-group form-group-margin">
                        <div class="input-group">
                            <span class="input-group-addon">创建时间</span>
                            <input ng-model="search.createTime" datetimepicker class="form-control">
                        </div>
                    </div>--%>
							<%--<div class="form-group form-group-margin">
		                       <div class="input-group">
		                           <span class="input-group-addon">用户状态</span>
		                           <ui-select on-select="search.status=$item.code" ng-init='options=[{code:1000,name:"正常"},{code:1001,name:"禁用"}];options.splice(0,0,{code:"",name:"全部"});temp=options[0]' ng-model="temp" theme="bootstrap" style="min-width: 200px">
		                                 <ui-select-match>{{$select.selected.name}}</ui-select-match>
		                                 <ui-select-choices repeat="item in options | filter: $select.search">
		                                     <span ng-bind-html="item.name | highlight: $select.search"></span>
		                                 </ui-select-choices>
		                           </ui-select>
		                       </div>
			                </div>--%>
				 <%-- <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>--%>
				  
				  <%--<a ng-click="edit()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-plus"></i>新增
				  </a>--%>


				</form>
				
				<!-- 表格 -->
                <table id="selectUserList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="selectUserPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>