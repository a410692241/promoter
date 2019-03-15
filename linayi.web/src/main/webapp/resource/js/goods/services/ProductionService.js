'use strict';
app.service('productionService', [function() {
	
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
		options.data = angular.toJson( options.data );
		options.url = urls.ms+"/goods/production/save.do";
		options.contentType="application/json";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/production/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/production/delete.do";
		ajax( options );
	}
	function listBrand( options ){
		options.data.sqlId="listBrand";
		options.url = urls.ms+"/system/options/listOptions.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save,
		listBrand:listBrand
	};
}]);