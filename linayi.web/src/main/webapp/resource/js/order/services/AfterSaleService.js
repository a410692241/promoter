'use strict';
app.service('afterSaleService', [function() {
	
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
		
	    if( data.orderSkuId == ""){
			throw new BusinessException("orderSkuId不能为空");
		}
	    if( data.type == ""){
			throw new BusinessException("type不能为空");
		}
	    if( data.cause == ""){
			throw new BusinessException("cause不能为空");
		}
	    if( data.content == ""){
			throw new BusinessException("content不能为空");
		}
	    if( data.amount == ""){
			throw new BusinessException("amount不能为空");
		}
	    if( data.processStatus == ""){
			throw new BusinessException("processStatus不能为空");
		}
	    if( data.processCause == ""){
			throw new BusinessException("processCause不能为空");
		}
	    if( data.processContent == ""){
			throw new BusinessException("processContent不能为空");
		}
	    if( data.processTime == ""){
			throw new BusinessException("processTime不能为空");
		}
	    if( data.processUser == ""){
			throw new BusinessException("processUser不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
		
		options.data = {
			afterSaleId:data.afterSaleId,
			orderSkuId:data.orderSkuId,
			type:data.type,
			cause:data.cause,
			content:data.content,
			amount:data.amount,
			processStatus:data.processStatus,
			processCause:data.processCause,
			processContent:data.processContent,
			processTime:new Date( data.processTime ).format('yyyy-MM-dd HH:mm:ss'),
			processUser:data.processUser,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			communityId:data.communityId,
		}
		options.url = urls.ms+"/order/afterSale/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/order/afterSale/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/order/afterSale/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);