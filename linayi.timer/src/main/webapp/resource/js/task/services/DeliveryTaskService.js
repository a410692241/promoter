'use strict';
app.service('deliveryTaskService', [function() {
	
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
		
	    if( data.taskNo == ""){
			throw new BusinessException("taskNo不能为空");
		}
	    if( data.title == ""){
			throw new BusinessException("title不能为空");
		}
	    if( data.content == ""){
			throw new BusinessException("content不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.orderId == ""){
			throw new BusinessException("orderId不能为空");
		}
	    if( data.startTime == ""){
			throw new BusinessException("startTime不能为空");
		}
	    if( data.endTime == ""){
			throw new BusinessException("endTime不能为空");
		}
	    if( data.dieTime == ""){
			throw new BusinessException("dieTime不能为空");
		}
	    if( data.receiveTime == ""){
			throw new BusinessException("receiveTime不能为空");
		}
	    if( data.deliveryTime == ""){
			throw new BusinessException("deliveryTime不能为空");
		}
	    if( data.finishTime == ""){
			throw new BusinessException("finishTime不能为空");
		}
	    if( data.serviceFee == ""){
			throw new BusinessException("serviceFee不能为空");
		}
	    if( data.realServiceFee == ""){
			throw new BusinessException("realServiceFee不能为空");
		}
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
		
		options.data = {
			taskId:data.taskId,
			taskNo:data.taskNo,
			title:data.title,
			content:data.content,
			communityId:data.communityId,
			orderId:data.orderId,
			startTime:new Date( data.startTime ).format('yyyy-MM-dd HH:mm:ss'),
			endTime:new Date( data.endTime ).format('yyyy-MM-dd HH:mm:ss'),
			dieTime:new Date( data.dieTime ).format('yyyy-MM-dd HH:mm:ss'),
			receiveTime:new Date( data.receiveTime ).format('yyyy-MM-dd HH:mm:ss'),
			deliveryTime:new Date( data.deliveryTime ).format('yyyy-MM-dd HH:mm:ss'),
			finishTime:new Date( data.finishTime ).format('yyyy-MM-dd HH:mm:ss'),
			serviceFee:data.serviceFee,
			realServiceFee:data.realServiceFee,
			userId:data.userId,
			status:data.status,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/task/deliveryTask/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/deliveryTask/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/deliveryTask/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);