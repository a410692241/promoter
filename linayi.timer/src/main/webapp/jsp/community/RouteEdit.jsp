<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<style>
	.checked-community-content .item-more{
		background-color: #ed4e2a !important;
	}
	.checked-community-content .item-more i:BEFORE{
		content: "\f00d" !important;
		color: white;
	}
</style>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="route.routeId"/>
<div class="row" >
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">路线名称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="route.name" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">车辆</label>
	   <div class="col-sm-10">
	   		<ui-select
				   ng-model="carCtrl.car" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.plate}}</ui-select-match>
		           <ui-select-choices repeat="item in carCtrl.carList | filter:{plate: $select.search}">
		               <span ng-bind-html="item.plate | highlight: $select.search"></span>
		           </ui-select-choices>
		     </ui-select>
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<div class="orders-container">
               <div class="orders-header">
                   <h6>未选择社区</h6>
<!--                    <h6 style="padding-top: 5px"> -->
<!--                    <input placeholder="请输入社区名称" ng-required="required" type="text" ng-model="search.brandName" class="form-control"> -->
				</h6>
               </div>
               <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                   <li class="order-item" ng-repeat="community in unCheckedCommunityList track by $index ">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{community.name}}</div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 item-right">
                                <div class="item-price">
                                    <span class="currency"></span>
                                    <span class="price"></span>
                                </div>
                            </div>
                        </div>
                        <a ng-click="toggleCheckCommunity( community,1, $index );" class="item-more" href="" style="margin-right: 12px;">
                            <i></i>
                        </a>
                    </li>
               </ul>
           </div>
	</div>
	<div class="checked-community-content form-group col-lg-6 col-xs-6 col-sm-12">
		<div class="orders-container">
               <div class="orders-header" >
                   <h6>已选择社区</h6>
<!--                    <h6 style="padding-top: 5px"> -->
<!--                    <input placeholder="请输入社区名称" ng-required="required" type="text" ng-model="search.brandName" class="form-control"> -->
				</h6>
               </div>
               <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                   <li class="order-item" ng-repeat="community in  checkedCommunityList track by $index ">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{community.name}}</div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 item-right">
                                <div class="item-price">
                                    <span class="currency"></span>
                                    <span class="price"></span>
                                </div>
                            </div>
                        </div>
                        <a ng-click="toggleCheckCommunity( community, -1, $index );" class="item-more" href="javascript:void(0)" style="margin-right: 12px;">
                            <i></i>
                        </a>
                        <a ng-hide=" $index == 0" ng-click="switchItem( $index, -1 );" class="fa fa-arrow-up item-more" href="javascript:void(0)" style="margin-right: 42px;color: white !important;padding-top: 2px;text-decoration: none;">
                            <i></i>
                        </a>
                        <a ng-hide=" $index == checkedCommunityList.length-1  || checkedCommunityList.length == 1 "  ng-click="switchItem( $index, 1 );" class="fa fa-arrow-down item-more" href="javascript:void(0)" style="margin-right: 72px;    color: white !important;padding-top: 2px;text-decoration: none;">
                            <i></i>
                        </a>
                    </li>
               </ul>
           </div>
	</div>
</div>
</form>

