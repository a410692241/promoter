'use strict';
app.service('sortingCategoryService', [function() {
	
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
	    if( data.rank == ""){
			throw new BusinessException("rank不能为空");
		}
	    if( data.logo == ""){
			throw new BusinessException("logo不能为空");
		}
		
		options.data = {
			sortingCategoryId:data.sortingCategoryId,
			name:data.name,
			rank:data.rank,
			logo:data.logo,
		}
		options.url = urls.ms+"/goods/sortingCategory/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/sortingCategory/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/sortingCategory/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);