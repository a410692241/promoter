<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags-cx" prefix="cx"%>
<%@ taglib uri="http://www.chanxa.com/jsp/tag/core" prefix="auth"%>
<div init-model='procurementTask'>
	<%=JSONObject.toJSONString(request.getAttribute("procurementTask"))%>
</div>
<style>
.modal-dialog {
	width: 70%;
}
</style>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<div class="row">
		<div class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">采购任务类型</label>
			<div class="col-sm-10">
				<span class="radio"
					ng-repeat="item in replenishTypeCtrl.replenishTypeList"
					style="display: inline;"> <label
					ng-click="replenishTypeCtrl.replenishType = item.code"> <input
						name="form-field-radio" type="radio" class="colored-blue">
						<span class="text">{{item.name}}</span>
				</label>
				</span> &nbsp; &nbsp;
			</div>
		</div>
		<div class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">指定社区采购</label>
			<div class="col-sm-10">
				<ui-select
					ng-init='
						communityCtrl.communityList=<cx:out-sql sqlId="options.listCommunity"></cx:out-sql>;'
						on-select="changeCommunity( $item )"
					ng-model="community" style="min-width: 210px"> <ui-select-match>{{$select.selected.name}}</ui-select-match>
				<ui-select-choices
					repeat="item in communityCtrl.communityList | filter:{name: $select.search}">
				<span ng-bind-html="item.name | highlight: $select.search"></span> </ui-select-choices>
				</ui-select>
			</div>
		</div>
		<div ng-if="community.communityId == communityCtrl.communityId"
			class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label no-padding-right">本社区买手</label>
			<div class="col-sm-10">
				<ui-select on-select="userCtrl.userId = $item.userId"
					ng-init='temp={}' ng-model="temp" style="min-width: 200px">
				<ui-select-match>{{$select.selected.nickname}}</ui-select-match> <ui-select-choices
					repeat="item in userCtrl.userList | filter:{nickname: $select.search}">
				<span ng-bind-html="item.nickname | highlight: $select.search"></span>
				</ui-select-choices> </ui-select>
			</div>
		</div>
		<div class="form-group col-lg-12 col-xs-12 col-sm-12">
			<label class="col-sm-2 control-label ">采购商品</label>
			<div class="col-sm-4">
			 <input ng-model="search.goodsName" type="text" class="form-control">
			</div>
		
			<a ng-click="listOrderSkuByCommunityId()"
				class="btn btn-primary form-group-margin" href="javascript:void(0);">
				<i class="fa fa-search"></i>搜索
			</a> <a ng-click="confirmOrderSkuList()"
				class="btn btn-primary form-group-margin" href="javascript:void(0);">
				<i class="fa fa-check"></i>选择添加
			</a>
			<!-- 表格 -->
			<table id="orderSkuList" style="width: 100%"></table>
			<!-- 分页 -->
			<div id="orderSkuPager"></div>

			<table id="confirmOrderSkuList" style="width: 100%"></table>
		</div>
	</div>
</form>
