<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div init-model='selfOrder'>
    <%=JSONObject.toJSONString(request.getAttribute("selfOrder"))%>
</div>
<div class="col-lg-12 col-sm-12 col-xs-12">
    <div class="widget flat radius-bordered">
        <div class="widget-header bg-danger">
            <span class="widget-caption">自定义订单编辑</span>
        </div>
        <div id="registration-form">
            <form role="form" id="supermarketInfoForm" >
                <div class="form-title">
                </div>
                <input type="hidden" ng-model="selfOrder.selfOrderId" />
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>商品名</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="selfOrder.goodsName"
                                       class="form-control">
                                <i class="glyphicon glyphicon-edit blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>品牌名</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="selfOrder.brandName"
                                       class="form-control">
                                <i class="glyphicon glyphicon-edit blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>商品属性值</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="selfOrder.attrValue"
                                       class="form-control">
                                <i class="glyphicon glyphicon-edit blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>最高价(元)</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="selfOrder.maxPrice"
                                       class="form-control">
                                <i class="glyphicon glyphicon-edit blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>最低价(元)</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="selfOrder.minPrice"
                                       class="form-control">
                                <i class="glyphicon glyphicon-edit blue"></i>
                            </span>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label>数量(件)</label>
                        <span class="input-icon icon-right">
                                <input type="text"
                                       ng-model="selfOrder.num"
                                       class="form-control">
                                <i class="glyphicon glyphicon-edit blue"></i>
                            </span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</form>

