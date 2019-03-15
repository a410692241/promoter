'use strict';
app.service('sortingSkuService', [function() {
	
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
	    if( data.taskCommunityId == ""){
			throw new BusinessException("taskCommunityId不能为空");
		}
	    if( data.orderId == ""){
			throw new BusinessException("orderId不能为空");
		}
	    if( data.goodsSkuId == ""){
			throw new BusinessException("goodsSkuId不能为空");
		}
	    if( data.supermarketId == ""){
			throw new BusinessException("supermarketId不能为空");
		}
	    if( data.sortingQuantity == ""){
			throw new BusinessException("sortingQuantity不能为空");
		}
	    if( data.sortingOption == ""){
			throw new BusinessException("sortingOption不能为空");
		}
	    if( data.boxId == ""){
			throw new BusinessException("boxId不能为空");
		}
	    if( data.flowerQuantity == ""){
			throw new BusinessException("flowerQuantity不能为空");
		}
	    if( data.flowerOption == ""){
			throw new BusinessException("flowerOption不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
		
		options.data = {
			sortingSkuId:data.sortingSkuId,
			taskId:data.taskId,
			taskCommunityId:data.taskCommunityId,
			orderId:data.orderId,
			goodsSkuId:data.goodsSkuId,
			supermarketId:data.supermarketId,
			sortingQuantity:data.sortingQuantity,
			sortingOption:data.sortingOption,
			boxId:data.boxId,
			flowerQuantity:data.flowerQuantity,
			flowerOption:data.flowerOption,
			status:data.status,
		}
		options.url = urls.ms+"/flower/sortingSku/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/flower/sortingSku/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/flower/sortingSku/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);