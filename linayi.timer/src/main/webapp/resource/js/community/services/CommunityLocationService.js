'use strict';
app.service('communityLocationService', [function() {
	
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
	    if( data.address == ""){
			throw new BusinessException("address不能为空");
		}
	    if( data.areaCode == ""){
			throw new BusinessException("areaCode不能为空");
		}
		options.data = {
			communityLocationId:data.communityLocationId,
			address:data.address,
			areaCode:data.areaCode,
			communityId:data.communityId,
			longitude:data.longitude,
			latitude:data.latitude,
		}
		options.url = urls.ms+"/community/communityLocation/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/communityLocation/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/smallCommunity/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);