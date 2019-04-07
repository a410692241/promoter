<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='brand'>
<%=JSONObject.toJSONString( request.getAttribute( "brand" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">名称</label>
	   <div class="col-sm-10">
	   		{{brand.name}}
	   </div>
	</div>
	<%--<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">品牌LOGO</label>
	   <div class="col-sm-10">
	   		{{brand.logo}}
	   </div>
	</div>--%>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">介绍</label>
	   <div class="col-sm-10">
	   		{{brand.content}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">排序</label>
	   <div class="col-sm-10">
	   		{{brand.rank}}
	   </div>
	</div>

</div>
</form>