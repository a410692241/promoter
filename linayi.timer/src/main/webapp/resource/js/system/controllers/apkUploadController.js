'use strict';

app.controller('apkUploadCtrl', function($scope,toaster,messager,templateform,$interval,$timeout,$state ) {
	var fileInfo={
		fileSize:null,
		fileName:null
	}
	function init(){
		$scope.cancelUpload = cancelUpload;
		$scope.fileSelected = fileSelected;
	}
	
	document.getElementById("apkForm").reset();
	
 	var dropbox = document.getElementById("dropbox");
 	dropbox.addEventListener("dragenter", function(e){  
 		$(dropbox).css("backgroundColor","#1ABC9C");
	}, false);  
	dropbox.addEventListener("dragleave", function(e){  
		dropbox.style.backgroundColor = 'white';  
	}, false);  
	dropbox.addEventListener("dragover", function(e){  
	    e.stopPropagation();  
	    e.preventDefault();
	    $(dropbox).css("backgroundColor","#1ABC9C");
	}, false);  
	dropbox.addEventListener("drop", function(e){  
	    e.stopPropagation();  
	    e.preventDefault();  
	    dropbox.style.backgroundColor = 'white';  
	    handleFiles(e.dataTransfer.files);  
	       
	}, false);
	window.addEventListener("dragover", function(e){  
	    e.stopPropagation();  
	    e.preventDefault();
	}, false);
	window.addEventListener("drop", function(e){  
	    e.stopPropagation();  
	    e.preventDefault();  
	       
	}, false);  
	window.parent.addEventListener("dragover", function(e){  
	    e.stopPropagation();  
	    e.preventDefault();
	}, false);  
	window.parent.addEventListener("drop", function(e){  
	    e.stopPropagation();  
	    e.preventDefault();  
	       
	}, false);
	
 
	function handleFiles(files) {  
	    for (var i = 0; i < files.length; i++) {  
	    	fileSelected(files[i]);
	    }  
	}  
	
  /**取消上传*/
  function cancelUpload(){
	  xhr.abort();
  }
 
  var fileName = null;
  var fileSize = null;
  /**选择文件*/
  function fileSelected( file ) {
	   if( !file ){
	   	   file = document.getElementById('file').files[0];
	   }
	   var fileSize = 0;
	   var fileName = "";
	   if (file) {
		 fileName = file.name;
	     if (file.size > 1024 * 1024){
	       fileSize = (Math.round(file.size * 100 /(1024 * 1024))/ 100).toString() + 'MB';
	     }else{
	       fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	     }
	   }
	   if( fileName == "" || !fileName.match( /.{1,}\.apk/)){
		$scope.$apply(function(){
			toaster.error("","上传文件不是APK文件！");
		});
		return;
	   }
	   fileInfo={
			   fileSize : fileSize,
			   fileName : fileName
	   }
	   document.getElementById("file_view").style.display="none";
	   document.getElementById("progress_view").style.display="block";
	   uploadFile( file );
	 }
  	/**上传文件*/
     var xhr
     function uploadFile( file ) {
       var fd = new FormData();
       fd.append("file", file );
       xhr = new XMLHttpRequest();
       xhr.upload.addEventListener("progress", uploadProgress, false);
       xhr.addEventListener("load", uploadComplete, false);
       xhr.addEventListener("error", uploadFailed, false);
       xhr.addEventListener("abort", uploadCanceled, false);
       xhr.open("POST",urls.ms + "/system/apk/uploadApk");
       xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
       xhr.send(fd);
     }
     /**进度条事件*/
	 function uploadProgress(evt) {
	   if (evt.lengthComputable) {
	     var percentComplete = Math.round(evt.loaded * 100 / evt.total);
	     document.getElementById('progressNum').innerHTML = fileInfo.fileName +"&nbsp;&nbsp;" +percentComplete.toString() + '%&nbsp;/&nbsp;'+fileInfo.fileSize;
	     document.getElementById('progressBack').style.width = percentComplete.toString() + '%';
	     if( percentComplete == 100 ){
	   	  document.getElementById('progressBack').innerText="解析中...";
	   	  $interval(changeColor, 200);
	     }
	   }else {
	     document.getElementById('progressNum').innerHTML = '请选择文件上传！';
	   }
	 }
	 /***上传完毕回调*/
     function uploadComplete(evt) {
       /* 服务器端返回响应时候触发event事件*/
       var result = evt.target.responseText;
       var response = $.parseJSON( result );
       if( response.success ){
    	   ApkInfo( response.data );
    	   initView();
  		}else{
  			$scope.$apply(function(){
  				toaster.error("",response.msg);
  			})
  			$timeout( initView,2000 );
  		}
     }
     /***上传完毕等待后台业务处理*/
     function changeColor(){
  		var progressBack = document.getElementById('progressBack');
  		if( progressBack.style.color == "red" ){
  			progressBack.style.color = "black";
  		}else if( progressBack.style.color == "black" ){
  			progressBack.style.color = "white";
  		}else if( progressBack.style.color == "white" ){
  			progressBack.style.color = "red";
  		}
     }
     function uploadFailed(evt) {
         //上传失败
    	 initView();
     }
     function uploadCanceled(evt) {
       //取消上传
   	  initView();
     }
     function initView(){
    	 $state.reload();
     }
     
     function ApkInfo( data ){
    	 var url = urls.ms + "/jsp/system/ApkInfo.jsp";
 		templateform.open({
 			title:"APK信息",
 			url:url,
 			data:data,
 			dataName:"apkInfo"
 		},function( modalInstance,data,scope ){
 			try{
 				if( !data.share ){
 					data.share = {};
 				}
			    if( !data.android.versionName  ){
			    	throw new BusinessException("请输入用户版本号！");
				}
			    if( !data.android.versionCode  ){
			    	throw new BusinessException("请输入升级版本号！");
			    }
			    if( !data.android.download_url){
			    	throw new BusinessException("请输入download_url！");
				}
			    if( !data.share.share_title ){
			    	throw new BusinessException("请输入分享标题！");
				}
			    if( !data.share.logo_url){
			    	throw new BusinessException("请输入logo_url！");
				}
			    if( !data.share.share_link ){
			    	throw new BusinessException("请输入share_link！");
				}
			    $.ajax({
			    	url:urls.ms+"/system/apk/updateApkInfo.do",
			    	data:{
			    		download_url:data.android.download_url,
			    		version:data.android.version,
			    		version:data.android.version,
			    		logo_url:data.share.logo_url,
			    		share_link:data.share.share_link,
			    		share_title:data.share.share_title
			    	},
			    	dataType:"json",
			    	type:"post",
			    	success:function( data ){
			    		if( data.success ){
 							modalInstance.close();
 						}else{
 							$scope.$apply(function(){
 								toaster.error( "",data.msg,3000 );
 							});
 						}
			    	}
			    });
 			}catch (e) {
 				console.error( e );
 	  			toaster.error( "",e.msg ? e.msg : "出错了",3000 );
 			}
 		});
     }
     
     //初始化
     init();
});