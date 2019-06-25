'use strict';
app.service('smallCommunityReqService', [function() {
	
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
		
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.smallCommunity == ""){
			throw new BusinessException("小区名不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("请选择是否检查");
		}
	    if( data.createTime == ""){
			throw new BusinessException("时间不能为空");
		}

		options.data = {
            smallCommunityReqId:data.smallCommunityReqId,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
            status:data.status,
            smallCommunity:data.smallCommunity,
		}
		options.url = urls.ms+"/community/smallCommunityReq/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/smallCommunityReq/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/smallCommunityReq/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);