(function(window,document){
"use strict"
angular.module("jqgrid",[]).constant('templateformConfig', {
	
}).config(function() {
	$.extend(true,$.jgrid.defaults,{
		//启用angular自动编译功能，如果开启导致点击事件多次触发，请构建表格时设置为false并自行处理编译
		autoCompile:true,
		//开启自动分页参数处理（将表格分页数据结构转easyui表格数据结构）
		autoPage:true,
		datatype: "json",
		styleUI : 'Bootstrap',
		mtype: "post",
		width:"100%",
		autowidth:true,
		hidegrid:false,
		rownumbers:true,
		viewrecords:true,
		multiboxonly:false,
		forceFit:true,
		//不需要分页是传-1
		rowNum : 10,
		rowList : [ 10, 20, 30,40,50 ],
		emptyrecords: '没有可以浏览的记录', 
		prmNames: {
			page:"currentPage",
			rows:"pageSize", 
			sort: null,
			order: null, 
			search:null, 
			nd:null, 
			id:"id",
			oper:"oper",
			editoper:"edit",
			addoper:"add",
			deloper:"del", 
			subgridid:"id", 
			npage: null, 
			totalrows:"total"
		},
//		gridComplete:function(){
//			var  grid = this;
//			try{
//				angular.element( this ).injector().invoke(function( $compile ) {
//					$compile( $( grid ).find( ".td-compile" ) )( angular.element( grid ).scope() );
//				});
//			}catch (e) {
//				console.error( e );
//			}
//			window.onresize=function(){
//				$( grid ).jqGrid('setGridWidth', $( grid ).parents( ".ui-jqgrid:eq(0)" ).width() );
//			}
//		},
//		beforeProcessing:function(data, status, xhr){
//			if( status != "success" || data.success == false || (
//					!(data instanceof Array) && !data.rows
//			)){
//				data = {
//						rows:[],
//						total:1
//				}
//			}
//			if( data instanceof Array ){
//				data = {
//						rows:data,
//						total:data.length
//				}
//			}
//			var rowNum = $( this ).jqGrid("getGridParam").rowNum;
//			var page = $( this ).jqGrid("getGridParam").page;
//			var total = data.total;
//			var rows = data.rows;
//			if( total && rowNum ){
//				total =  Math.floor(total/rowNum) + ( total % rowNum > 0 ? 1 : 0 )
//				data.total  = total;
//			}else{
//				data.total  = 1;
//			}
//			for( var i = 0; i < rows.length; i++ ){
//				var id = null;
//				if( rowNum && page ){
//					id = rowNum * ( page - 1 ) + i;
//				}
//				id = i;
//				data.rows[i].rowId = id;
//				data.rows[i] = {
//						id:id,
//						cell:data.rows[i]
//				}
//			}
//		}
	});
});
})(window,document)