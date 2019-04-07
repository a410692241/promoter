<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row" style="min-height:200px;max-height: 500px;">
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<div class="col-lg-12 col-sm-12 col-xs-12">
	     <a href='#' class='btn btn-primary fa fa-add btn-sm td-compile' ng-click="addRoleView()">新增角色</a>
         <table id="selectRoleList"></table>
    </div>
</form>
</div>

<script id="jsp/system/selectRoleManagee/addRole.html" type="text/ng-template">
<div class="row" style="min-height:100px;max-height: 500px;">
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<div class="col-lg-12 col-sm-12 col-xs-12">
	<div class="form-group col-lg-12 col-xs-12 col-sm-12">
	   <label for="inputEmail3" class="col-sm-3 control-label no-padding-right">角色名称</label>
	   <div class="col-sm-9">
	       <input ng-required="required" ng-readonly="selectUser.accountId > 0" type="text" ng-model="role.roleName" class="form-control">
	   </div>
	</div>
    </div>
</form>
</div>
</script>
<script type="text/javascript">
function listRole(  ){
	var $grid = $("#selectRoleList");
	$grid.jqGrid({
		url : urls.ms+"/roleAction/getRoleList.do",
		rownumbers:false,
		rowNum:-1,
		colModel : [
			{name:'roleId',label:'角色id',sortable:false,hidden:true}, 
			{name:'roleName',label:'角色名',sortable:false}, 
			{name:'status',label:'角色状态',sortable:false,formatter:function( cellValue,options,rowObject ){
				if( cellValue==1000 ){
            		return "有效"; 
            	}else if( value==cellValue ){
            		return "无效";
            	}
			}}, 
			{label:"操作",name:"opt",sortable:false,formatter:function(cellvalue, options, rowObject){
            	return "<a href='javascript:void(0)' class='btn btn-primary fa fa-edit btn-sm td-compile' ng-click='edit("+rowObject.roleId+")'>[修改权限]</a> <a href='javascript:void(0)' class='btn btn-primary fa fa-remove btn-sm td-compile' ng-click='deleteRole("+rowObject.roleId+")'>[删除]</a>";
			}}
		]
	});
}
listRole();
</script>