'use strict';
app.service('rechargeService', [function() {
	
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
	    if( data.amount == ""){
			throw new BusinessException("amount不能为空");
		}
	    if( data.preAmount == ""){
			throw new BusinessException("preAmount不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.adminId == ""){
			throw new BusinessException("adminId不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.type == ""){
			throw new BusinessException("type不能为空");
		}
	    if( data.userType == ""){
			throw new BusinessException("userType不能为空");
		}
	    if( data.serviceFee == ""){
			throw new BusinessException("serviceFee不能为空");
		}
		
		options.data = {
			rechargeId:data.rechargeId,
			userId:data.userId,
			amount:data.amount,
			preAmount:data.preAmount,
			status:data.status,
			adminId:data.adminId,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			type:data.type,
			userType:data.userType,
			serviceFee:data.serviceFee,
		}
		options.url = urls.ms+"/user/recharge/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/recharge/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/recharge/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);