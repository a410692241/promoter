'use strict';
app.service('correctService', [function() {
	
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
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.imagePath == ""){
			throw new BusinessException("imagePath不能为空");
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
	    if( data.parentId == ""){
			throw new BusinessException("parentId不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.seccondImagePath == ""){
			throw new BusinessException("seccondImagePath不能为空");
		}
		
		options.data = {
			correctId:data.correctId,
			goodsSkuId:data.goodsSkuId,
			supermarketId:data.supermarketId,
			userId:data.userId,
			imagePath:data.imagePath,
			price:data.price,
			priceType:data.priceType,
			validStart:new Date( data.validStart ).format('yyyy-MM-dd HH:mm:ss'),
			validEnd:new Date( data.validEnd ).format('yyyy-MM-dd HH:mm:ss'),
			parentId:data.parentId,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			status:data.status,
			seccondImagePath:data.seccondImagePath,
		}
		options.url = urls.ms+"/goods/correct/save.do";
		ajax( options );
	}
	
	function getById( options ){
		/*var data = options.data;
		options.data = {
			createTime:new Date( data.actualStartTime ).format('yyyy-MM-dd HH:mm:ss'),
		}*/
		options.url = urls.ms+"/correct/correct/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/correct/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);