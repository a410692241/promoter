    <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <%@ taglib prefix="cl" uri="http://www.lay.com/option" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>
        <head>
        <meta charset="utf-8"/>
                <script type="text/javascript" src="resource/js/order/controllers/OrderDetail.js"></script>
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
        <div class="input-group form-group form-group-margin">
                <span class="input-group-addon">超市</span>
                <select ng-model="search.categoryId" ng-change="getType(search.categoryId)" ng-init="">
                        <option value="">全部</option>
                        <c:forEach items="${categorys}" var="category">
                                <option value="${category.categoryId}">${category.name}</option>
                        </c:forEach>
                </select>
        </div>
        </body>
        </html>