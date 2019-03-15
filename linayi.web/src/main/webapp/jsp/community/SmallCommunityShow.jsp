<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="communityLocation.smallCommunityId"/>
<div class="row">
	
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">省</label>
	   <div class="col-sm-10">
	       	<ui-select ng-disabled="areaCtrl.province"
	       			on-select="selectedArea( $item,1 )"  ng-model="areaCtrl.province" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.provinceList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		         </ui-select-choices>
		     </ui-select>
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">市</label>
	   <div class="col-sm-10">
	       <ui-select  ng-disabled="areaCtrl.city"
	       			on-select="selectedArea( $item,2 )" ng-model="areaCtrl.city" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.cityList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		         </ui-select-choices>
		    </ui-select>
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">区</label>
	   <div class="col-sm-10">
	      <ui-select ng-disabled="areaCtrl.region"
	      			on-select="selectedArea( $item,3 )" ng-model="areaCtrl.region" style="min-width: 200px" >
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.regionList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		         </ui-select-choices>
		    </ui-select>
	   </div>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">街道</label>
	   <div class="col-sm-10">
	       <ui-select
	       		    on-select = 'selectedArea( $item,5 )' ng-disabled="areaCtrl.street"
				    ng-model="areaCtrl.street" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.streetList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		           </ui-select-choices>
		     </ui-select>
	   </div>
	</div>
<!-- 	<div ng-show="!fromCommunity" class="form-group col-lg-12 col-xs-12 col-sm-12"> -->
<!-- 	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">社区</label> -->
<!-- 	   <div class="col-sm-10"> -->
<!-- 	   		<ui-select -->
<!-- 	   				on-select = 'selectedArea( $item,4)' ng-disabled="communityLocationCtrl.community" -->
<!-- 				    ng-model="communityLocationCtrl.community" style="min-width: 200px"> -->
<!-- 		           <ui-select-match >{{$select.selected.name}}</ui-select-match> -->
<!-- 		           <ui-select-choices repeat="item in communityLocationCtrl.communityList | filter:{name: $select.search}"> -->
<!-- 		               <span ng-bind-html="item.name | highlight: $select.search"></span> -->
<!-- 		           </ui-select-choices> -->
<!-- 		     </ui-select> -->
<!-- 	   </div> -->
<!-- 	</div>  -->
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">小区</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-disabled="communityLocationCtrl.communityLocation.name" 
	       			ng-model="communityLocationCtrl.communityLocation.name" class="form-control">
	   </div>
	</div>
	<div ng-show = "communityLocationCtrl.communityLocation.supermarketName" class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">超市</label>
	   <div class="col-sm-10">
	       <input ng-disabled="communityLocationCtrl.communityLocation.supermarketName" ng-required="required" type="text" 
	       			ng-model="communityLocationCtrl.communityLocation.supermarketName" class="form-control">
	   </div>
	</div>
</div>
</form>