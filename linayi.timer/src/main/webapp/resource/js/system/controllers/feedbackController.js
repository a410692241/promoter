'use strict';

app.controller('feedbackCtrl', function($scope,toaster,feedbackService,messager,templateform,$interval,$timeout,$state ) {
	 function init(){
		$scope.search={
			problemType:"",
			submitTimeStart:"",
			submitTimeEnd:""
		}
		$scope.feedbackCtrl={
			list:list
		}
		list();
	 }
     
	 function list(){
		 var $grid = $("#feedbackList");
			if( $grid[0].grid ){
				$grid.jqGrid('setGridParam', {
					page : 1,
					postData:$scope.search,
				}).trigger("reloadGrid");
				return;
			}
			$grid.jqGrid({
				url : urls.ms+"/feedBack/feedBackList.do",
				postData:$scope.search,
				pager : "#feedbackPager",
				colModel : [
					{name:'problemTypeCN',label:'问题类型',sortable:false}, 
					{name:'content',label:'反馈内容',sortable:false}, 
					{name:'contact',label:'联系方式(手机)',sortable:false}, 
					{name:'submitTime',label:'提交时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
						return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
					}}
				]
			});
	 }
	 
     //初始化
     init();
});