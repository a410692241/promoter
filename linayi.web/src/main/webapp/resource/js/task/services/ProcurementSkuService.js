'use strict';
app.service('procurementSkuService', [function() {
	
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
		
	    if( data.taskId == ""){
			throw new BusinessException("taskId不能为空");
		}
	    if( data.goodsSkuId == ""){
			throw new BusinessException("goodsSkuId不能为空");
		}
	    if( data.price == ""){
			throw new BusinessException("price不能为空");
		}
	    if( data.quantity == ""){
			throw new BusinessException("quantity不能为空");
		}
	    if( data.buyQuantity == ""){
			throw new BusinessException("buyQuantity不能为空");
		}
	    if( data.buyOption == ""){
			throw new BusinessException("buyOption不能为空");
		}
	    if( data.buyPrice == ""){
			throw new BusinessException("buyPrice不能为空");
		}
	    if( data.buyPriceImage == ""){
			throw new BusinessException("buyPriceImage不能为空");
		}
	    if( data.receiveQuantity == ""){
			throw new BusinessException("receiveQuantity不能为空");
		}
	    if( data.receiveOption == ""){
			throw new BusinessException("receiveOption不能为空");
		}
	    if( data.receiveSite == ""){
			throw new BusinessException("receiveSite不能为空");
		}
		
		options.data = {
			procurementSkuId:data.procurementSkuId,
			taskId:data.taskId,
			goodsSkuId:data.goodsSkuId,
			price:data.price,
			quantity:data.quantity,
			buyQuantity:data.buyQuantity,
			buyOption:data.buyOption,
			buyPrice:data.buyPrice,
			buyPriceImage:data.buyPriceImage,
			receiveQuantity:data.receiveQuantity,
			receiveOption:data.receiveOption,
			receiveSite:data.receiveSite,
		}
		options.url = urls.ms+"/task/procurementSku/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/procurementSku/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/procurementSku/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);