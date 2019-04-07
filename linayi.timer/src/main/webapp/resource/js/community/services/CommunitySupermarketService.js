'use strict';
app.service('communitySupermarketService', [function() {
	
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
	    if( data.supermarketId == ""){
			throw new BusinessException("supermarketId不能为空");
		}
	    if( data.type == ""){
			throw new BusinessException("type不能为空");
		}
		
		options.data = {
			communitySupermarketId:data.communitySupermarketId,
			communityId:data.communityId,
			supermarketId:data.supermarketId,
			type:data.type,
		}
		options.url = urls.ms+"/community/communitySupermarket/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/communitySupermarket/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/communitySupermarket/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);