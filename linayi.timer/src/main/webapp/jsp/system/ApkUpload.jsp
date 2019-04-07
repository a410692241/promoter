<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="resource/js/system/controllers/apkUploadController.js"></script>
</head>
<body>
<div class="row" ng-controller="apkUploadCtrl">
    <div class="col-xs-12 col-md-12">
        <div class="widget">
            <div class="widget-header ">
                <span class="widget-caption"></span>
                <div class="widget-buttons">
                    <a href="" widget-collapse></a>
                    <a href="" widget-maximize></a>
                </div>
            </div>
            <div class="widget-body" style="height: 400px">
				<section id="dropbox" style=";color: #4A4747;font-size: 14px;height: 300px;transform:translate(-50%,30px);margin-left:50%;text-align: center;padding-top: 10px;width:70%;border: 1px dashed silver;cursor: pointer; ">
				    <form action="#" id="apkForm" method="post" enctype="multipart/form-data">
				    	<figure style="transform:translateY(50%)">
				    		<div id="file_view">
					    		<img alt="" onclick="$('input[type=file]').click()" src="${pageContext.request.contextPath}/resource/image/apkUpload.png">
					    		<input id="file" type="file" name="file" style="display:none" onchange="angular.element(this).scope().fileSelected()">
					    		<figcaption style="margin-top: 10px"> 点击按钮选择应用的安装包，或拖拽文件到此区域</figcaption>
					    	</div>
					    	<div id="progress_view" style="display: none;">
					    		<div style="margin: 10px">应用正在上传中，请不要关闭浏览器</div>
					    		<div id="progress" style="box-sizing:border-box;width:100%;height: 22px;border:1px solid #1ABC9C ;border-radius:10px;">
					    			<div id="progressBack" style="color:white;background-color: #1ABC9C;height: 100%;border-radius:8px;width: 0px"></div>
					    		</div>
					    		<div id="progressNum" style="margin: 10px">0%</div>
					    		<div style="padding-top: 10px;margin: 10px;margin: 10px">
					    			<span style="background-color: #505055;border: 1px solid 95A5A6;padding: 5px;color: white;" ng-click="cancelUpload()">取消上传</span>
					    		</div>
				    		</div>
				    	</figure>
					</form>
				</section>
            </div>
        </div>
    </div>
</div>
<!--消息框 -->
<toaster-container toaster-options="{'position-class': 'toast-top-right', 'close-button':true,'limit':1}"></toaster-container>
</body>
</html>