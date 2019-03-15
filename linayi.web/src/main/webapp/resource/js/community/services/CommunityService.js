'use strict';
app.service('communityService', [function() {
	
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
		
//		if( !data.sectionNo ){
//			throw new BusinessException("请输入社区编号");
//		}
	    if( !data.name ){
			throw new BusinessException("亲输入社区名称");
		}
//	    if( !data.nickname ){
//	    	throw new BusinessException("请输入负责人名称");
//	    }
	    if( !data.mobile ){
	    	throw new BusinessException("请输入手机号");
	    }
	    if( !data.address ){
			throw new BusinessException("请输入地址");
		}
	    if( !data.areaCode ){
			throw new BusinessException("请选择街道");
		}
		options.data = {
			communityId:data.communityId,
			name:data.name,
			address:data.address,
//			sectionNo:data.sectionNo,
//			nickname:data.nickname,
			mobile:data.mobile,
			email:data.email,
			qq:data.qq,
			areaCode:data.areaCode,
		}
		options.url = urls.ms+"/community/community/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/community/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/community/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);