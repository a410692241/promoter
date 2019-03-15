'use strict';
app.service('evaluateService', [function() {
	
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
		
	    if( data.orderId == ""){
			throw new BusinessException("orderId不能为空");
		}
	    if( data.content == ""){
			throw new BusinessException("content不能为空");
		}
	    if( data.deliverScore == ""){
			throw new BusinessException("deliverScore不能为空");
		}
	    if( data.platformScore == ""){
			throw new BusinessException("platformScore不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
		
		options.data = {
			evaluateId:data.evaluateId,
			orderId:data.orderId,
			content:data.content,
			deliverScore:data.deliverScore,
			platformScore:data.platformScore,
			communityId:data.communityId,
		}
		options.url = urls.ms+"/order/evaluate/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/order/evaluate/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/order/evaluate/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);