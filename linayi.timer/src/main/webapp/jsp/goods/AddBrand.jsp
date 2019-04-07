<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='selectUser' >
    <%=JSONObject.toJSONString( request.getAttribute( "role" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
    <input type="hidden" ng-model="selectUser.brandId">
    <div class="row" style="max-height: 500px;overflow: auto;">
        <div class="form-group col-lg-6 col-xs-6 col-sm-12">
            <label  class="col-sm-2 control-label no-padding-right">品牌名称</label>
            <div class="form-group col-lg-6 col-xs-6 col-sm-12">
                <div class="col-sm-10">
                    <input ng-required="required" type="text" ng-model="selectUser.brandName" class="form-control">
                </div>
            </div>
        </div>
    </div>
</form>

