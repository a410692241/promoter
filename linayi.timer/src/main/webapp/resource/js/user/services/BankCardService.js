'use strict';
app.service('bankCardService', [function() {
	
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
	    if( data.nickname == ""){
			throw new BusinessException("nickname不能为空");
		}
	    if( data.account == ""){
			throw new BusinessException("account不能为空");
		}
	    if( data.address == ""){
			throw new BusinessException("address不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.bankId == ""){
			throw new BusinessException("bankId不能为空");
		}
	    if( data.userType == ""){
			throw new BusinessException("userType不能为空");
		}
		
		options.data = {
			bankCardId:data.bankCardId,
			userId:data.userId,
			nickname:data.nickname,
			account:data.account,
			address:data.address,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			bankId:data.bankId,
			userType:data.userType,
		}
		options.url = urls.ms+"/user/bankCard/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/bankCard/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/bankCard/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);