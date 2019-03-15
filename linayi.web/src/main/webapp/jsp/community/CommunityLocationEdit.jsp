<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">小区编辑</span>
        </div>
        <div id="registration-form">
            <form role="form" id="supermarketInfoForm">
                <div class="form-title">
                </div>
                <input type="hidden" ng-model="communityLocation.smallCommunityId"/>

	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">省</label>
		<span class="input-icon icon-right">
	       	<ui-select
	       			on-select="selectedArea( $item,1 )"  ng-model="areaCtrl.province" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.provinceList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		         </ui-select-choices>
		     </ui-select>
			 <i class="glyphicon glyphicon-map-marker blue"></i>
		</span>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">市</label>
		<span class="input-icon icon-right">
	       <ui-select
	       			on-select="selectedArea( $item,2 )" ng-model="areaCtrl.city" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.cityList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		         </ui-select-choices>
		    </ui-select>
			 <i class="glyphicon glyphicon-map-marker blue"></i>
		</span>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">区</label>
        <span class="input-icon icon-right">
	      <ui-select
	      			on-select="selectedArea( $item,3 )" ng-model="areaCtrl.region" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.regionList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		         </ui-select-choices>
		    </ui-select>
             <i class="glyphicon glyphicon-map-marker blue"></i>
                            </span>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">街道</label>
        <span class="input-icon icon-right">
	       <ui-select
	       		    on-select = 'selectedArea( $item,5 )'
				    ng-model="areaCtrl.street" style="min-width: 200px">
		           <ui-select-match>{{$select.selected.name}}</ui-select-match>
		           <ui-select-choices repeat="item in areaCtrl.streetList | filter:{name: $select.search}">
		               <span ng-bind-html="item.name | highlight: $select.search"></span>
		           </ui-select-choices>
		     </ui-select>
	   <i class="glyphicon glyphicon-map-marker blue"></i>
                            </span>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">小区</label>
        <span class="input-icon icon-right">
	       <input ng-required="required" type="text"
	       			ng-model="communityLocationCtrl.communityLocation.name" class="form-control">
                <i class="glyphicon glyphicon-map-marker blue"></i>
                </span>
	</div>
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">配送员ID</label>
        <span class="input-icon icon-right">
	       <input ng-required="required" type="text"
	       			ng-model="communityLocationCtrl.communityLocation.delivererId" class="form-control">
                <i class="glyphicon glyphicon-map-marker blue"></i>
                </span>
	</div>
	<div ng-show = "communityLocationCtrl.communityLocation.supermarketName" class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label class="col-sm-1 control-label no-padding-right">超市</label>
        <span class="input-icon icon-right">
	       <input ng-disabled="communityLocationCtrl.communityLocation.supermarketName" ng-required="required" type="text"
	       			ng-model="communityLocationCtrl.communityLocation.supermarketName" class="form-control">
             <i class="glyphicon glyphicon-shopping-cart blue"></i>
        </span>
	</div>
</form>
</div>
</div>
</div>
