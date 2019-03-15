<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<form class="form-horizontal ng-pristine ng-valid" role="form">
    <input type="hidden" ng-model="production.productionId"/>
    <div class="row" style="max-height: 500px;">
        <div class="form-group col-lg-12 col-xs-12 col-sm-12">
            <label style="max-width: 70px"
                   class="col-sm-2 control-label no-padding-right">产品名称</label>
            <div class="col-sm-10">
                <input max-length="20" ng-required="required" type="text"
                       ng-model="production.title" class="form-control">
            </div>
        </div>
        <div class="form-group col-lg-12 col-xs-12 col-sm-12">
            <label style="max-width: 70px"
                   class="col-sm-2 control-label no-padding-right">类别</label>
            <div class="col-sm-10">
                <ui-select ng-model="categoryCtrl.category" style="min-width: 200px">
                    <ui-select-match>{{$select.selected.name}}</ui-select-match>
                    <ui-select-choices
                            repeat="item in categoryCtrl.categoryList | filter:{name: $select.search}">
                        <span ng-bind-html="item.name | highlight: $select.search"></span></ui-select-choices>
                </ui-select>
            </div>
        </div>
        <div class="form-group col-lg-12 col-xs-12 col-sm-12">
            <label style="max-width: 70px"
                   class="col-sm-2 control-label no-padding-right">品牌</label>
            <div class="col-sm-10">
                <input max-length="20" ng-click="selectBrand()"
                       ng-required="required" type="text" ng-model="brandCtrl.name"
                       class="form-control" readonly="readonly" placeholder="请点击选择类别下的品牌">
                <div style="display: none;">{{brandCtrl.id}}</div>
                <input max-length="20" ng-required="required" type="hidden"
                       ng-model="brandCtrl.id" class="form-control">
                <!-- 	      	<ui-select -->
                <!-- 	      		id="editBrandId" -->
                <!-- 			   	ng-model="brandCtrl.brand" style="min-width: 200px"> -->
                <!-- 	            <ui-select-match>{{$select.selected.name}}</ui-select-match> -->
                <!-- 	            <ui-select-choices repeat="item in brandCtrl.brandList | filter: {name:$select.search}"> -->
                <!-- 	                <span ng-bind-html="item.name | highlight: $select.search"></span> -->
                <!-- 	            </ui-select-choices> -->
                <!-- 	        </ui-select> -->
            </div>
        </div>
        <!-- 	<div class="form-group col-lg-12 col-xs-12 col-sm-12"> -->
        <!-- 	   	<label style="max-width: 70px" class="col-sm-2 control-label no-padding-right"> -->
        <!-- 	   		规格属性 -->
        <!-- 	   	</label> -->
        <!-- 	   	 <div class="col-sm-10"> -->
        <!-- 		   	<ui-select -->
        <!-- 		   		placholder="xxxx" -->
        <!-- 		   		close-on-select="false" -->
        <!-- 		   		multiple -->
        <!-- 		   		theme="select2"  -->
        <%-- 		   		ng-init=' --%>
        <!-- 			   		attrCtrl.dataList=<cx:out-sql sqlId="options.listAttribute" paramters="{removed:2}"></cx:out-sql>; -->
        <!-- 			   		initAttrCtrl(); -->
        <%-- 			   		' --%>
        <!-- 			   	ng-model="attrCtrl.attrList"> -->
        <!-- 	            <ui-select-match>{{$item.name}}</ui-select-match> -->
        <!-- 	            <ui-select-choices repeat="item in attrCtrl.dataList | filter:{name: $select.search}"> -->
        <!-- 	                <span ng-bind-html="item.name | highlight: $select.search"></span> -->
        <!-- 	            </ui-select-choices> -->
        <!-- 	        </ui-select> -->
        <!-- 	      </div> -->
        <!-- 	</div> -->
        <div class="form-group col-lg-12 col-xs-12 col-sm-12">
            <label style="max-width: 70px"
                   class="col-sm-2 control-label no-padding-right"> 销售属性 </label>
            <div class="col-sm-10">
                <ui-select close-on-select="false" multiple theme="select2"
                           ng-init='salesAttrCtrl.dataList=<cx:out-sql sqlId="options.listAttribute" paramters="{removed:2}"></cx:out-sql>;
			   		initSalesAttrCtrl();
			   		'
                           ng-model="salesAttrCtrl.attrList">
                    <ui-select-match>{{$item.name}}</ui-select-match>
                    <ui-select-choices
                            repeat="item in salesAttrCtrl.dataList | filter:{name: $select.search}">
                        <span ng-bind-html="item.name | highlight: $select.search"></span></ui-select-choices>
                </ui-select>
            </div>
        </div>
    </div>
</form>
