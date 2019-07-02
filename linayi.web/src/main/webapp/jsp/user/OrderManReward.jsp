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
        <table id="orderManRewardList" style="width: 100%"></table>
        <%--<!-- 分页 -->
        <div id="correctPager"></div>--%>

        <!--消息框 -->
    </div>
</div>
<div class="input-group form-group form-group-margin" style="width: 100%;" ng-show="procurement.ordersId">
    <table border="3" style="width: 100%;border-color: #2dc3e8;">
        <tr>
            <th style="width: 5%; text-align: center;">序号</th>
            <th style="width: 60%; height: 40px; text-align: center;">奖励要求</th>
            <th style="width: 10%; text-align: center;">可结算金额（元）</th>
            <th style="width: 0.3%; text-align: center;">操作</th>
        </tr>

        <c:forEach items="${orderManRewards}" var="orderManRewards" varStatus="status">
                <tr>
                    <td style="text-align: center;">${status.index+1}</td>
                    <td style="text-align: center;">${orderManRewards.ruleDescription}</td>
                    <td style="text-align: center;">${orderManRewards.actualAmount / 100}</td>
                    <form method="post" action="/promoter/promoter/settlement.do">
                    <input type="hidden" id="orderManId" VALUE=${orderManRewards.orderManId}>
                    <td><input type="submit" value="结算"></td>
                    </form>
                </tr>

        </c:forEach>
    </table>
</div>
</body>

</html>