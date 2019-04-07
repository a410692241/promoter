//如果图片路径存在则返回原路径，否则返回默认图片路径
angular.module('app')
.filter('toDefaultImg', function() {
	return function( imagePath ) {	
		if( imagePath ){
			return imagePath;
		}else{
			return app.linayi.defaultImage;
		}
	}
});
//将一个数转正整toPositive
angular.module('app').filter('toPositive', function() {
	return function( input ) {	
		console.info( input );
		if( input ){
			if( input < 0 ){
				return -input;
			}
			return input;
		}else{
			return input;
		}
	}
});