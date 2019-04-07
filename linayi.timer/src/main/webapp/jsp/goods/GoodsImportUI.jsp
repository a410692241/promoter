<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style type="text/css">
.modal-dialog{
	width: 70%;
}
</style>
<form  class="form-horizontal ng-pristine ng-valid" role="form">
<div ng-style="{position: 'inherit'}" cg-busy="{promise:promise,message:'数据处理中......',backdrop:true,delay:0,minDuration:0}"></div>
<div class="row">
	<div class="col-lg-12" style="text-align: center;">
		<span style="color: red;font-size: 14px;vertical-align: super;">
			导入格式
		</span>
		<a ng-click="confirmImport()" class="btn btn-sm btn-primary form-group-margin" href="javascript:void(0);">
			<i class="fa fa-plus"></i>点击导入
		</a>
	</div>
</div>
<div class="row">
<div class="col-lg-12">
	<table class="table table-bordered table-hover">
        <thead>
            <tr>
            	<th>
                	商品编号
                </th>
                <th>
                   	商品标题
                </th>
                <th>
                	商品副标题
                </th> 
                 <th>
                	商品品牌
                </th>
                 <th>
                	产品名称
                </th>               
                 <th>
                	销售属性
                </th>               
                 <th>
                	规格
                </th>               
                 <th>
                	一级类别
                </th>               
                 <th>
                	二级类别
                </th>               
                 <th>
                	三级类别
                </th>               
            </tr>
        </thead>
        <tbody>
            <tr >
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            	<td></td>
            </tr>
        </tbody>
    </table>
</div>
</div>
<div class="row">
	<div class="col-lg-12" style="text-align: center;">
		<span style="color: red;font-size: 14px;vertical-align: super;">
			导入失败记录
		</span>
		<a ng-click="downloadErrorData()" class="btn btn-sm btn-primary form-group-margin" href="javascript:void(0);">
			<i class="fa fa-download"></i>点击下载
		</a>
	</div>
</div>
	<table class="table table-bordered table-hover">
        <thead>
            <tr>
          		<th>
                	商品编号
                </th>
                <th>
                   	商品标题
                </th>
                <th>
                	商品副标题
                </th>
                 <th>
                	商品品牌
                </th>
                 <th>
                	产品名称
                </th> 
                  <th>
                	销售属性
                </th>               
                 <th>
                	规格
                </th>               
                 <th>
                	一级类别
                </th>               
                 <th>
                	二级类别
                </th>               
                 <th>
                	三级类别
                </th>               
                
                <th>
                	<span style="color: red;">错误信息</span>
                </th>
            </tr>
        </thead>
        <tbody>
            <tr ng-repeat="goods in importResult.data track by $index" >
            	<td ng-repeat="prop in goods track by $index">
					{{prop}}
                </td>
            </tr>
        </tbody>
    </table>
</div>
</form>