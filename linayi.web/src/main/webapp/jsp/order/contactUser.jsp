<%@page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div init-model='userInfo'>
    <%=JSONObject.toJSONString(request.getAttribute("userInfo"))%>
</div>
<table class="table table-striped">
    <tr>
        <th>昵称</th>
        <th>性别</th>
        <th>联系电话</th>
        <th>qq</th>
    </tr>
    <tr>
        <td>{{userInfo.nickname}}</td>
        <td>
            <%--<div ng-switch="userInfo.sex">--%>
                <%--<div ng-switch-when="MALE">--%>
                    <%--男--%>
                <%--</div>--%>
                <%--<div ng-switch-when="FEMALE">--%>
                    <%--女--%>
                <%--</div>--%>
                <%--<div ng-switch-default>--%>
                    <%--未知--%>
                <%--</div>--%>
            <%--</div>--%>
            {{userInfo.sex}}
        </td>
        <td>
            <%--<div ng-switch="userInfo.mobile">--%>
                <%--<div ng-switch-when="null">--%>
                    <%--未知--%>
                <%--</div>--%>
                <%--<div ng-switch-default>--%>
                    <%--{{userInfo.mobile}}--%>
                <%--</div>--%>
            <%--</div>--%>
            {{userInfo.mobile}}
        </td>
        <td>
            <%--<div ng-switch="userInfo.mobile">--%>
                <%--<div ng-switch-when="null">--%>
                    <%--未知--%>
                <%--</div>--%>
                <%--<div ng-switch-default>--%>
                    <%--{{userInfo.qq}}--%>
                <%--</div>--%>
            <%--</div>--%>
            {{userInfo.qq}}
        </td>
    </tr>
</table>