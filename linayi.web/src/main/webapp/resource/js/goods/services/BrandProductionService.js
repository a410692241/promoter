'use strict';
app.service('brandProductionService', [function() {
	
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
	    if( data.brandId == ""){
			throw new BusinessException("brandId不能为空");
		}
		
		options.data = {
			brandProductionId:data.brandProductionId,
			productionId:data.productionId,
			brandId:data.brandId,
		}
		options.url = urls.ms+"/goods/brandProduction/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/brandProduction/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/brandProduction/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);