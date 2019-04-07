'use strict';
app.service('skuAttributeService', [function() {
	
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
	    if( data.attributeId == ""){
			throw new BusinessException("attributeId不能为空");
		}
	    if( data.attrValueId == ""){
			throw new BusinessException("attrValueId不能为空");
		}
		
		options.data = {
			skuAttributeId:data.skuAttributeId,
			goodsSkuId:data.goodsSkuId,
			attributeId:data.attributeId,
			attrValueId:data.attrValueId,
		}
		options.url = urls.ms+"/goods/skuAttribute/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/skuAttribute/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/skuAttribute/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);