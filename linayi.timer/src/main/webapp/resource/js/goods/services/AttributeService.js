'use strict';
app.service('attributeService', [function() {
	
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
			throw new BusinessException("属性名不能为空");
		}
	    //判断是否填写规格值
		
			angular.forEach( data.attributeValueList,function( item,index ){
				if( !item.value ){
					throw "规格值不能为空！";
				}
			} );
		
		options.data =  angular.toJson({
			attributeId:data.attributeId,
			name:data.name,
			attributeValueList:data.attributeValueList,
		});
		options.url = urls.ms+"/goods/attribute/save.do";
		options.contentType="application/json";
		ajax( options );
	}
	
	function getById( options ){
		options.url = urls.ms+"/goods/attribute/get.do";
		ajax( options );
	}
	
	function remove( options ){
		options.url = urls.ms+"/goods/attribute/delete.do";
		ajax( options);
	}
	
	return {
		remove:remove,
		getById:getById,
		save:save
	};
}]);