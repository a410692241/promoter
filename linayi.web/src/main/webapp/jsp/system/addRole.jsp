<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div init-model='selectUser' >
	<%=JSONObject.toJSONString( request.getAttribute( "role" ) )%>
</div>
<%--<div ng-init='selectUser.password="";
selectUser.initRealName=selectUser.realName;
selectUser.initUserPhone=selectUser.userPhone;
'></div>--%>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<input type="hidden" ng-model="selectUser.roleMenuId">
<%--<div class="row" style="max-height: 500px;overflow: auto;">
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">角色名称</label>
	   <div class="col-sm-10">
	       <input ng-required="required" ng-readonly="selectUser.roleName > 0" type="text" ng-model="selectUser.userName" class="form-control">
	   </div>
	</div>--%>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
		<label    class="col-sm-2 control-label no-padding-right">角色名</label>
		<div class="col-sm-10">
			<input ng-required="required" type="text" ng-model="selectUser.roleName" class="form-control">
		</div>
	</div>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">密码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="selectUser.password" class="form-control">
	   </div>
	</div>--%>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">确认密码</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="selectUser.password1" class="form-control">
	   </div>
	</div>--%>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 control-label no-padding-right">真实姓名</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="selectUser.realName" class="form-control">
	   </div>
	</div>--%>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label    class="col-sm-2 control-label no-padding-right">菜单名</label>
	   <div class="col-sm-10">
	       <input ng-required="required" type="text" ng-model="selectUser.menuName" class="form-control">
	   </div>
	</div>--%>

</div>
</form>

