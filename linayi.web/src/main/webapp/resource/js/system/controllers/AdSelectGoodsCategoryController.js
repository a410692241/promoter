'use strict';

app.controller('AdCategoryCtrl', function($scope,toaster,messager,templateform ) {
	
	function init(){
		$scope.list = list;
		$scope.categroyList = [];
		$scope.list();
	}
	
	/**列表查询*/
	function list(){
		$.ajax({
			url:urls.ms+"/goods/category/list.do",
			data:{},
			dataType:"json",
			type:"post",
			success:function( data ){
				$scope.$apply(function(){
					$scope.categroyList = [{
						name:"ROOT",
						categoryId:"",
						level:0,
						rank:1,
						children:data
					}]
				});
			}
		})
	}
    //初始化
    init();
});