'use strict';

app.controller('selectUserCtrl', function($scope,toaster,selectUserService,messager,templateform,$q) {

	function init(){
		$scope.show = show;
		$scope.edit = edit;
		$scope.remove = remove;
		$scope.list = list;
		$scope.resetPWD = resetPWD;
		$scope.cancelFrozen = cancelFrozen;
		$scope.fenpeiRole = fenpeiRole;
		$scope.fenpeiPrivilege = fenpeiPrivilege;
		$scope.roleManage = roleManage;
		$scope.addRoleView = addRoleView;
		$scope.deleteRole = deleteRole;
		$scope.search={
			userName:"",
			realName:"",
			mobile:"",
			status:""
		}
		$scope.list();
	}

	//列表查询
	function list(){
		var $grid = $("#selectUserList");
		if( $grid[0].grid ){
			$grid.jqGrid('setGridParam', {
				page : 1,
				postData:$scope.search,
			}).trigger("reloadGrid");
			return;
		}
		$grid.jqGrid({
			url : urls.ms+"/account/list.do",
			postData:$scope.search,
			pager : "#selectUserPager",
			colModel : [
				{name:'userName',label:'账号名称',sortable:false},
				{name:'userType',label:'类型',sortable:false},
				{name:'userId',label:'编码',sortable:false},
				/*{name:'realName',label:'真实姓名',sortable:false},*/
				/*{name:'qq',label:'QQ',sortable:false},*/
				/*{name:'email',label:'邮箱',sortable:false},*/
				{name:'status',label:'用户状态',sortable:false,formatter:function( cellvalue, options, rowObject ){
						if(cellvalue==='ENABLED'){
							return "有效";
						}else if(cellvalue==='DISABLED'){
							return "无效";
						}				}},
				{name:'mobile',label:'联系电话',sortable:false},
				{label:"操作",name:"opt",width:450,sortable:false,formatter:function(cellvalue, options, rowObject){
						var optString="";
						optString+=" <a href='javascript:void(0)' ng-click='edit("+rowObject.accountId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>[修改]</a>"+
							" <a href='javascript:void(0)' ng-click='fenpeiRole("+rowObject.accountId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>[分配角色]</a>" /*+
							" <a href='javascript:void(0)' ng-click='fenpeiPrivilege("+rowObject.accountId+")' class='btn btn-primary fa fa-edit btn-sm td-compile'>[分配权限]</a>"*/;
						if(rowObject.accountId != '10000000'){
							if(rowObject.status == 'DISABLED'){
								optString += " <a href='javascript:void(0)' ng-click='cancelFrozen("+rowObject.accountId+",1000)' class='btn btn-primary fa fa-eye btn-sm td-compile'>[启用]</a>";
							}else{
								optString += " <a href='javascript:void(0)' ng-click='cancelFrozen("+rowObject.accountId+",1001)' class='btn btn-primary fa fa-eye btn-sm td-compile'>[禁用]</a>";
							}
							optString += " <a href='javascript:void(0)' ng-click='resetPWD("+rowObject.accountId+")' class='btn btn-primary fa fa-undo btn-sm td-compile'>[密码重置]</a>";
							optString += " <a href='javascript:void(0)' ng-click='remove("+rowObject.accountId+")' class='btn btn-primary fa fa-remove btn-sm td-compile'>[删除]</a>";
						}
						return optString;
					}}
			],
			onCellSelect:function(	rowid,iCol, cellcontent,e){
				var account = $(this).jqGrid("getRowData",rowid);
				showUserContent( account.accountId );
			}
		});
	}

	function showUserContent( id ){
		var url = urls.ms + "/jsp/system/userContent.jsp?";
		if( id ){
			url = url + $.param( {accountId:id} );
		}
		$scope.userRolePrivilege=[];
		var roleOptions={
			data:{
				userId:id
			},
			success:function( data ){
				$scope.$apply(function(){
					$scope.userRolePrivilege=data;
				});
			}
		}
		selectUserService.listUserRolePrivilege( roleOptions );

		$scope.userPrivilege=[];
		var privilegeOptions={
			data:{
				userId:id
			},
			success:function( data ){
				$scope.$apply(function(){
					$scope.userPrivilege=data;
				});
			}
		}
		selectUserService.listUserPrivilege( privilegeOptions );

		var modalInstance = templateform.open({
			title:"员工权限信息",
			buttons:[],
			url:url,
			backdrop: true,
			keyboard: true,
			scope:$scope,
		});
	}



	//分配角色
	function fenpeiRole( id,roleId ){
		var url = urls.ms + "/jsp/system/selectRole.jsp?";
		if( id ){
			url = url + $.param( {accountId:id} );
		}
		if( roleId ){
			url = url + $.param( {roleId:localStorage.getItem("roleId")} );
		}

		templateform.open({
			title:"分配角色",
			url:url,
			size:"xs"
		},function( modalInstance,data,scope ){
			try{
				var rowIdList = $("#selectRoleList").jqGrid("getGridParam", "selarrrow");
				var roleIdList = [];
				for( var i = 0; i < rowIdList.length; i++ ){
					roleIdList[i] = $("#selectRoleList").jqGrid("getRowData",rowIdList[i]).roleId - 0;
				}
				localStorage.getItem("roleId");
				selectUserService.fenpeiRole( {
					data:{
						accountId:id,
						roleId: localStorage.getItem("roleId")
					},
					success:function( data ){
						if( data.respCode==="S" ){
							$scope.$apply(function(){
								toaster.success( "",data.msg,3000 );
							});
							modalInstance.close();
						}else{
							$scope.$apply(function(){
								toaster.error( "",data.msg,3000 );
							});
						}
					}
				} );
			}catch (e) {
				console.error( e );
				toaster.error( "",e.msg ? e.msg : "出错了",3000 );
			}
		});
	}

	//分配权限
	function fenpeiPrivilege( id ){
		//获取权限树
		selectUserService.getPrivilegesList({
			data:{
				userId:id
			},
			success:function(data){
				$scope.privilageList = data;
			}
		});

		var url = urls.ms + "/jsp/system/selectUserPrivilege.jsp?";
		if( id ){
			url = url + $.param( {accountId:id} );
		}
		templateform.open({
			title:"分配权限",
			url:url,
			size:"lg",
			scope:$scope,
			onOpen:function( modalInstance,data,scope ){

			}
		},function( modalInstance,data,scope ){
			selectUserService.addUserPrivilege({
				data:{
					userId:id,
					privilageList:scope.privilageList
				},
				success:function( data ){
					if( data.success ){
						$scope.$apply(function(){
							toaster.success( "",data.msg,3000 );
						});
						modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			});
		});
	}

	/*//分配权限
	function fenpeiPrivilege( id ){
		//获取权限树
		selectUserService.getPrivilegesList({
			data:{
				userId:id
			},
			success:function(data){
				$scope.privilageList = data;
			}
		});

		var url = urls.ms + "/jsp/system/selectUserPrivilege.jsp?";
		if( id ){
			url = url + $.param( {accountId:id} );
		}
		templateform.open({
			title:"分配权限",
			url:url,
			size:"lg",
			scope:$scope,
			onOpen:function( modalInstance,data,scope ){

			}
		},function( modalInstance,data,scope ){
			selectUserService.addUserPrivilege({
				data:{
					userId:id,
					privilageList:scope.privilageList
				},
				success:function( data ){
					if( data.success ){
						$scope.$apply(function(){
							toaster.success( "",data.msg,3000 );
						});
						modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			});
		});
	}
*/
	//启用、禁用
	function cancelFrozen(id,status){
		messager.confirm("确认"+(status == 1000 ? "启用":"禁用")+"?",function( modalInstance ){
			selectUserService.cancelFrozen({
				"data":{accountId:id,status:status},
				"success":function( data ){
					if( data.respCode==="S" ){
						$("#selectUserList").trigger("reloadGrid");
						$scope.$apply(function(){
							toaster.success( "",data.msg,3000 );
						});
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
					modalInstance.close();
				}
			});
		});
	}

	//重置密码
	function resetPWD( id ){
		messager.confirm("确认重置密码？",function( modalInstance ){
			selectUserService.resetPWD({
				"data":{accountId:id},
				"success":function( data ){
					if( data.respCode==="S" ){
						$("#selectUserList").trigger("reloadGrid");
						$scope.$apply(function(){
							toaster.success( "",data.msg,3000 );
						});
						modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			});
		});
	}

	//删除
	function remove( id ){
		messager.confirm("确认删除？",function( modalInstance ){
			selectUserService.remove({
				"data":{accountId:id},
				"success":function( data ){
					if( data.respCode==="S" ){
						$("#selectUserList").trigger("reloadGrid");
						$scope.$apply(function(){
							toaster.success( "",data.msg,3000 );
						});
						modalInstance.close();
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			});
		});
	}

	//新增，编辑
	function edit( id ){
		var url = urls.ms + "/account/edit.do?";
		if( id ){
			url = url + $.param( {accountId:id} );
		}
		templateform.open({
			title:"用户信息",
			url:url
		},function( $modalInstance,data,scope ){
			try{
				selectUserService.save( {
					data:scope.selectUser,
					success:function( data ){
						if( data.respCode==="S" ){
							$("#selectUserList").trigger("reloadGrid");
							toaster.success("","操作成功！",3000);
							$modalInstance.close();
						}else{
							$scope.$apply(function(){
								toaster.error( "",data.msg,3000 );
							});
						}
					}
				} )
			}catch (e) {
				console.error( e );
				toaster.error( "",e.msg ? e.msg : "出错了",3000 );
			}
		});
	}

	//查看
	function show( id ){
		selectUserService.getById({
			data:{userId:id},
			then:function( data ){
				templateform.open({
					title:"User",
					buttons:[],
					backdrop: true,
					keyboard: true,
					data:data,
					dataName:"user",
					url:urls.ms + "/jsp/user/UserShow.jsp"
				},function( modalInstance,data ){
					selectUserService.save( {
						data:data,
						success:function( data ){
							if( data.success ){
								modalInstance.close();
								$("#selectRoleList").trigger("reloadGrid");
							}else{
								$scope.$apply(function(){
									toaster.error( "",data.msg,3000 );
								});
							}
						}
					} );
				});
			}
		});
	}

	//角色管理
	function roleManage(){
		var url = urls.ms + "/jsp/system/selectRoleManage.jsp?";
		templateform.open({
			title:"角色信息管理",
			url:url,
			scope:$scope,
			buttons:[]
		},function( modalInstance,data,scope ){

		});
	}

	/**新增角色*/
	function addRoleView( roleId,roleName ){
		$scope.role={
			roleId:roleId ? roleId : null,
			roleName:roleName
		}
		var url = "jsp/system/selectRoleManagee/addRole.html";
		templateform.open({
			title:"新增角色",
			url:url,
			scope:$scope,
			size:"xs",
			ngTemplate:true,
		},function( modalInstance,data,scope ){
			selectUserService.addRole( {
				data:$scope.role,
				success:function( data ){
					;
					if( data.success ){
						$("#selectRoleList").trigger("reloadGrid");
						$scope.$apply(function(){
							modalInstance.close();
						});
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			} );
		});
	}

	function deleteRole( id ){
		messager.confirm("确认删除？",function( modalInstance ){
			selectUserService.deleteRole({
				"data":{roleId:id},
				"success":function( data ){
					if( data.success ){
						$("#selectRoleList").trigger("reloadGrid");
						$scope.$apply(function(){
							toaster.success( "",data.msg,3000 );
							modalInstance.close();
						});
					}else{
						$scope.$apply(function(){
							toaster.error( "",data.msg,3000 );
						});
					}
				}
			});
		});
	}

	//初始化
	init();
});