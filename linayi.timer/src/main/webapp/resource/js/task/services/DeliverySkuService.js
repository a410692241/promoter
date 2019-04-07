'use strict';
app.service('deliverySkuService', [function() {
	
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
	    if( data.quantity == ""){
			throw new BusinessException("quantity不能为空");
		}
	    if( data.option == ""){
			throw new BusinessException("option不能为空");
		}
		
		options.data = {
			deliverySkuId:data.deliverySkuId,
			taskId:data.taskId,
			goodsSkuId:data.goodsSkuId,
			quantity:data.quantity,
			option:data.option,
		}
		options.url = urls.ms+"/task/deliverySku/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/deliverySku/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/deliverySku/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);