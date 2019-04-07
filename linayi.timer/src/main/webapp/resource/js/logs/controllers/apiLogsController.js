'use strict';

app.controller('apiLogsCtrl', function($scope,toaster,apiLogsService,messager,templateform ) {
	
	function init(){
		$scope.show = show;
		$scope.list = list;
		$scope.search={
				requestMethod:"",
				requestTime:"",
		}
		$scope.list();
	}
	
	//列表查询
	function list(){
		var $grid = $("#apiLogsList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/logs/apiLogs/list.do",
			postData:$scope.search,
			pager : "#apiLogsPager",
			colModel : [
				{name:'logId',label:'主键',sortable:false,hidden:true}, 
				{name:'requestUrl',label:'请求url',sortable:false}, 
				{name:'requestTime',label:'请求时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}}, 
				{name:'agreementParams',label:'请求参数',sortable:false}, 
				{name:'requestHead',label:'请求头',sortable:false}, 
				{name:'requestBody',label:'请求体',sortable:false}, 
				{name:'requestMethod',label:'请求方法',sortable:false}, 
				{name:'requestIp',label:'请求ip',sortable:false}, 
				{name:'errorCode',label:'错误码',sortable:false}, 
				{name:'errorMsg',label:'错误消息',sortable:false}, 
				{name:'responseTime',label:'响应时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
					return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
				}}, 
				{name:'responseBody',label:'响应内容',sortable:false}, 
				{name:'responseCode',label:'响应码',sortable:false}, 
				{name:'msgId',label:'消息id',sortable:false}, 
				{name:'exception',label:'异常',sortable:false}, 
	             {label:"操作",name:"opt",sortable:false,formatter:function(cellvalue, options, rowObject){
		           	return " <a href='javascript:void(0)' ng-click='show("+rowObject.logId+")' class='btn btn-primary fa fa-eye btn-sm td-compile'>[查看]</a>";
	             }}
			]
		});
	}

    //查看
    function show( id ){
		templateform.open({
			title:"接口日志信息",
			buttons:[],
			backdrop: true,
		    keyboard: true,
			url:urls.ms + "/logs/apiLogs/show.do?logId="+id
		},function( modalInstance,data ){});
    }
    
    
    //初始化
    init();
});