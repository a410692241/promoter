'use strict';
app.service('skuSupermarketService', [function() {
	
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
	    if( data.supermarketId == ""){
			throw new BusinessException("supermarketId不能为空");
		}
	    if( data.price == ""){
			throw new BusinessException("price不能为空");
		}
	    if( data.priceType == ""){
			throw new BusinessException("priceType不能为空");
		}
	    if( data.validStart == ""){
			throw new BusinessException("validStart不能为空");
		}
	    if( data.validEnd == ""){
			throw new BusinessException("validEnd不能为空");
		}
	    if( data.correctId == ""){
			throw new BusinessException("correctId不能为空");
		}
	    if( data.earningsTime == ""){
			throw new BusinessException("earningsTime不能为空");
		}
	    if( data.removed == ""){
			throw new BusinessException("removed不能为空");
		}
		
		options.data = {
			skuSupermarketId:data.skuSupermarketId,
			goodsSkuId:data.goodsSkuId,
			supermarketId:data.supermarketId,
			price:data.price,
			priceType:data.priceType,
			validStart:new Date( data.validStart ).format('yyyy-MM-dd HH:mm:ss'),
			validEnd:new Date( data.validEnd ).format('yyyy-MM-dd HH:mm:ss'),
			correctId:data.correctId,
			earningsTime:new Date( data.earningsTime ).format('yyyy-MM-dd HH:mm:ss'),
			removed:data.removed,
		}
		options.url = urls.ms+"/goods/skuSupermarket/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/skuSupermarket/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/skuSupermarket/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);