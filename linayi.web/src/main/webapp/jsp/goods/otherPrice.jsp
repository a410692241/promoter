<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>

</head>
<body>
<input type="hidden" id="procurementTaskId" value="${procurementTaskId}">
<div class="row" ng-controller="orderDetailCtrl">
    <div class="col-xs-12 col-md-12">


        <!-- 表格 -->
        <table id="orderSupermarketList" style="width: 100%"></table>
        <%--<!-- 分页 -->
        <div id="correctPager"></div>--%>

        <!--消息框 -->
    </div>
</div>
<%--超市选择--%>
<div class="input-group form-group form-group-margin" style="width: 100%;" ng-show="procurement.ordersId">
    <table border="3" style="width: 100%;border-color: #2dc3e8;">
        <tr>
            <th style="width: 10%; text-align: center;">序号</th>
            <th style="width: 40%; height: 40px; text-align: center;">超市</th>
            <th style="width: 25%; text-align: center;">价格</th>
            <th style="width: 25%; text-align: center;">状态</th>
        </tr>
        <c:forEach items="${otherPrice}" var="otherPrice" varStatus="status">
        <tr>
            <td style="text-align: center;">${status.index+1}</td>
            <td style="height: 40px; padding: 0 20px;">${otherPrice.name}</td>
            <td style="text-align: center;">${otherPrice.price / 100}</td>
            <td style="text-align: center;">${otherPrice.status}</td>
        </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>