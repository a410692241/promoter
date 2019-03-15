<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row" style="min-height:200px;max-height: 500px;">
<form class="form-horizontal ng-pristine ng-valid" role="form">
	<div class="col-lg-12 col-sm-12 col-xs-12">
         <tabset>
             <tab heading="菜单">
                    <div class="bg-whitesmoke">
                        <abn-tree tree-data="userRolePrivilege"
                                  expand-level="10"
                                  icon-leaf="fa fa-file-o"
                                  icon-expand="fa fa-plus"
                                  icon-collapse="fa fa-minus">
                        </abn-tree>
                    </div>
             </tab>

             <%--<tab heading="员工单独权限">
                    <div class="bg-whitesmoke">
                        <abn-tree tree-data="userPrivilege"
                                  expand-level="10"
                                  icon-leaf="fa fa-file-o"
                                  icon-expand="fa fa-plus"
                                  icon-collapse="fa fa-minus">
                        </abn-tree>
                    </div>
             </tab>--%>
         </tabset>
         <div class="horizontal-space"></div>
    </div>
</form>
</div>

<style type="text/css">
.modal-body{
	padding: 0px;
}
.tab-content{
 	box-shadow: none;
 	background-color: white;
 	padding-right: 0px;
}
</style>