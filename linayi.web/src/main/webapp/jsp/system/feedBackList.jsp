<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/system/controllers/feedbackController.js"></script>
<script type="text/javascript" src="resource/js/system/services/feedbackService.js"></script>
</head>
<body>
<div class="row" ng-controller="feedbackCtrl">
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
		                           <span class="input-group-addon">类型</span>
		                           <ui-select on-select="search.problemType=$item.code" ng-init='options=<cx:out-options dictMapCd="feedbackProblemType"></cx:out-options>;options.splice(0,0,{code:"",name:"全部"});temp=options[0]' ng-model="temp" theme="bootstrap" style="min-width: 200px">
		                                 <ui-select-match>{{$select.selected.name}}</ui-select-match>
		                                 <ui-select-choices repeat="item in options | filter: $select.search">
		                                     <span ng-bind-html="item.name | highlight: $select.search"></span>
		                                 </ui-select-choices>
		                           </ui-select>
		                       </div>
			                </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">提交时间</span>
					                <input type="text" class="form-control" datetimepicker options="{timepicker:false}"  ng-model="search.submitTimeStart"/>
		                     	</div>
		                    </div>
							<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">至</span>
					                <input type="text" class="form-control" datetimepicker options="{timepicker:false}" ng-model="search.submitTimeEnd"/>
		                     	</div>
		                    </div>
				  <a ng-click="feedbackCtrl.list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>
				</form>
				
				<!-- 表格 -->
                <table id="feedbackList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="feedbackPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>