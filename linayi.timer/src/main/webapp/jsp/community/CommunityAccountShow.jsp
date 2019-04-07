<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='communityAccount'>
<%=JSONObject.toJSONString( request.getAttribute( "communityAccount" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">主键</label>
	   <div class="col-sm-10">
	   		{{communityAccount.communityAccountId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">负责人地址</label>
	   <div class="col-sm-10">
	   		{{communityAccount.address}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">地区编码</label>
	   <div class="col-sm-10">
	   		{{communityAccount.areaCode}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">创建时间</label>
	   <div class="col-sm-10">
	   		{{communityAccount.createTime | date:'yyyy-MM-dd HH:mm:ss'}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">负责人名称</label>
	   <div class="col-sm-10">
	   		{{communityAccount.nickname}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">负责人联系电话</label>
	   <div class="col-sm-10">
	   		{{communityAccount.mobile}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">登陆账号</label>
	   <div class="col-sm-10">
	   		{{communityAccount.account}}
	   </div>
	</div>
	<!-- <div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">登录密码</label>
	   <div class="col-sm-10">
	   		{{communityAccount.password}}
	   </div>
	</div> -->
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">父账号ID</label>
	   <div class="col-sm-10">
	   		{{communityAccount.parentId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">社区网点ID</label>
	   <div class="col-sm-10">
	   		{{communityAccount.communityId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">备注</label>
	   <div class="col-sm-10">
	   		{{communityAccount.remark}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">设备TOKEN</label>
	   <div class="col-sm-10">
	   		{{communityAccount.mobileToken}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label for="inputEmail3" class="col-sm-2 no-padding-right" style="text-align: right;">性别</label>
	   <div class="col-sm-10">
	   		{{communityAccount.sex}}
	   </div>
	</div>

</div>
</form>