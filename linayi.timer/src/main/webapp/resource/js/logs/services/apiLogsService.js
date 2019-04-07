'use strict';
app.service('apiLogsService', [function() {
	function ajax( options ){
		$.ajax({
			url:options.url,
			data:options.data,
			async:options.async ? options.async : true,
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
	
	
	function getById( options ){
		options.url = urls.ms+"/logs/apiLogs/show.do";
		ajax( options );
	}
	
	return {
		getById:getById,
	};
}]);