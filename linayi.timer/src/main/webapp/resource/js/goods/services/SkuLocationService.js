'use strict';
app.service('skuLocationService', [function() {
	
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
	    if( data.goodsSkuId == ""){
			throw new BusinessException("goodsSkuId不能为空");
		}
		
		options.data = {
			skuLocationId:data.skuLocationId,
			communityId:data.communityId,
			goodsSkuId:data.goodsSkuId,
		}
		options.url = urls.ms+"/goods/skuLocation/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/skuLocation/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/skuLocation/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);