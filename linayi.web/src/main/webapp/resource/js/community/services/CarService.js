'use strict';
app.service('carService', [function() {
	
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
		
	    if( data.driverId == ""){
			throw new BusinessException("driverId不能为空");
		}
	    if( data.plate == ""){
			throw new BusinessException("plate不能为空");
		}
	    if( data.createTime == ""){
			throw new BusinessException("createTime不能为空");
		}
	    if( data.carNo == ""){
			throw new BusinessException("carNo不能为空");
		}
	    if( data.removed == ""){
			throw new BusinessException("removed不能为空");
		}
	    if( data.routeId == ""){
			throw new BusinessException("routeId不能为空");
		}
		
		options.data = {
			carId:data.carId,
			driverId:data.driverId,
			plate:data.plate,
			createTime:new Date( data.createTime ).format('yyyy-MM-dd HH:mm:ss'),
			carNo:data.carNo,
			removed:data.removed,
			routeId:data.routeId,
		}
		options.url = urls.ms+"/community/car/save.do";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/community/car/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/community/car/delete.do";
		ajax( options );
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);