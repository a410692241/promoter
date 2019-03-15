'use strict';
app.service('routeCommunityService', [function() {
	
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
		
	    if( data.routeId == ""){
			throw new BusinessException("routeId不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.rank == ""){
			throw new BusinessException("rank不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.routeTime == ""){
			throw new BusinessException("routeTime不能为空");
		}
		
		options.data = {
			routeCommunityId:data.routeCommunityId,
			routeId:data.routeId,
			communityId:data.communityId,
			rank:data.rank,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			routeTime:new Date( data.routeTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/community/routeCommunity/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/routeCommunity/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/routeCommunity/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);