(function ( $, K ) {
'use strict';
angular.module('kindeditor',[])
.config(function(){
    function create(target) {
        var opts = $.data(target, 'kindeditor');
        var editor = K.create(target, opts);
        $.data(target, 'kindeditor').editor = editor;
    }
	
	$.fn.kindeditor = function (options, param) {
        if (typeof options == 'string') {
            var method = $.fn.kindeditor.methods[options];
            if (method) {
                return method(this, param);
            }
        }
        options = options || {};
        return this.each(function () {
            var state = $.data(this, 'kindeditor');
            if (state) {
                $.extend(state, options);
            } else {
                state = $.data(this, 'kindeditor', $.extend({}, $.fn.kindeditor.defaults, $.fn.kindeditor.parseOptions(this), options));
            }
            create(this);
        });
    };
	
	$.fn.kindeditor.parseOptions = function (target) {
        return $.extend({}, $.parser.parseOptions(target, []));
    };
    
    $.fn.kindeditor.methods = {
        editor: function (jq) {
            return $.data(jq[0], 'kindeditor').editor;
        }
    };
    
    $.fn.kindeditor.defaults = {
    		designMode : true,
    		cssPath : 'resource/others/BeyondAdmin/lib/jquery/kindeditor/plugins/code/prettify.css',
			uploadJson : 'resource/others/BeyondAdmin/lib/jquery/kindeditor/jsp/upload_json.jsp',
			fileManagerJson : 'resource/others/BeyondAdmin/lib/jquery/kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
    		fullscreenMode : false,
    		filterMode : true,
    		wellFormatMode : true,
    		shadowMode : true,
    		loadStyleMode : true,
    		basePath : K.basePath,
    		themesPath : K.basePath + 'themes/',
    		langPath : K.basePath + 'lang/',
    		pluginsPath : K.basePath + 'plugins/',
    		themeType : 'default',
    		langType : 'zh_CN',
    		urlType : '',
    		newlineTag : 'p',
    		resizeType : 2,
    		syncType : 'form',
    		pasteType : 2,
    		dialogAlignType : 'page',
    		useContextmenu : true,
    		fullscreenShortcut : false,
    		bodyClass : 'ke-content',
    		indentChar : '\t',
    		cssPath : '',
    		cssData : '',
    		minWidth : 650,
    		minHeight : 100,
    		minChangeSize : 50,
    		zIndex : 811213,
    		items : [
    			'source','undo', 'redo', '|', 'preview', 'print', 'cut', 'copy', 'paste',
    			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
    			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
    			'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
    			'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
    			'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
    			'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
    			'anchor', 'link', 'unlink', '|', 'about'
    		],
    		noDisableItems : ['source', 'fullscreen'],
    		colorTable : [
    			['#E53333', '#E56600', '#FF9900', '#64451D', '#DFC5A4', '#FFE500'],
    			['#009900', '#006600', '#99BB00', '#B8D100', '#60D978', '#00D5FF'],
    			['#337FE5', '#003399', '#4C33E5', '#9933E5', '#CC33E5', '#EE33EE'],
    			['#FFFFFF', '#CCCCCC', '#999999', '#666666', '#333333', '#000000']
    		],
    		fontSizeTable : ['9px', '10px', '12px', '14px', '16px', '18px', '24px', '32px'],
    		htmlTags : {
    			font : ['id', 'class', 'color', 'size', 'face', '.background-color'],
    			span : [
    				'id', 'class', '.color', '.background-color', '.font-size', '.font-family', '.background',
    				'.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.line-height'
    			],
    			div : [
    				'id', 'class', 'align', '.border', '.margin', '.padding', '.text-align', '.color',
    				'.background-color', '.font-size', '.font-family', '.font-weight', '.background',
    				'.font-style', '.text-decoration', '.vertical-align', '.margin-left'
    			],
    			table: [
    				'id', 'class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'bordercolor',
    				'.padding', '.margin', '.border', 'bgcolor', '.text-align', '.color', '.background-color',
    				'.font-size', '.font-family', '.font-weight', '.font-style', '.text-decoration', '.background',
    				'.width', '.height', '.border-collapse'
    			],
    			'td,th': [
    				'id', 'class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor',
    				'.text-align', '.color', '.background-color', '.font-size', '.font-family', '.font-weight',
    				'.font-style', '.text-decoration', '.vertical-align', '.background', '.border'
    			],
    			a : ['id', 'class', 'href', 'target', 'name'],
    			embed : ['id', 'class', 'src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess'],
    			img : ['id', 'class', 'src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border'],
    			'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
    				'id', 'class', 'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',
    				'.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left'
    			],
    			pre : ['id', 'class'],
    			hr : ['id', 'class', '.page-break-after'],
    			'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : ['id', 'class'],
    			iframe : ['id', 'class', 'src', 'frameborder', 'width', 'height', '.width', '.height']
    		},
    		layout : '<div class="container"><div class="toolbar"></div><div class="edit"></div><div class="statusbar"></div></div>',
	        afterChange: function () {
	            this.sync();
	        },
	        afterBlur: function () { this.sync(); }
    };
    
})
.constant('kindeditorConfig', {
	
})
.directive('kindeditor',function( $compile, $timeout ){
	
	return {
		restrict:'A',
        require:'?ngModel',
        scope:true,
        compile: function( $element,$attrs,ngModelCtrl ) {
            return function( $scope, $element, $attrs, $ngModel){
            	$timeout(function(){
            		$element.kindeditor({
            			afterChange:function(){
            				$ngModel.$setViewValue( this.html() );
            			}
            		})
            	},200)
        		
            }
        }
	}
});
})(jQuery, KindEditor);