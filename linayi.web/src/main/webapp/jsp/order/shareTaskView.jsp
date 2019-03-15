<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='selfOrderMessages'>
    <%=JSONObject.toJSONString(request.getAttribute("selfOrderMessages"))%>
</div>
<table class="table table-striped">
    <tr>
        <th>编号</th>
        <th>分享员</th>
        <th>超市</th>
        <th>采价状态</th>
        <th>是否查看</th>
    </tr>
    <tr ng-repeat="x in selfOrderMessages">
        <td>{{ x.message_id }}</td>
        <td>{{ x.nickname }}</td>
        <td>{{x.supermarket}}</td>
        <td>
            <div ng-switch="x.status">
                <div ng-switch-when="WAIT_DEAL">
                    等待处理
                </div>
                <div ng-switch-when="SUCCESS">
                    处理成功
                </div>
                <div ng-switch-when="FAIL">
                    处理失败
                </div>
            </div>
        </td>
        <td>
            <div ng-switch="x.view_status">
                <div ng-switch-when="VIEWED">
                    已读
                </div>
                <div ng-switch-default>
                    未读
                </div>
            </div>
        </td>
    </tr>
</table>