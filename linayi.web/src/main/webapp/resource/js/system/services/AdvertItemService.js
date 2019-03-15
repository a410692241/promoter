'use strict';
app.service('advertItemService', [function() {
	
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
		
	    if( data.advertItemId == ""){
			throw new BusinessException("advertItemId不能为空");
		}
	    if( data.imagePath == ""){
			throw new BusinessException("imagePath不能为空");
		}
	    if( data.openType == ""){
			throw new BusinessException("openType不能为空");
		}
	    if( data.openValue == ""){
			throw new BusinessException("openValue不能为空");
		}
		
		options.data = {
			advertItemId:data.advertItemId,
			advertId:data.advertId,
			imagePath:data.imagePath,
			openType:data.openType,
			openValue:data.openValue,
		}
		options.url = urls.ms+"/system/advertItem/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/system/advertItem/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/system/advertItem/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);