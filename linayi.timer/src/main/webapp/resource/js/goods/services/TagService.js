'use strict';
app.service('tagService', [function() {
	
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
	    if( data.code == ""){
			throw new BusinessException("code不能为空");
		}
		
		options.data = {
			tagId:data.tagId,
			name:data.name,
			code:data.code,
		}
		options.url = urls.ms+"/goods/tag/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/tag/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/tag/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);