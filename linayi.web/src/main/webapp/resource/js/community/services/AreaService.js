'use strict';
app.service('areaService', [function() {
	
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
		debugger;
		var data = options.data;
		
	    // if( data.province == ""){
		// 	throw new BusinessException("请选择省");
		// }
	    // if( data.city == ""){
		// 	throw new BusinessException("市不能为空");
		// }
	    // if( data.region == ""){
		// 	throw new BusinessException("小区名不能为空");
		// }
	    // if( data.street == ""){
		// 	throw new BusinessException("街道不能为空");
		// }

		options.data = {
			code: data.code,
			name:data.name,
		}
		options.url = urls.ms+"/area/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/smallCommunityReq/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/smallCommunityReq/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);