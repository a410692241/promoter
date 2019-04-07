'use strict';
app.service('communityAccountService', [function() {
	
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
		
	    if( data.areaCode == ""){
			throw new BusinessException("areaCode不能为空");
		}
	    if( data.account == ""){
			throw new BusinessException("account不能为空");
		}
	    if( data.toChangePSW != data.affirmPSD){
			throw new BusinessException("确认密码不相同");
		}
	   /* if( data.password == ){
			throw new BusinessException("parentId不能为空");
		}*/
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    
		
		options.data = {
			communityAccountId:data.communityAccountId,
			areaCode:data.areaCode,
			account:data.account,
			password:data.dbPSW,
			toChangePSW:data.toChangePSW,
		}
		options.url = urls.ms+"/community/communityAccount/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/communityAccount/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/communityAccount/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);