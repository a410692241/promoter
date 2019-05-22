'use strict';

app.controller('userCtrl', function($scope,toaster,userService,messager,templateform ) {

	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.list = list;
		$scope.auth = auth;
		$scope.audit = audit;
		$scope.batchDisable = batchDisable;
		$scope.disable = disable;
		$scope.search={
				userId:"",
				nickname:"",
				sex:"",
				mobile:"",
				userType:"",
		};
		$scope.checkUserId = new Set();
		$scope.list();
		$scope.user = {
				userId : "",
				nickname : '',
				account : '',
				userType : '',
				email : '',
				headImage : '',
				sexName : '',
				address : '',
				age : '',
				authenticationRemark :'',
				mobile:''
		};
	}

	/**列表查询*/
	function list(){
		var $grid = $("#AuthenticationApplyList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/user/authentication/list.do",
			postData:$scope.search,
			pager : "#AuthenticationApplyPager",
			multiselect :true,
			colModel : [
			            {name:'applyId',label:'记录id'}, 
			            {name:'userId',label:'用户id'}, 
			            {name:'realName',label:'真实姓名',sortable:false}, 
			            {name:'mobile',label:'联系电话',sortable:false}, 
			            {name:'status',label:'审核状态',sortable:false,width:100,formatter:function(cellvalue, options, rowObject){
			            	if(cellvalue == "WAIT_AUDIT"){
			            		return "待审核";
			            	}else if(cellvalue == "AUDIT_SUCCESS"){
			            		return "审核通过";
			            	}else if(cellvalue == "AUDIT_FAIL"){
			            		return "审核不通过";
			            	}
			            	return ""; 
			            }},
			            {name:'authenticationType',label:'认证类型',sortable:false,formatter:function(cellvalue, options, rowObject){
			            	if(cellvalue == "SHARER"){
			            		return "价格分享员";
			            	}else if(cellvalue == "PROCURER"){
			            		return "采买员";
			            	}else if(cellvalue == "DELIVERER"){
			            		return "配送员";
			            	}else if(cellvalue == "SPOKESMAN"){
								return "代言人";
							}else if(cellvalue == "ORDER_MAN"){
								return "下单员";
							}
			            	return ""; 
			            }},
			            {name:'createTime',label:'创建时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
			            	return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
			            }},
			            {name:'updateTime',label:'修改时间',sortable:false,formatter:function( cellvalue, options, rowObject ){
			            	return cellvalue ? new Date( cellvalue ).format("yyyy-MM-dd hh:mm:ss") : "";
			            }},
						{name:'idCardFront',label:'身份证正面',sortable:false,hidden:true},
						{name:'idCardBack',label:'身份证反面',sortable:false,hidden:true},
						{name:'address',label:'详细地址',sortable:false,hidden:true},
						{name:'supermarketName',label:'超市名称',sortable:false,hidden:true},
						{name:'supermarketId',label:'超市Id',sortable:false,hidden:true},
						{name:'smallCommunityId',label:'小区id',sortable:false,hidden:true},
			            {label:"操作",name:"opt",width:300,sortable:false,formatter:function(cellvalue, options, rowObject){
			            	var status = rowObject.status;
			            	var opts = "";
			            	opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.applyId+" )' class='btn btn-primary shiny fa fa-eye btn-sm td-compile'>详情</a> ";
			            	if (status === "WAIT_AUDIT" ) {
								opts = opts + "<a href='javascript:void(0);' ng-click='audit( " + rowObject._rowId + " )' class='btn btn-darkorange fa fa-edit btn-sm td-compile'>审核</a> ";
							}
			            	return opts;
			            }}
			            ],
			            onSelectRow:function(rowId,status){
			            	var rows = $( this ).jqGrid( "getRowData" , rowId);
			            	if(status){
			            		$scope.checkUserId.add(rows.userId);
			            	}else{
			            		$scope.checkUserId.deletes(rows.userId);
			            	}
			            }
		});
	}

	/**禁用或者启用*/
	function disable( id , valid ){
		var text = valid == 10 ? "启用" : "禁用"
			messager.confirm("确认"+ text + "?",function( $modalInstance ){
				$.ajax({
					url : urls.ms + '/user/user/disable.do',
					type : "post",
					data : {"userId":id,"valid":valid},
					dataType : "JSON",
					success : function(data){
						if( data.success ){
							$("#AuthenticationApplyList").trigger("reloadGrid");
							$scope.$apply(function(){
								$modalInstance.close();
								toaster.success( "","操作成功",3000 );
							});
						}
					}
				})

			});
	}

	/**新增，编辑*/
	function edit( id ){
		var title = '新增';
		var url = urls.ms + "/jsp/user/UserEdit.do";
		$scope.user = {
				nickname : "",
				account : "",	
				userType : "",
				headImage : "",
				email : "",
				mobile : ""
		}
		if( id ){
			title = '编辑'
				//获取次id的用户信息
				$.ajax({
					url : urls.ms + "/user/user/get.do",
					async : false,
					type : "post",
					data : {"userId":id},
					dataType : "JSON",
					success : function(data){
						console.log(data)
						var user = data.user;
						var userType = data.userType;
						$scope.user = {
								userId : user.userId,
								nickname : user.nickname,
								account : user.account,
								userType : userType,
								headImage : user.headImage,
								email : user.email,
								mobile : user.mobile
						}
					}
				})
		}
		templateform.open({
			title:title + "账号信息",
			url:url,
			scope:$scope,
			data:$scope.user,
			onOpen:function( $modalInstance, data ,$scope){
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,data, $scope );
		});
	}

	/**保存*/
	function save( $modalInstance,data, $scope ){
		try{
			console.log(data)
			$('#userInfoForm').form("submit",{   
				url:urls.ms + "/user/user/save.do",
				onSubmit: function( param ){   
					for(var key in data){
						param[key] = data[key]
					}
					param.userType = data.userType.code
				},   
				success:function(data){   
					data = angular.fromJson( data );
					if( data.success ){
						$("#AuthenticationApplyList").trigger("reloadGrid");
						$scope.$apply(function(){
							list();
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						});
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}   
			});
		}catch (e) {
			console.error( e );
			toaster.error( "",typeof e == "string" ? //
					e : e.msg ? //
							e.msg : "出错了",3000 );
		}
	}

	/**查看*/
	function show( id ){
    	console.log(id)
    	var rowData = $("#AuthenticationApplyList").jqGrid("getRowData",id);
    	console.log(rowData);
		var url = urls.ms + "/user/authentication/show.do?";
		if( id ){
			url = url + $.param( {applyId:id} );
		}
		templateform.open({
			title:"用户信息显示",
			buttons:[],
			backdrop: true,
			keyboard: true,
			url:url
		});
	}
	
	/**查看*/
	function edit( id ){
		var url = urls.ms + "/user/authentication/edit.do?";
		if( id ){
			url = url + $.param( {applyId:id} );
		}
		templateform.open({
			title:"用户信息显示",
			buttons:[],
			backdrop: true,
			keyboard: true,
			url:url
		});
	}
	    	
	var bindingType;
	var identity;
	
    function bindingType() {
    	bindingType = $("#bindingType").val();
	}
    
    function identity() {
    	identity = $("#identity").val();
	}

	/**审核*/
    function audit(rowId){
    	
    	console.log(rowId)
    	var rowData = $("#AuthenticationApplyList").jqGrid("getRowData",rowId);
    	console.log(rowData);
    	var realName = rowData.realName;
    	var mobile = rowData.mobile;
    	var idCardFront = rowData.idCardFront;
    	var idCardBack = rowData.idCardBack;
    	var userId = rowData.userId;
    	var applyId = rowData.applyId;
    	var authenticationType = rowData.authenticationType;
    	var smallCommunityId = rowData.smallCommunityId;
    	var address = rowData.address;
    	var supermarketName = rowData.supermarketName;
    	var supermarketId = rowData.supermarketId;
    	var url = urls.ms + "/jsp/user/AuthenticationApplyAudit.jsp";
    	templateform.open({
			title:"分享审核",
			url:url,
			scope:$scope,
			buttons:[{
				text:"审核通过",
				iconCls:'fa fa-check',
				handle:function( $modalInstance, data ,$scope ){
					$.ajax({
						url : urls.ms + "/user/authentication/authenticationAudit.do",
						async : false,
						type : "post",
						data : {"identity":identity,"userId":userId,"realName":realName,"mobile":mobile,"idCardFront":idCardFront,"idCardBack":idCardBack,"status":'AUDIT_SUCCESS',"applyId":applyId,"authenticationType":authenticationType,"smallCommunityId":smallCommunityId,"supermarketId":supermarketId},
						dataType : "JSON",
						success:function(data){
							list();
							$("#correctList").trigger("reloadGrid");
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						}
					})
				}
			},{
				text:"不予通过",
				iconCls:'fa fa-times',
				handle:function( $modalInstance, data ,$scope ){
					$.ajax({
						url : urls.ms + "/user/authentication/authenticationAudit.do",
						async : false,
						type : "post",
						data : {"userId":userId,"realName":realName,"mobile":mobile,"idCardFront":idCardFront,"idCardBack":idCardBack,"status":'AUDIT_FAIL',"applyId":applyId,"authenticationType":authenticationType,"smallCommunityId":smallCommunityId},
						dataType : "JSON",
						success:function(data){
							list();
							$("#correctList").trigger("reloadGrid");
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						}
					})
				}
			}],
			onOpen:function( $modalInstance, data ,$scope){
				//获取数据
				$scope.apply1 = {
				    	realName:rowData.realName,
				    	mobile:rowData.mobile,
				    	idCardFront:rowData.idCardFront,
				    	idCardBack:rowData.idCardBack,
						validStart:rowData.validStart,
						userId:rowData.userId,
						address:rowData.address,
						supermarketName:rowData.supermarketName
				}
			}
		},function( $modalInstance,data, $scope ){
		});
    	
    }
    
	
	//批量禁用
	function batchDisable(){
		messager.confirm("确认禁用 ?",function( $modalInstance ){
			var s = $scope.checkUserId;
			var size = s.size;
			if(size == 0){
				toaster.error( "","请选择需要操作的用户",3000 );
			}else{
				var a = new Array();
				for(var x in s){
					a.push(x)
				}
				var userIdListStr = a.join("-");
				$modalInstance.close();
				$.ajax({
					url:urls.ms + "/user/user/batchDisable.do?",
					data: { "userIdListStr": userIdListStr} ,
					type:"post",
					dataType:"json",
					success:function(data){
						$("#AuthenticationApplyList").trigger("reloadGrid");
						$scope.$apply(function(){
							toaster.success( "","操作成功",3000 );
						});
					}
				})
			}
		})
	}

	function auth(id){
		var url = urls.ms + "/jsp/user/UserAuth.jsp";
		templateform.open({
			title:"用户认证",
			url:url,
			scope:$scope,
			buttons:[{
				text:"认证通过",
				iconCls:'fa fa-check',
				handle:function( $modalInstance, data ,$scope ){
					$.ajax({
						url : urls.ms + "/user/user/auth.do",
						async : false,
						type : "post",
						data : {"userId":id,"authentication":40},
						dataType : "JSON",
						success:function(data){
							$("#AuthenticationApplyList").trigger("reloadGrid");
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						}
					})
				}
			},{
				text:"不予通过",
				iconCls:'fa fa-times',
				handle:function( $modalInstance, data ,$scope ){
					$.ajax({
						url : urls.ms + "/user/user/auth.do",
						async : false,
						type : "post",
						data : {"userId":id,"authentication":30},
						dataType : "JSON",
						success:function(data){
							$("#AuthenticationApplyList").trigger("reloadGrid");
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						}
					})
				}
			}],
			onOpen:function( $modalInstance, data ,$scope){
				$.ajax({
					url : urls.ms + "/user/user/get.do",
					async : false,
					type : "post",
					data : {"userId":id},
					dataType : "JSON",
					success : function(data){
						console.log(data)
						var user = data.user;
						$scope.user = {
								userId : user.userId,
								nickname : user.nickname,
								account : user.account,
								headImage : user.headImage,
								age : user.age,
								sexName : user.sexName,
								address : user.address,
								authenticationRemark : user.authenticationRemark
						}
					}
				})
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,data, $scope );
		});

	}

	//初始化
	init();
});