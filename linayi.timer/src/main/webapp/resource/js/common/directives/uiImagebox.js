//initModel
//<div id="iconFile" class="easyui-imagebox" data-options="name:'file'" imagename="file">
//	<img style="width: initial; height: initial; max-width: initial; max-height: initial; cursor: pointer;" alt="无图片" src="blob:http://172.16.1.251:8087/43cdbe58-54ad-4ada-b004-2dd6e8e62bcc">
//	<input type="file" style="display:none" name="file">
//</div>
angular.module('app')
    .directive('uiImagebox', function ( $compile,$parse,toaster ) {
    	function getDefaultOptions(){
        	return {
    			id: null,
    			name:null,
    			width:null,
    			height:null,
    			src:null,
    			alt: null,
//    			图片后缀名，多个用逗号隔开
    			extension:null,
    			disabled: false,
    			//如果图片不存在，是否以默认图片填充，默认填充
    			toDefaultImg:true
    		}
        }
        
        $.fn.imagebox={
        	parseOptions:function parseOptions( target ){
	    		var t = $(target);
	    		return $.extend({}, $.parser.parseOptions(target, 
	    			['id','name','src','alt']
	    		), {
	    			disabled: (t.attr('disabled') ? true : undefined),
	    		})
        	}
    	}
    	
    	return {
        	restrict: 'A',
        	require:'ngModel',
			replace:true,
			scope:true,
            compile: function(element,attrs,ngModelCtrl){
            	var target = element[0];
            	$.data(target, 'imagebox', {
    				options: $.extend({}, getDefaultOptions(), $.fn.imagebox.parseOptions( target ) )
    			});
    			$( target ).removeAttr('disabled');
        		var opts = $.data(target, 'imagebox').options;
        		var t = $(target).empty();
        		var $img = null;
        		if( t.is("img") ){
        			$img = t;
        		}else{
        			$img = $("<img style=''/>").appendTo(t);
        		}
        		if( opts.toDefaultImg ){
        			$img.attr("ng-init",""+attrs.ngModel+"=("+attrs.ngModel+"| toDefaultImg)");
        		}
//        		var defaultImg = location.origin+"/"+location.pathname.split("/")[1]+"/resource/image/addImg.png";
        		$img.attr("ng-src","{{"+attrs.ngModel+"}}");
        		
        		$img.css("width",opts.width ? opts.width : "initial");
        		$img.css("height",opts.height ? opts.height : "initial");
        		$img.css("max-width",opts.maxWidth ? opts.maxWidth : "initial");
        		$img.css("max-height",opts.maxHeight ? opts.maxHeight : "initial");
        		$img.css("cursor",opts.cursor ? opts.cursor : "pointer");
        		
        		$img.attr("alt",opts.alt ? opts.alt : "无图片");
        		
        		var $file = $("<input type='file' accept='image/*' style='display:none'/>");
        		$img.after($file);
        		if(opts.name){
        			$file.attr("name",opts.name);
        			$(target).attr("imagename",opts.name);
        			$(target).removeAttr("name");
        		}else{
        			$file.attr("name","file");
        		}
            	//连接函数，注册事件
				return function($scope, $element, $attrs, $ngModel){
					var $file = $element.find("input[type='file']").eq(0);
					var $img = $element.find("img").eq(0);
					$img.on("click",function( event ){
						if( opts.disabled ){
							return;
						}
						$file.val('');
						$file[0].click();
					})
					$file.on("change",function( event ){
						var file = event.target.files[0];
		    			if(file){
		    				var FileName=new String((file.name).toLowerCase());//文件名
		    				
		    	            var extension=new String (FileName.substring(FileName.lastIndexOf(".")+1,FileName.length));//文件扩展名
		    	            
		    	            var imgExtension = opts.extension;
		    	            if( imgExtension ){
		    	            	imgExtension = imgExtension.toLowerCase();
		    	            	if( imgExtension.indexOf(extension) >= 0){
		    	            		$ngModel.$setViewValue( URL.createObjectURL( file ) );
		    	            	}
		    	            	$scope.$apply(function(){
		    	            		toaster.error( "","请选择正确格式的图片!",3000 );
		    	            	})
		    	            }else{
		    	            	var extList = "jpg,png,jpeg,ico,bmp";
		    	            	if( extList.indexOf(extension) >= 0 ){
		    	            		$ngModel.$setViewValue( URL.createObjectURL( file ) );
		    	            	}else{
		    	            		$scope.$apply(function(){
			    	            		toaster.error( "","请选择正确格式的图片!",3000 );
			    	            	})
		    	            	}
		    	            }
		    	            
		    			}
					})
				};
			}
        };
        
});