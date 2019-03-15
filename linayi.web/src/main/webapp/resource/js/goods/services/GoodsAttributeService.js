'use strict';
app.service('goodsAttributeService', [function() {
	
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
	    if( data.attributeId == ""){
			throw new BusinessException("attributeId不能为空");
		}
	    if( data.sales == ""){
			throw new BusinessException("sales不能为空");
		}
		
		options.data = {
			goodsAttributeId:data.goodsAttributeId,
			goodsId:data.goodsId,
			attributeId:data.attributeId,
			sales:data.sales,
		}
		options.url = urls.ms+"/goods/goodsAttribute/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/goodsAttribute/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/goodsAttribute/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);