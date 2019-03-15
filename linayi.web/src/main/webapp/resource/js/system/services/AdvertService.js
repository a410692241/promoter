'use strict';
app.service('advertService', [function() {
	
	function ajax( options ){
		$.ajax({
			url:options.url,
			data:options.data,
			dataType: options.dataType ? options.dataType : "json",
			type: options.type ? options.type : "post",
			success: options.success,
	    	contentType : options.contentType ? options.contentType : "application/x-www-form-urlencoded",
			error: options.error ? options.error : function(){}
		}).then(function( data ){
			if( options.then ){
				options.then.call( this,data );
			}
		});
	}
	
	function save( options ){
		var data = options.data;
		
	    if( data.title == ""){
			throw new BusinessException("title不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.validStart == ""){
			throw new BusinessException("validStart不能为空");
		}
	    if( data.validEnd == ""){
			throw new BusinessException("validEnd不能为空");
		}
	    if( data.publishTime == ""){
			throw new BusinessException("publishTime不能为空");
		}
	    if( data.lastModify == ""){
			throw new BusinessException("lastModify不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.template == ""){
			throw new BusinessException("template不能为空");
		}
	    if( data.adminId == ""){
			throw new BusinessException("adminId不能为空");
		}
	    if( data.position == ""){
			throw new BusinessException("position不能为空");
		}
		
		options.data = {
			advertId:data.advertId,
			title:data.title,
			status:data.status,
			validStart:new Date( data.validStart ).format('yyyy-MM-dd HH:mm:ss'),
			validEnd:new Date( data.validEnd ).format('yyyy-MM-dd HH:mm:ss'),
			publishTime:new Date( data.publishTime ).format('yyyy-MM-dd HH:mm:ss'),
			lastModify:new Date( data.lastModify ).format('yyyy-MM-dd HH:mm:ss'),
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			template:data.template,
			adminId:data.adminId,
			position:data.position,
		}
		options.url = urls.ms+"/system/advert/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/system/advert/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/system/advert/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);