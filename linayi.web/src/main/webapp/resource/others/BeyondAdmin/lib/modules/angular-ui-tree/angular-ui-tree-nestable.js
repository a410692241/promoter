(function(window, document, angular, undefined){
	angular.module('ui-tree-nestable', [])
	.directive('uiTreeNestable', ['$compile','$templateCache','$interval',function( $compile,$templateCache,$interval ){
		return {
			restrict: 'A',
			require: 'ngModel',
			replace:true,
			controller:function( $scope ){
				$scope.checkNode=function( $event ){
					var $this = $( $event.target )
					if( event.target.type == "checkbox" ){
						var scope = angular.element( $this ).scope();
						var node = scope.$modelValue;
						checkedProcess( $this,!node.checked );
					}
				}
			},
			template:function(element,attrs,ngModelCtrl){
				return "<div>"+element.html()+"</div>"
			},
			compile: function(element,attrs,ngModelCtrl){
				
				return function($scope, $element, $attrs, $ngModel){
					
					var $scope = element.scope();
					var modelName = attrs.ngModel;
					var optionsName = attrs.uiTreeNestable;
					
					$element.removeAttr( "ui-tree-nestable" );
					
					
					var options = $scope[ optionsName ];
					options = $.extend({
						checkbox: attrs.checkbox == "true" ,
						accept: function(sourceNodeScope, destNodesScope, destIndex) {
							//同级拖动：sourceNodeScope.depth() == destNodesScope.depth()+1
							return false;
						}
					},options);
					
					$scope[ optionsName ] = options;
					if( !optionsName || !$scope[ optionsName ] ){
						optionsName =  optionsName || "options"
					}
					
					var tpl = element.html();
					$templateCache.put("uitree/ui-tree-nestable-content.tpl.html",tpl);
					
					var tplindex = new Date().getTime()+"-"+Math.random()+"-";
					
					var nodesRendererTemplate = tplindex+"uitree/ui-tree-nestable-checkbox.tpl.html";
					
					createTemplate( $templateCache,tpl,options,nodesRendererTemplate );
					
					var drag = attrs.drag == "" ? "" : "data-nodrag";
					
					
					var tree = '<div ui-tree="'+optionsName+'" class="dd" '+drag+'>\
					<ol ui-tree-nodes ng-model="'+modelName+'" class="dd-list">\
					<li ng-repeat="node in '+modelName+'" ui-tree-node ng-include="\''+nodesRendererTemplate+'\'"></li>\
					</ol>\
					</div>';
					
					element.html( tree );
					
					$compile( element )( $scope );
					
					var promise = $interval(function(){
						var checkedList = $element.find("input[checked='checked']");
						if( checkedList.length > 0 ){
							$interval.cancel( promise );
							for( var i = checkedList.length ; i > 0; i-- ){
								var $this = checkedList[i-1];
								var $modelValue = angular.element( $this ).scope().$modelValue;
								if( $modelValue.children && $modelValue.children.length > 0 ){
									continue;
								}
								checkedProcess( $this,true );
							}
						}
					},300);
				};
			}
		};
		
		function createTemplate($templateCache,tpl,options,nodesRendererTemplate){
			var itemConfig = $.extend( {},{
				childrenName:"children",
				checkedName:"checked"
			}, options.itemConfig );
			if( options.checkbox ){
				$templateCache.put(nodesRendererTemplate,
						'<div class="dd-item" ui-tree-handle >\
						<a href="javascript:void(0)" ng-if="node.children && node.children.length > 0" data-nodrag ng-click="toggle(this)" style="cursor: pointer;">\
						<span\
						class="glyphicon"\
						ng-class="{\
						\'fa fa-plus\': collapsed,\
						\'fa fa-minus\': !collapsed\
						}"></span>\
						</a>\
						<div ng-click="checkNode( $event )" data-nodrag class="checkbox" style="display:inline">\
						<label>\
						<input type="checkbox" ng-checked="node.checked"/>\
						<span class="text"></span>\
						</label>\
						</div>\
						'+tpl+'\
						</div>\
						<ol class="dd-list" ui-tree-nodes ng-model="node.children" ng-class="{hidden: collapsed}">\
						<li ng-repeat="node in node.children" ui-tree-node ng-include="\''+nodesRendererTemplate+'\'">\
						</li>\
				</ol>');
			}else{
				$templateCache.put(nodesRendererTemplate,
						'<div class="dd-item" ui-tree-handle >\
						<a href="javascript:void(0)" ng-if="node.children && node.children.length > 0" data-nodrag ng-click="toggle(this)" style="cursor: pointer;">\
						<span\
						class="glyphicon"\
						ng-class="{\
						\'fa fa-plus\': collapsed,\
						\'fa fa-minus\': !collapsed\
						}"></span>\
						</a>\
						'+tpl+'\
						</div>\
						<ol class="dd-list" ui-tree-nodes ng-model="node.children" ng-class="{hidden: collapsed}">\
						<li ng-repeat="node in node.children" ui-tree-node ng-include="\''+nodesRendererTemplate+'\'">\
						</li>\
				</ol>');
			}
			
		}
		
		function checkedProcess( target,checked ){
			var $this = $( target );
			//$nodeScope,$parentNodeScope,$modelValue,$childNodesScope
			var scope = angular.element( $this ).scope();
			var node = scope.$modelValue;
			node.checked = checked;
			if( node.checked ){
				var $parents = $this.parents(".dd-list").prev("div");
				$parents.each(function(i){
						angular.element( this ).scope().$modelValue.checked=true;
				});
			}else{
				var $parents = $this.parents(".dd-list").prev("div");
				$parents.each(function(i){
					var parent = angular.element( this ).scope().$modelValue;
					var children = parent.children;
					var isChecked = false;
					for( var i = 0 ; i < children.length; i++ ){
						if( children[i].checked ){
							isChecked = true;
							break
						}
					}
					parent.checked = isChecked;
				});
			}
			if( node.children ){
				node.children.forEach(function f( child ){
					child.checked = node.checked;
					if( child.children ){
						child.children.forEach( f );
					}
				});
			}
		}
		
		function buildHtml( model,modelName,tpl,options ){
			
			var html = "";
			var optionsHtml = ""
		
			if( options.checkbox ){
				optionsHtml = '<div data-nodrag class="checkbox" style="display:inline">\
						             <label>\
						                 <input type="checkbox" ng-checked="$item.checked"/>\
						                 <span class="text"></span>\
						             </label>\
						     	</div>';
			}
			for( var i = 0 ; i < model.length; i++ ){
				html = html + '<li ui-tree-node ng-repeat="node in '+modelName+' track by $index" ng-if="$index == '+i+'">\
								<div class="dd-item" ui-tree-handle>\
							    <a href="javascript:void(0)" ng-if="node.children && node.children.length > 0" data-nodrag ng-click="toggle(this)" style="cursor: pointer;">\
									<span\
							        class="glyphicon"\
							        ng-class="{\
							          \'fa fa-plus\': collapsed,\
							          \'fa fa-minus\': !collapsed\
							        }"></span>\
								</a>'
									+optionsHtml+tpl+
							  '</div>\
							  <ol class="dd-list" ui-tree-nodes ng-model="node.children" ng-class="{hidden: collapsed}">';
				if( model[i].children && model[i].children.length > 0 ){
					html = html +buildHtml( model[i].children,"node.children",tpl,options );
				}
				html =  html + '</ol></li>';
			}
			return html;
		}
		
//		var $scope = element.scope();
//		var model = $scope[attrs.ngModel];
//		var options = $scope[attrs.uiTreeNestable];
//		var tpl = element.html();
		//$templateCache.put('templateId.html', '<a>This is the content of the template</a>');

	}]);
})(window, document, window.angular);
