'use strict';
app.service('userOrderService', [function() {
	
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
		
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.orderId == ""){
			throw new BusinessException("orderId不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
		
		options.data = {
			userOrderId:data.userOrderId,
			userId:data.userId,
			orderId:data.orderId,
			communityId:data.communityId,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/user/userOrder/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/userOrder/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/userOrder/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);