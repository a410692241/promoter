'use strict';
app.service('orderService', [function() {
	
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
		
	    if( data.userId == ""){
			throw new BusinessException("userId不能为空");
		}
	    if( data.payType == ""){
			throw new BusinessException("payType不能为空");
		}
	    if( data.nickname == ""){
			throw new BusinessException("nickname不能为空");
		}
	    if( data.mobile == ""){
			throw new BusinessException("mobile不能为空");
		}
	    if( data.address == ""){
			throw new BusinessException("address不能为空");
		}
	    if( data.areaCode == ""){
			throw new BusinessException("areaCode不能为空");
		}
	    if( data.serviceFee == ""){
			throw new BusinessException("serviceFee不能为空");
		}
	    if( data.ammount == ""){
			throw new BusinessException("ammount不能为空");
		}
	    if( data.status == ""){
			throw new BusinessException("status不能为空");
		}
	    if( data.communityId == ""){
			throw new BusinessException("communityId不能为空");
		}
	    if( data.seccondStatus == ""){
			throw new BusinessException("seccondStatus不能为空");
		}
	    if( data.lessHandle == ""){
			throw new BusinessException("lessHandle不能为空");
		}
	    if( data.remark == ""){
			throw new BusinessException("remark不能为空");
		}
	    if( data.reserveTime == ""){
			throw new BusinessException("reserveTime不能为空");
		}
	    if( data.receiveTime == ""){
			throw new BusinessException("receiveTime不能为空");
		}
	    if( data.cancelTime == ""){
			throw new BusinessException("cancelTime不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.removed == ""){
			throw new BusinessException("removed不能为空");
		}
	    if( data.packingOption == ""){
			throw new BusinessException("packingOption不能为空");
		}
	    if( data.packingTime == ""){
			throw new BusinessException("packingTime不能为空");
		}
		
		options.data = {
			orderId:data.orderId,
			userId:data.userId,
			payType:data.payType,
			nickname:data.nickname,
			mobile:data.mobile,
			address:data.address,
			areaCode:data.areaCode,
			serviceFee:data.serviceFee,
			ammount:data.ammount,
			status:data.status,
			communityId:data.communityId,
			seccondStatus:data.seccondStatus,
			lessHandle:data.lessHandle,
			remark:data.remark,
			reserveTime:new Date( data.reserveTime ).format('yyyy-MM-dd HH:mm:ss'),
			receiveTime:new Date( data.receiveTime ).format('yyyy-MM-dd HH:mm:ss'),
			cancelTime:new Date( data.cancelTime ).format('yyyy-MM-dd HH:mm:ss'),
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			removed:data.removed,
			packingOption:data.packingOption,
			packingTime:new Date( data.packingTime ).format('yyyy-MM-dd HH:mm:ss'),
		}
		options.url = urls.ms+"/order/order/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/order/order/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/order/order/delete.do";
		ajax( options );
	}

	function saveSelfOrder(options) {
		var data = options.data;
		if (data.goodsName == ""){
			alert("商品名不能为空");
			throw new BusinessException("商品名不能为空")
		}
		if (data.brandName == ""){
			alert("品牌名不能为空");
			throw new BusinessException("品牌名不能为空")
		}
		if (data.attrValue == ""){
			alert("商品属性值不能为空");
			throw new BusinessException("商品属性值不能为空")
		}
		if (data.minPrice > data.maxPrice){
			alert("最低价格不能高于最高价格");
			throw new BusinessException("最低价格不能高于最高价格")
		}
		options.data = {
			selfOrderId: data.selfOrderId,
			goodsName: data.goodsName,
			brandName: data.brandName,
			attrValue: data.attrValue,
			minPrice: data.minPrice,
			maxPrice: data.maxPrice,
			num: data.num
		};
		options.url = urls.ms + "/selfOrder/save.do"
		ajax(options)
	}
	function share(options) {
		var data = options.data;
		if (data.goodsName == ""){
			alert("请检查商品名")
			throw new BusinessException("请检查商品名")
		}
		if (data.brandName == ""){
			alert("请检查品牌名")
			throw new BusinessException("请检查品牌名")
		}
		if (data.attrValue == ""){
			alert("请检查商品属性")
			throw new BusinessException("请检查商品属性")
		}
		options.data = {
			selfOrderId: data.selfOrderId,
			goodsName: data.goodsName,
			brandName: data.brandName,
			attrValue: data.attrValue,
			userId: data.userId
		};
		options.url = urls.ms + "/selfOrder/share.do";
		ajax(options)
	}

	function turnToOrder(options) {
		options.url = urls.ms + "/selfOrder//turnToOrder.do";
		ajax(options)
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save,
		saveSelfOrder:saveSelfOrder,
		share: share,
		turnToOrder: turnToOrder
	};
}]);