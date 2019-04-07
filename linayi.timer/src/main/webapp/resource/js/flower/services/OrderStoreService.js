'use strict';
app.service('orderStoreService', [function() {
	
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
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.receiveSite == ""){
			throw new BusinessException("receiveSite不能为空");
		}
		
		options.data = {
			orderStoreId:data.orderStoreId,
			orderId:data.orderId,
			goodsSkuId:data.goodsSkuId,
			supermarketId:data.supermarketId,
			quantity:data.quantity,
			status:data.status,
			receiveSite:data.receiveSite,
		}
		options.url = urls.ms+"/flower/orderStore/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/flower/orderStore/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/flower/orderStore/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);