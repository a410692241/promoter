<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/community/services/CommunityLocationService.js"></script>
<script type="text/javascript" src="resource/js/community/controllers/CommunityLocationController.js"></script>
<style>
.modal-dialog{
	width: 70%;
}
</style>
</head>
<body>
<div class="row" ng-controller="communityLocationCtrl">
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
            		
<!-- 							<div ng-if="!fromCommunity" class="form-group form-group-margin"> -->
<!-- 		                        <div class="input-group"> -->
<!-- 	                            <span class="input-group-addon">社区</span> -->
<!-- 			            		<ui-select -->
<!-- 								   	on-select="search.communityId = $item.code"  -->
<%-- 								   	ng-init=' --%>
<!-- 									   	options=<cx:out-sql sqlId="options.listCommunity"></cx:out-sql>; -->
<!-- 									   	options.splice(0,0,{"name":"全部","code":""}); -->
<%-- 									   	temp=options[0]' --%>
<!-- 									   ng-model="area" style="min-width: 210px"> -->
<!-- 							           <ui-select-match>{{$select.selected.name}}</ui-select-match> -->
<!-- 							           <ui-select-choices repeat="item in options | filter:{name: $select.search}"> -->
<!-- 							               <span ng-bind-html="item.name | highlight: $select.search"></span> -->
<!-- 							           </ui-select-choices> -->
<!-- 							     </ui-select> -->
<!-- 		                        </div> -->
<!-- 		                    </div> -->
		                    
<!-- 		                    <div class="form-group form-group-margin" ng-show="false"> -->
<!-- 		                        <div class="input-group"> -->
<!-- 		                            <input ng-model="search.communityId" type="text" class="form-control"> -->
<!-- 		                        </div> -->
<!-- 		                    </div> -->
		                     <div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">社区</span>
		                            <input ng-model="search.communityName" type="text" class="form-control">
		                        </div>
		                    </div>
		                    <div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">街道</span>
		                            <input ng-model="search.streetName" type="text" class="form-control">
		                        </div>
		                    </div>
		                    <div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">小区</span>
		                            <input ng-model="search.name" type="text" class="form-control">
		                        </div>
		                    </div>
					<div class="form-group form-group-margin">
						<div class="input-group">
							<span class="input-group-addon">来源</span>
							<ui-select on-select="search.source=$item.code;"
									   ng-init='options=<cl:getByEnum enumName="com.linayi.enums.SourceType" nameWithCode="getSourceTypeName;name"/>;
                                   options.splice(0,0,{"name":"全部","code":""});'
									   ng-model="temp" style="min-width: 178px">
								<ui-select-match>{{$select.selected.name}}</ui-select-match>
								<ui-select-choices
										repeat="item in options | filter:{name: $select.search}">
									<span ng-bind-html="item.name | highlight: $select.search"></span>
								</ui-select-choices>
							</ui-select>
						</div>
					</div>
								<div class="form-group form-group-margin">
		                        <div class="input-group">
		                            <span class="input-group-addon">状态</span>
		                          <select ng-model="search.type" ng-change="getType(search.type)" ng-init="search.type='showAll'">
		                          	<option value="showAll">全部</option>
		                          	<option value="showBind">绑定</option>
		                          	<option value="showUnbind">未绑定</option>
		                          </select>
		                        </div>
		                    </div>
				  <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>
				  
				  <a ng-click="edit()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-plus"></i>新增
				  </a>

				</form>
				
				<!-- 表格 -->
                <table id="communityLocationList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="communityLocationPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>