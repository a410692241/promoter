'use strict';
app.service('brandService', [function() {
	
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
	    if( data.logo == ""){
			throw new BusinessException("logo不能为空");
		}
	    if( data.content == ""){
			throw new BusinessException("content不能为空");
		}
	    if( data.rank == ""){
			throw new BusinessException("rank不能为空");
		}
		
		options.data = {
			brandName:data.brandName,
		}
		options.url = urls.ms+"/goods/brand/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/brand/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/brand/delete.do";
		ajax( options );
	}


	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);