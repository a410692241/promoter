'use strict';
app.service('shoppingCarService', [function() {
	
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
		
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
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
		
		options.data = {
			shoppingCarId:data.shoppingCarId,
			userId:data.userId,
			goodsSkuId:data.goodsSkuId,
			supermarketId:data.supermarketId,
			quantity:data.quantity,
		}
		options.url = urls.ms+"/user/shoppingCar/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/shoppingCar/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/shoppingCar/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);