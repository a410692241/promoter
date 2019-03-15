'use strict';
app.service('acceptService', [function() {
	
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
		
	    if( data.messageId == ""){
			throw new BusinessException("messageId不能为空");
		}
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.userType == ""){
			throw new BusinessException("userType不能为空");
		}
	    if( data.read == ""){
			throw new BusinessException("read不能为空");
		}
	    if( data.params == ""){
			throw new BusinessException("params不能为空");
		}
		
		options.data = {
			acceptId:data.acceptId,
			messageId:data.messageId,
			userId:data.userId,
			userType:data.userType,
			read:data.read,
			params:data.params,
		}
		options.url = urls.ms+"/system/accept/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/system/accept/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/system/accept/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);