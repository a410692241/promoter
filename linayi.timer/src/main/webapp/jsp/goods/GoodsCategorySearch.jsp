<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="correct.correctId"/>
<div class="row" >
	<div class="form-group col-lg-4 col-xs-4 col-sm-12">
	<div class="row">
		<div class="col-sm-12" style="padding-right: 0">
			<div class="orders-container" >
                <div class="orders-header" style="height:70px">
                    <h6>类别列表</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请输入类别搜索" ng-required="required" type="text" ng-model="search.categoryName" class="form-control">
					</h6>
                </div>
                <ul ng-init="index = -1" class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': search.category == category ? '#efefef' : '#fff'}" ng-click="listBrandList( category );" class="order-item" ng-repeat="category in  categoryList | filter:search.categoryName">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{category.name}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
		</div>
	</div>
	</div>
	<div class="form-group col-lg-4 col-xs-4 col-sm-12">
	<div class="row">	
		<div class="col-sm-12" style="padding-right: 0;">
			<div class="orders-container">
                <div class="orders-header" style="height:70px">
                    <h6>品牌列表</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请输入品牌搜索" ng-required="required" type="text" ng-model="search.brandName" class="form-control">
					</h6>
                </div>
                <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': search.brand == brand ? '#efefef' : '#fff'}" class="order-item" ng-click="listProduction( brand )" ng-repeat="brand in  brandList | filter:search.brandName">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{brand.name}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
		</div>
	</div>
	</div>
	<div class="form-group col-lg-4 col-xs-4 col-sm-12">
	<div class="row">
		<div class="col-sm-12" style="padding-right: 0;">
			<div class="orders-container">
                <div class="orders-header" style="height:70px">
                    <h6>产品列表</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请输入产品名称搜索" ng-required="required" type="text" ng-model="search.productionName" class="form-control">
					</h6>
                </div>
                <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': search.production == production ? '#efefef' : '#fff'}" class="order-item" ng-click="selectedProduction( production )" ng-repeat="production in  productionList | filter:search.productionName">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{production.name}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
		</div>
	</div>
	</div>
</div>
</form>

