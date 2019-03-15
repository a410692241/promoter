'use strict';
app.service('userService', [function() {
	
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
		
	    if( data.nickname == ""){
			throw new BusinessException("nickname不能为空");
		}
	    if( data.sex == ""){
			throw new BusinessException("sex不能为空");
		}
	    if( data.account == ""){
			throw new BusinessException("account不能为空");
		}
	    if( data.password == ""){
			throw new BusinessException("password不能为空");
		}
	    if( data.address == ""){
			throw new BusinessException("address不能为空");
		}
	    if( data.areaCode == ""){
			throw new BusinessException("areaCode不能为空");
		}
	    if( data.headImage == ""){
			throw new BusinessException("headImage不能为空");
		}
	    if( data.locationId == ""){
			throw new BusinessException("locationId不能为空");
		}
	    if( data.authentication == ""){
			throw new BusinessException("authentication不能为空");
		}
	    if( data.mobileToken == ""){
			throw new BusinessException("mobileToken不能为空");
		}
//	    if( data.birthDay == ""){
//			throw new BusinessException("birthDay不能为空");
//		}
	    if( data.amount == ""){
			throw new BusinessException("amount不能为空");
		}
	    if( data.payPassword == ""){
			throw new BusinessException("payPassword不能为空");
		}
	    if( data.userType == ""){
			throw new BusinessException("userType不能为空");
		}
	    if( data.valid == ""){
			throw new BusinessException("valid不能为空");
		}
		
		options.data = {
			userId:data.userId,
			nickname:data.nickname,
			sex:data.sex,
			account:data.account,
			password:data.password,
			address:data.address,
			areaCode:data.areaCode,
			headImage:data.headImage,
			locationId:data.locationId,
			authentication:data.authentication,
			mobileToken:data.mobileToken,
//			birthDay:new Date( data.birthDay ).format('yyyy-MM-dd HH:mm:ss'),
			amount:data.amount,
			payPassword:data.payPassword,
			userType:data.userType.code,
			valid:data.valid,
		}
		options.url = urls.ms+"/user/user/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/user/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/user/deletes.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);