'use strict';
app.service('driverService', [function() {
	
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

	    if( !data.nickname ){
			throw new BusinessException("请输入昵称");
		}
	    if( !data.mobile ){
			throw new BusinessException("请输入手机号");
		}
	    if( !data.account ){
			throw new BusinessException("请输入账号");
		}
	    if( !data.carNo ){
	    	throw new BusinessException("请输入车辆编号");
	    }
	    if( !data.plate ){
	    	throw new BusinessException("请输入车牌号");
	    }
	    if( !data.password ){
			throw new BusinessException("请输入密码");
		}
	    if( !data.address ){
			throw new BusinessException("请输入地址");
		}
		
		options.data = {
			nickname:data.nickname,
			mobile:data.mobile,
			account:data.account,
			carNo:data.carNo,
			plate:data.plate,
			password:data.password,
			address:data.address,
			remark:data.remark
		}
		options.url = urls.ms+"/community/driver/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/driver/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/driver/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);