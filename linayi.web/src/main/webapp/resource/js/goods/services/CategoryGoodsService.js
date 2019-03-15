'use strict';
app.service('categoryGoodsService', [function() {
	
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
		
	    if( data.goodsId == ""){
			throw new BusinessException("goodsId不能为空");
		}
	    if( data.categoryId == ""){
			throw new BusinessException("categoryId不能为空");
		}
		
		options.data = {
			categoryGoodsId:data.categoryGoodsId,
			goodsId:data.goodsId,
			categoryId:data.categoryId,
		}
		options.url = urls.ms+"/goods/categoryGoods/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/categoryGoods/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/categoryGoods/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);