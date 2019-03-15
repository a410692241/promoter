<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<input type="hidden" ng-model="selectUser.accountId">
<div class="row" style="min-height:200px;max-height: 500px;overflow-x: hidden;">
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<table id="selectRoleList"></table>
	<table id="selectRolePrivilegeList"></table>
</form>
</div>
<script type="text/javascript">
//列表查询
function listUserRoleList(){
	var selectRoleId = -1;
	var $grid = $("#selectRoleList");
	$grid.jqGrid({
		url : urls.ms+"/account/getRoleList.do",
		rownumbers:false,
		rowNum:-1,
		multiboxonly:true,
		colModel : [
			{name:'roleId',label:'主键',sortable:false},
			{name:'roleName',label:'角色名',sortable:false}, 
			{name:'status',label:'角色状态',sortable:false,formatter:function( cellValue,options,rowObject ){
				if( cellValue=="ENABLED" ){
            		return "有效"; 
            	}else if( value=="DISABLED" ){
            		return "<font color='red'>无效</font>";
            	}
			}}, 
		],
		loadComplete:function( rows ){
			checkPrivilege( rows,${param.accountId} );  
		},
		onSelectRow: function (rowId,status) {
			var row = $(this).jqGrid("getRowData",rowId);
			localStorage.setItem("roleId",row.roleId);
		},
		ondblClickRow:function(rowid, iRow, iCol, e){
			var row = $(this).jqGrid("getRowData",rowid);
			listRolePrivilegeList( row.roleId );
		}
	});
}

function listRolePrivilegeList( id ){
	var $grid = $("#selectRolePrivilegeList");
	$grid.jqGrid({
		url : urls.ms+"/roleAction/getRolePrivilege.do",
		rownumbers:false,
		rowNum:-1,
		caption:"角色权限列表",
		postData:{
			roleId:id
		},
		colModel : [
			{name:'privilegeCode',label:'权限编码',sortable:false}, 
			{name:'privilegeName',label:'权限名称',sortable:false}, 
		]
	});
}

/**
 * 获取用户已有的权限
 * @param data
 * @param userId
 */
function checkPrivilege(rows,id){
	//获取此用户的权限
	$.ajax({ 
		url: urls.ms+'/account/getRoleListByUser.do?accountId='+id,
        dataType : "json",
        type: "post", 
		success: function(obj){
			for(var i=0;i<obj.length;i++){
				
				for(var j=0;j< rows.length;j++){
					
					if(obj[i].roleId== rows[j].cell.roleId){
						$("#selectRoleList").jqGrid("setSelection", rows[j].id);
					}
				}
				
			}
		} 
	});
}

listUserRoleList();
</script>
