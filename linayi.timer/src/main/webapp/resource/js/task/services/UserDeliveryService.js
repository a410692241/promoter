'use strict';
app.service('userDeliveryService', [function() {
	
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
		
	    if( data.taskId == ""){
			throw new BusinessException("taskId不能为空");
		}
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
		
		options.data = {
			userDeliveryId:data.userDeliveryId,
			taskId:data.taskId,
			userId:data.userId,
			communityId:data.communityId,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/task/userDelivery/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/userDelivery/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/userDelivery/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);