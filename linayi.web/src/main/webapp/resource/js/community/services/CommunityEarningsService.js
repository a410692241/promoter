'use strict';
app.service('communityEarningsService', [function() {
	
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
		
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.amount == ""){
			throw new BusinessException("amount不能为空");
		}
	    if( data.type == ""){
			throw new BusinessException("type不能为空");
		}
	    if( data.earningsTime == ""){
			throw new BusinessException("earningsTime不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
		
		options.data = {
			communityEarningsId:data.communityEarningsId,
			communityId:data.communityId,
			amount:data.amount,
			type:data.type,
			earningsTime:new Date( data.earningsTime ).format('yyyy-MM-dd HH:mm:ss'),
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/community/communityEarnings/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/communityEarnings/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/communityEarnings/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);