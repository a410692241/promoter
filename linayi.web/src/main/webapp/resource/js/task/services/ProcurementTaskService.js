'use strict';
app.service('procurementTaskService', [function() {
	
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
		options.contentType="application/json";
		options.url = urls.ms+"/task/procurementTask/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/task/procurementTask/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/task/procurementTask/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);