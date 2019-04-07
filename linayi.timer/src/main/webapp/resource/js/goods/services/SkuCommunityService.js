'use strict';
app.service('skuCommunityService', [function() {
	
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
		
	    if( data.goodsSkuId == ""){
			throw new BusinessException("goodsSkuId不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.minSupermarketId == ""){
			throw new BusinessException("minSupermarketId不能为空");
		}
	    if( data.maxSupermarketId == ""){
			throw new BusinessException("maxSupermarketId不能为空");
		}
	    if( data.minPrice == ""){
			throw new BusinessException("minPrice不能为空");
		}
	    if( data.maxPrice == ""){
			throw new BusinessException("maxPrice不能为空");
		}
		
		options.data = {
			skuCommunityId:data.skuCommunityId,
			goodsSkuId:data.goodsSkuId,
			communityId:data.communityId,
			minSupermarketId:data.minSupermarketId,
			maxSupermarketId:data.maxSupermarketId,
			minPrice:data.minPrice,
			maxPrice:data.maxPrice,
		}
		options.url = urls.ms+"/goods/skuCommunity/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/skuCommunity/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/skuCommunity/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);