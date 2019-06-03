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
		$scope.exportData = exportData;
		$scope.search={
			goodsSkuId:'',
			name:''
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
/*				supermarketGoodsId : '',
				supermarketId : '',
				name : '',
				price : '',
				correctId : ''*/
		};
	}

	/**列表查询*/
	function list(){
		var $grid = $("#GoodsDifferenceList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/goods/goods/getBackstageDifference.do",
/*			postData:$scope.search,*/
			pager : "#GoodsDifferenceListPager",
			multiselect :true,
			rownumWidth: 100,
			colModel : [
			            {name:'goodsSkuId',label:'商品ID',width:80},
			            {name:'fullName',label:'商品名称',sortable:false,width:200},
			            {name:'maxPrice',label:'最高价(单位:元)',sortable:false,
							formatter: function (cellvalue, options, rowObject) {
								return cellvalue / 100;
							},},
						{name:'minPrice',label:'最低价(单位:元)',sortable:false,
							formatter: function (cellvalue, options, rowObject) {
								return cellvalue / 100;
							},},
						{name:'spreadRate',label:'价差率(%)',sortable:false,
							formatter: function (cellvalue, options, rowObject) {
								return   cellvalue>100? "<span style='color: red;text-decoration:none;font-weight: bold;'>"+cellvalue +"%"+"</span>":"<span style='font-weight: bold;' >"+cellvalue+"%"+"</a>";
							}},
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
							$("#GoodsSku1List").trigger("reloadGrid");
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
						$("#GoodsSku1List").trigger("reloadGrid");
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
    	var rowData = $("#GoodsSku1List").jqGrid("getRowData",id);
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
	
	/**审核*/
    function audit(rowId){
    	console.log(rowId)
    	var rowData = $("#GoodsSku1List").jqGrid("getRowData",rowId);
    	console.log(rowData);
    	var realName = rowData.realName;
    	var mobile = rowData.mobile;
    	var idCardFront = rowData.idCardFront;
    	var idCardBack = rowData.idCardBack;
    	var userId = rowData.userId;
    	var applyId = rowData.applyId;
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
						data : {"userId":userId,"realName":realName,"mobile":mobile,"idCardFront":idCardFront,"idCardBack":idCardBack,"status":'AUDIT_SUCCESS',"applyId":applyId},
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
						data : {"userId":userId,"realName":realName,"mobile":mobile,"idCardFront":idCardFront,"idCardBack":idCardBack,"status":'AUDIT_FAIL',"applyId":applyId},
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
						userId:rowData.userId
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
						$("#GoodsSku1List").trigger("reloadGrid");
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
							$("#GoodsSku1List").trigger("reloadGrid");
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
							$("#GoodsSku1List").trigger("reloadGrid");
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


	//导出数据
	function exportData() {
		var goodsSkuId = $scope.search.goodsSkuId;
		var name = $scope.search.name;
		var data = '';
		if (goodsSkuId === undefined || goodsSkuId == '') {
			goodsSkuId = null;
		} else {
			data += '&goodsSkuId=' + goodsSkuId;
		}
		if (name === undefined || name == '') {
			name = null;
		} else {
			data += '&name=' + name;
		}

		location.href = urls.ms + "/goods/goods/exportDifferenceRanking.do?" + data.replace("&", "");
		toaster.success("", "导出成功!", 1000);


	}

	//初始化
	init();
});