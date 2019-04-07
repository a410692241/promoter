<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div init-model='brandProduction'>
<%=JSONObject.toJSONString( request.getAttribute( "brandProduction" ) )%>
</div>
<form class="form-horizontal ng-pristine ng-valid" role="form">
<div class="row" style="max-height: 500px;overflow: auto;">

	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">主键</label>
	   <div class="col-sm-10">
	   		{{brandProduction.brandProductionId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">产品ID</label>
	   <div class="col-sm-10">
	   		{{brandProduction.productionId}}
	   </div>
	</div>
	<div class="form-group col-lg-6 col-xs-6 col-sm-12">
	   <label  class="col-sm-2 no-padding-right" style="text-align: right;">品牌ID</label>
	   <div class="col-sm-10">
	   		{{brandProduction.brandId}}
	   </div>
	</div>

</div>
</form>