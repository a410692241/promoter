'use strict';
app.service('supermarketService', [function() {
	
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
	    if( data.address == ""){
			throw new BusinessException("address不能为空");
		}
	    if( data.areaCode == ""){
			throw new BusinessException("areaCode不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.type == ""){
			throw new BusinessException("type不能为空");
		}
	    if( data.logo == ""){
			throw new BusinessException("logo不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.remark == ""){
			throw new BusinessException("无服务点");
		}
	    if( data.updateTime == ""){
			throw new BusinessException("updateTime不能为空");
		}
	    if( data.sectionNo == ""){
			throw new BusinessException("sectionNo不能为空");
		}
	    if( data.longitude == ""){
			throw new BusinessException("longitude不能为空");
		}
	    if( data.latitude == ""){
			throw new BusinessException("latitude不能为空");
		}
		
		options.data = {
			supermarketId:data.supermarketId,
			name:data.name,
			address:data.address,
			areaCode:data.areaCode,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			type:data.type,
			logo:data.logo,
			status:data.status,
			remark:data.remark,
			lastModify:new Date( data.updateTime ).format('yyyy-MM-dd HH:mm:ss'),
			sectionNo:data.sectionNo,
			longitude:data.longitude,
			latitude:data.latitude,
		}
		options.url = urls.ms+"/community/supermarket/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/supermarket/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/supermarket/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);