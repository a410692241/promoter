'use strict';
app.service('categoryService', [function() {
	
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
	    if( data.parentCode == ""){
			throw new BusinessException("parentCode不能为空");
		}
	    if( data.logo == ""){
			throw new BusinessException("logo不能为空");
		}
	    if( data.rank == ""){
			throw new BusinessException("rank不能为空");
		}
		
		options.data = {
			categoryId:data.categoryId,
			name:data.name,
			code:data.code,
			parentCode:data.parentCode,
			logo:data.logo,
			rank:data.rank,
		}
		options.url = urls.ms+"/goods/category/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/category/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/category/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);