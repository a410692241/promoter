<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/user/services/UserService.js"></script>
<script type="text/javascript" src="resource/js/user/controllers/UserController.js"></script>
</head>
<body>
<div class="row" ng-controller="userCtrl">
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
            	<form class="form-inline">
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">名称</span>
		                            <input ng-model="search.nickname" type="text" class="form-control">
		                        </div>
		                    </div>
							
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">手机号</span>
		                            <input ng-model="search.mobile" type="text" class="form-control">
		                        </div>
		                    </div>
		                    <div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">qq号</span>
		                            <input ng-model="search.qq" type="text" class="form-control">
		                        </div>
		                    </div>
		                    <div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">真实姓名</span>
		                            <input ng-model="search.realName" type="text" class="form-control">
		                        </div>
		                    </div>
							<div class="form-group form-group-margin">
								<div class="input-group">
									<span class="input-group-addon">用户编号</span>
									<input ng-model="search.userId" type="text" class="form-control">
								</div>
							</div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                           <%-- <span class="input-group-addon">用户类型</span>
		                             <ui-select
								   		on-select="search.userType=$item.code"
								   		ng-init='
								   			options=<cx:out-options dictMapCd="userUserType" />;
								   			options.splice(0,0,{name:"全部",code:""});
								   		'
									   	ng-model="temp" style="min-width: 200px">
							            <ui-select-match>{{$select.selected.name}}</ui-select-match>
							            <ui-select-choices repeat="item in options | filter: {name:$select.search}">
							                <span ng-bind-html="item.name | highlight: $select.search"></span>
							            </ui-select-choices>
							        </ui-select> --%>
		                        </div>
		                    </div>
		                    <%-- <div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">性别</span>
		                            <ui-select
								   		on-select="search.sex=$item.code"
								   		ng-init='
								   			options=<cx:out-options dictMapCd="userSex" />;
								   			options.splice(0,0,{name:"全部",code:""});
								   		'
									   	ng-model="temp" style="min-width: 200px">
							            <ui-select-match>{{$select.selected.name}}</ui-select-match>
							            <ui-select-choices repeat="item in options | filter: {name:$select.search}">
							                <span ng-bind-html="item.name | highlight: $select.search"></span>
							            </ui-select-choices>
							        </ui-select> 
		                        </div>
		                    </div> --%>
							<div class="form-group form-group-margin">
							<div class="input-group">
								<span class="input-group-addon">创建时间</span> <input type="text"
									class="form-control" datetimepicker
									ng-model="search.createTimeStart" />
							</div>
							至
							<div class="input-group">
								<input type="text" class="form-control" datetimepicker
									ng-model="search.createTimeEnd" />
							</div>
						</div>
				  <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>
				  
				  <%--<a ng-click="edit()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-plus"></i>新增
				  </a>--%>
				  
				  <!--  <a ng-click="batchDisable()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-times"></i>批量禁用
				  </a> -->
				  
				</form>
				
				<!-- 表格 -->
                <table id="userList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="userPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>