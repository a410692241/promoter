'use strict';
app.service('attrValueService', [function() {
	
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
		
	    if( data.name == ""){
			throw new BusinessException("name不能为空");
		}
	    if( data.attributeId == ""){
			throw new BusinessException("attributeId不能为空");
		}
	    if( data.removed == ""){
			throw new BusinessException("removed不能为空");
		}
		
		options.data = {
			attrValueId:data.attrValueId,
			name:data.name,
			attributeId:data.attributeId,
			removed:data.removed,
		}
		options.url = urls.ms+"/goods/attrValue/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/attrValue/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/attrValue/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);