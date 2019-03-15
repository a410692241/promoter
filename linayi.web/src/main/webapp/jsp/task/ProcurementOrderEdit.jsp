<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='procurementOrder'>
<%=JSONObject.toJSONString( request.getAttribute( "procurementOrder" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<input  type="hidden" ng-model="procurementOrder.procurementOrderId"/>
<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">主键</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.procurementOrderId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">任务ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.taskId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">订单ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.orderId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">社区ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.orderCommunityId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">商品ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.goodsSkuId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.quantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">实际分配数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.allotQuantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">分拣数量</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.sortingQuantity" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">分拣操作</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.sortingOption" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">分拣状态</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.sortingStatus" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">流转箱ID</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.boxId" class="form-control">
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 control-label no-padding-right">位置编号</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="procurementOrder.receiveSite" class="form-control">
	   </div>
	</div>

</div>
</form>

