'use strict';
app.service('skuSupermarketTagService', [function() {
	
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
		
	    if( data.skuSupermarketId == ""){
			throw new BusinessException("skuSupermarketId不能为空");
		}
	    if( data.tagId == ""){
			throw new BusinessException("tagId不能为空");
		}
		
		options.data = {
			skuSupermarketTagId:data.skuSupermarketTagId,
			skuSupermarketId:data.skuSupermarketId,
			tagId:data.tagId,
		}
		options.url = urls.ms+"/goods/skuSupermarketTag/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/skuSupermarketTag/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/skuSupermarketTag/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);