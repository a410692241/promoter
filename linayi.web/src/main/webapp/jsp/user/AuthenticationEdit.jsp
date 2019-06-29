<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form role="form" id="goodsSkuInfoForm" role="form" method="post" enctype="multipart/form-data">
	<div class="row" style="max-height: 500px;overflow: auto;">
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">真实姓名</label>
			<div class="col-sm-10">
				<input ng-required="required" id="realName" type="text"
					   ng-model="apply1.realName" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">电话</label>
			<div class="col-sm-10">
				<input ng-required="required" id="mobile" type="text"
					   ng-model="apply1.mobile" class="form-control"/>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">身份证</label>
			<div class="col-sm-10">
				<%--<img style="height: 100px;width: 300px;" src="{{goods.image}}"/>--%>
				<div  name="file" style="width: 100%;height:50%;border: 1px solid #D5D5D5;" ui-imagebox ng-model="apply1.idCardFront"></div>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">银行卡</label>
			<div class="col-sm-10">
				<%--<img style="height: 100px;width: 300px;" src="{{goods.image}}"/>--%>
				<div  name="file" style="width: 100%;height:50%;border: 1px solid #D5D5D5;" ui-imagebox ng-model="apply1.idCardBack"></div>
			</div>
		</div>
		<div class="form-group col-lg-6 col-xs-6 col-sm-12">
			<label  class="col-sm-2 control-label no-padding-right" style="text-align: right;">编号</label>
			<div class="col-sm-10">
				<input ng-required="required" id="realName" type="text" readonly
					   ng-model="apply1.applyId" class="form-control"/>
			</div>
		</div>
	</div>
</form>