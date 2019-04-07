'use strict';
app.service('orderSkuService', [function() {
	
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
	    if( data.goodsSkuId == ""){
			throw new BusinessException("goodsSkuId不能为空");
		}
	    if( data.supermarketId == ""){
			throw new BusinessException("supermarketId不能为空");
		}
	    if( data.quantity == ""){
			throw new BusinessException("quantity不能为空");
		}
	    if( data.price == ""){
			throw new BusinessException("price不能为空");
		}
	    if( data.maxPrice == ""){
			throw new BusinessException("maxPrice不能为空");
		}
	    if( data.maxSupermarketId == ""){
			throw new BusinessException("maxSupermarketId不能为空");
		}
	    if( data.receiveQuantity == ""){
			throw new BusinessException("receiveQuantity不能为空");
		}
	    if( data.receiveOption == ""){
			throw new BusinessException("receiveOption不能为空");
		}
	    if( data.serviceFee == ""){
			throw new BusinessException("serviceFee不能为空");
		}
	    if( data.deliverFee == ""){
			throw new BusinessException("deliverFee不能为空");
		}
	    if( data.buyFee == ""){
			throw new BusinessException("buyFee不能为空");
		}
	    if( data.supermarketLs == ""){
			throw new BusinessException("supermarketLs不能为空");
		}
		
		options.data = {
			orderSkuId:data.orderSkuId,
			orderId:data.orderId,
			goodsSkuId:data.goodsSkuId,
			supermarketId:data.supermarketId,
			quantity:data.quantity,
			price:data.price,
			maxPrice:data.maxPrice,
			maxSupermarketId:data.maxSupermarketId,
			receiveQuantity:data.receiveQuantity,
			receiveOption:data.receiveOption,
			serviceFee:data.serviceFee,
			deliverFee:data.deliverFee,
			buyFee:data.buyFee,
			supermarketLs:data.supermarketLs,
		}
		options.url = urls.ms+"/order/orderSku/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/order/orderSku/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/order/orderSku/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);