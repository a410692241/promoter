<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row" style="min-height:200px;max-height: 500px;overflow: auto;">
<form class="form-horizontal ng-pristine ng-valid" role="form">
<style type="text/css">
	.angular-ui-tree-handle{
		margin : 10px !important;
		border: 1px solid #dae2ea !important;
	    background: #f8faff !important;
	    color: #7c9eb2 !important;
	    padding: 10px !important;
	}	
</style>
	<div class="col-lg-12 col-sm-12 col-xs-12">
		<div checkbox="true" ui-tree-nestable ng-model="privilageList">
			{{node.text}}
		</div>
	</div>
</form>
</div>