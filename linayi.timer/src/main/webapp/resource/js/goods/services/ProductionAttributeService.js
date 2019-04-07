'use strict';
app.service('productionAttributeService', [function() {
	
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
		
	    if( data.productionId == ""){
			throw new BusinessException("productionId不能为空");
		}
	    if( data.attributeId == ""){
			throw new BusinessException("attributeId不能为空");
		}
	    if( data.sales == ""){
			throw new BusinessException("sales不能为空");
		}
		
		options.data = {
			productionAttributeId:data.productionAttributeId,
			productionId:data.productionId,
			attributeId:data.attributeId,
			sales:data.sales,
		}
		options.url = urls.ms+"/goods/productionAttribute/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/productionAttribute/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/productionAttribute/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);