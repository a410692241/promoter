<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/goods/services/GoodsService.js"></script>
<script type="text/javascript" src="resource/js/goods/controllers/GoodsRecommandController.js"></script>
</head>
<body>
<div class="row" ng-controller="goodsRecommandCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-body">
            	<form class="form-inline">
                <div class="input-group form-group form-group-margin">
                <span class="input-group-addon">商品名称</span>
                 <input ng-model="search.goodsName" type="text" class="form-control">
                </div>
				<div class="input-group form-group form-group-margin">
	                 <span class="input-group-addon">类别</span>
	                  <ui-select
					   		on-select="checkedCategory($item)" 
					   		ng-init='
						   		options=<cx:out-sql sqlId="options.listCategory" paramters="{level:3}"></cx:out-sql>;
						   		options.splice(0,0,{"name":"全部","code":""});
						   		temp=(options | filter:{code:search.categoryId})[0];'
						   	ng-model="temp" style="min-width: 180px">
				            <ui-select-match>{{$select.selected.name}}</ui-select-match>
				            <ui-select-choices repeat="item in options | filter:{name: $select.search}">
				                <span ng-bind-html="item.name | highlight: $select.search"></span>
				            </ui-select-choices>
				        </ui-select>
	            </div>
				<div class="input-group form-group form-group-margin">
                   <span class="input-group-addon">品牌</span>
                   <ui-select
                        id="brandId"
				   		on-select="search.brandId=$item.code"
					   	ng-model="selectedBarand" style="min-width: 180px">
			            <ui-select-match>{{$select.selected.name}}</ui-select-match>
			            <ui-select-choices repeat="item in brandList | filter: {name:$select.search}">
			                <span ng-bind-html="item.name | highlight: $select.search"></span>
			            </ui-select-choices>
			        </ui-select>
	            </div>
				<div class="input-group form-group form-group-margin">
                           <span class="input-group-addon">上架状态</span>
                           <ui-select
						   		on-select="search.status=$item.code"
						   		ng-init='
						   			options=<cx:out-options dictMapCd="goodsStatus" />;
						   			options.splice(0,0,{name:"全部",code:""});
						   			temp=(options | filter:{code:search.categoryId})[0];
						   		'
							   	ng-model="temp" style="min-width: 200px">
					            <ui-select-match>{{$select.selected.name}}</ui-select-match>
					            <ui-select-choices repeat="item in options | filter: {name:$select.search}">
					                <span ng-bind-html="item.name | highlight: $select.search"></span>
					            </ui-select-choices>
					        </ui-select>
                        </div>
				  <a ng-click="list()" class="btn btn-primary form-group-margin" href="javascript:void(0);">
				  	<i class="fa fa-search"></i>搜索
				  </a>
				</form>
				
				<!-- 表格 -->
                <table id="goodsRecommandList" style="width: 100%"></table>
				<!-- 分页 -->
				<div id="goodsRecommandPager"></div>
				
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-center', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>