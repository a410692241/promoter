'use strict';
app.service('procurementOrderService', [function() {
	
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
	    if( data.orderId == ""){
			throw new BusinessException("orderId不能为空");
		}
	    if( data.orderCommunityId == ""){
			throw new BusinessException("orderCommunityId不能为空");
		}
	    if( data.goodsSkuId == ""){
			throw new BusinessException("goodsSkuId不能为空");
		}
	    if( data.quantity == ""){
			throw new BusinessException("quantity不能为空");
		}
	    if( data.allotQuantity == ""){
			throw new BusinessException("allotQuantity不能为空");
		}
	    if( data.sortingQuantity == ""){
			throw new BusinessException("sortingQuantity不能为空");
		}
	    if( data.sortingOption == ""){
			throw new BusinessException("sortingOption不能为空");
		}
	    if( data.sortingStatus == ""){
			throw new BusinessException("sortingStatus不能为空");
		}
	    if( data.boxId == ""){
			throw new BusinessException("boxId不能为空");
		}
	    if( data.receiveSite == ""){
			throw new BusinessException("receiveSite不能为空");
		}
		
		options.data = {
			procurementOrderId:data.procurementOrderId,
			taskId:data.taskId,
			orderId:data.orderId,
			orderCommunityId:data.orderCommunityId,
			goodsSkuId:data.goodsSkuId,
			quantity:data.quantity,
			allotQuantity:data.allotQuantity,
			sortingQuantity:data.sortingQuantity,
			sortingOption:data.sortingOption,
			sortingStatus:data.sortingStatus,
			boxId:data.boxId,
			receiveSite:data.receiveSite,
		}
		options.url = urls.ms+"/task/procurementOrder/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/procurementOrder/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/procurementOrder/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);