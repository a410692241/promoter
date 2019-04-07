'use strict';
app.service('userLocationService', [function() {
	
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
		
	    if( data.address == ""){
			throw new BusinessException("address不能为空");
		}
	    if( data.areaCode == ""){
			throw new BusinessException("areaCode不能为空");
		}
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.nickname == ""){
			throw new BusinessException("nickname不能为空");
		}
	    if( data.mobile == ""){
			throw new BusinessException("mobile不能为空");
		}
		
		options.data = {
			userLocationId:data.userLocationId,
			address:data.address,
			areaCode:data.areaCode,
			userId:data.userId,
			nickname:data.nickname,
			mobile:data.mobile,
		}
		options.url = urls.ms+"/user/userLocation/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/userLocation/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/userLocation/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);