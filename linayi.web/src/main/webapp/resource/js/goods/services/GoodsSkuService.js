'use strict';
app.service('goodsSkuService', [function() {
	
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
		
	    if( data.goodsId == ""){
			throw new BusinessException("goodsId不能为空");
		}
	    if( data.goodsName == ""){
			throw new BusinessException("goodsName不能为空");
		}
	    if( data.quantity == ""){
			throw new BusinessException("quantity不能为空");
		}
	    if( data.unit == ""){
			throw new BusinessException("unit不能为空");
		}
	    if( data.barcode == ""){
			throw new BusinessException("barcode不能为空");
		}
	    if( data.skuNo == ""){
			throw new BusinessException("skuNo不能为空");
		}
	    if( data.removed == ""){
			throw new BusinessException("removed不能为空");
		}
	    if( data.buyFee == ""){
			throw new BusinessException("buyFee不能为空");
		}
	    if( data.deliverFee == ""){
			throw new BusinessException("deliverFee不能为空");
		}
		
		options.data = {
			goodsSkuId:data.goodsSkuId,
			goodsId:data.goodsId,
			goodsName:data.goodsName,
			quantity:data.quantity,
			unit:data.unit,
			barcode:data.barcode,
			skuNo:data.skuNo,
			removed:data.removed,
			buyFee:data.buyFee,
			deliverFee:data.deliverFee,
		}
		options.url = urls.ms+"/goods/goodsSku/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/goodsSku/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/goodsSku/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);