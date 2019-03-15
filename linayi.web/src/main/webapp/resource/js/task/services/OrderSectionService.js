'use strict';
app.service('orderSectionService', [function() {
	
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
		
	    if( data.startTime == ""){
			throw new BusinessException("startTime不能为空");
		}
	    if( data.endTime == ""){
			throw new BusinessException("endTime不能为空");
		}
	    if( data.deliveryTime == ""){
			throw new BusinessException("deliveryTime不能为空");
		}
	    if( data.mark == ""){
			throw new BusinessException("mark不能为空");
		}
	    if( data.startNo == ""){
			throw new BusinessException("startNo不能为空");
		}
		
		options.data = {
			orderSectionId:data.orderSectionId,
			startTime:new Date( data.startTime ).format('yyyy-MM-dd HH:mm:ss'),
			endTime:new Date( data.endTime ).format('yyyy-MM-dd HH:mm:ss'),
			deliveryTime:new Date( data.deliveryTime ).format('yyyy-MM-dd HH:mm:ss'),
			mark:data.mark,
			startNo:data.startNo,
		}
		options.url = urls.ms+"/task/orderSection/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/orderSection/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/orderSection/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);