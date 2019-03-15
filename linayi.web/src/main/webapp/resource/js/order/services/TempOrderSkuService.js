'use strict';
app.service('tempOrderSkuService', [function() {
	
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
		
	    if( data.orderId == ""){
			throw new BusinessException("orderId不能为空");
		}
	    if( data.skuSupermarketId == ""){
			throw new BusinessException("skuSupermarketId不能为空");
		}
	    if( data.supermarketId == ""){
			throw new BusinessException("supermarketId不能为空");
		}
	    if( data.price == ""){
			throw new BusinessException("price不能为空");
		}
	    if( data.quantity == ""){
			throw new BusinessException("quantity不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.buyFee == ""){
			throw new BusinessException("buyFee不能为空");
		}
	    if( data.sortingCategoryId == ""){
			throw new BusinessException("sortingCategoryId不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
		
		options.data = {
			tempOrderSkuId:data.tempOrderSkuId,
			orderId:data.orderId,
			skuSupermarketId:data.skuSupermarketId,
			supermarketId:data.supermarketId,
			price:data.price,
			quantity:data.quantity,
			communityId:data.communityId,
			buyFee:data.buyFee,
			sortingCategoryId:data.sortingCategoryId,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/order/tempOrderSku/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/order/tempOrderSku/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/order/tempOrderSku/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);