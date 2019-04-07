<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="correct.correctId"/>
<div class="row" >
	<div class="form-group col-lg-3 col-xs-3 col-sm-12">
	<div class="row">
		<div class="col-sm-12" style="padding-right: 0">
			<div class="orders-container" >
                <div class="orders-header" style="height:70px">
                    <h6>省</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请选择省" ng-required="required" type="text" ng-model="province.name" class="form-control">
					</h6>
                </div>
                <ul ng-init="index = -1" class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': checkProvince.code == province.code ? '#efefef' : '#fff'}" ng-click="getCity( province );" class="order-item" ng-repeat="province in provinceList | filter:province.name">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{province.name}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
		</div>
	</div>
	</div>
	<div class="form-group col-lg-3 col-xs-3 col-sm-12">
	<div class="row">	
		<div class="col-sm-12" style="padding-right: 0;">
			<div class="orders-container">
                <div class="orders-header" style="height:70px">
                    <h6>市</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请选择市" ng-required="required" type="text" ng-model="city.name" class="form-control">
					</h6>
                </div>
                <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': checkCity.code == city.code ? '#efefef' : '#fff'}" class="order-item" ng-click="getRegion( city )" ng-repeat="city in cityList | filter:city.name">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{city.name}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
		</div>
	</div>
	</div>
	<div class="form-group col-lg-3 col-xs-3 col-sm-12">
	<div class="row">
		<div class="col-sm-12" style="padding-right: 0;">
			<div class="orders-container">
                <div class="orders-header" style="height:70px">
                    <h6>区</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请选择区" ng-required="required" type="text" ng-model="region.name" class="form-control">
					</h6>
                </div>
                <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': checkRegion.code == region.code ? '#efefef' : '#fff'}" class="order-item" ng-click="getStreet( region )" ng-repeat="region in regionList | filter:region.name">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{region.name}}</div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
		</div>
	</div>
	</div>
	<div class="form-group col-lg-3 col-xs-3 col-sm-12">
	<div class="row">
		<div class="col-sm-12" style="padding-right: 0;">
			<div class="orders-container">
                <div class="orders-header" style="height:70px">
                    <h6>街道</h6>
                    <h6 style="padding-top: 5px">
                    <input placeholder="请选择街道" ng-required="required" type="text" ng-model="street.name" class="form-control">
					</h6>
                </div>
                <ul class="orders-list" style="max-height: 500px;overflow-y: auto;overflow-x: hidden;">
                    <li ng-style="{'background-color': checkStreet.code == street.code ? '#efefef' : '#fff'}" class="order-item" ng-click="getResult( street )" ng-repeat="street in streetList | filter:street.name">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 item-left">
                                <div class="item-booker">{{street.name}}</div>
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

