'use strict';
app.service('goodsEffectService', [function() {
	
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
		
		options.data = {
			goodsId:data.goodsId,
			goodsName:data.goodsName,
			subtitle:data.subtitle,
			content:data.content,
			lastModify:new Date( data.lastModify ).format('yyyy-MM-dd HH:mm:ss'),
			brandId:data.brandId,
			adminId:data.adminId,
			remark:data.remark,
			productionId:data.productionId,
			status:data.status,
			removed:data.removed,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/goods/goods/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/goods/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/goods/deleteGoodsById.do";
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