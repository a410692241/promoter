'use strict';
app.service('bankService', [function() {
	
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
		
	    if( data.logo == ""){
			throw new BusinessException("logo不能为空");
		}
	    if( data.name == ""){
			throw new BusinessException("name不能为空");
		}
	    if( data.code == ""){
			throw new BusinessException("code不能为空");
		}
	    if( data.swiftCode == ""){
			throw new BusinessException("swiftCode不能为空");
		}
		
		options.data = {
			bankId:data.bankId,
			logo:data.logo,
			name:new Date( data.name ).format('yyyy-MM-dd HH:mm:ss'),
			code:data.code,
			swiftCode:data.swiftCode,
		}
		options.url = urls.ms+"/user/bank/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/user/bank/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/user/bank/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);