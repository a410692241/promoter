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
		$scope.identity=identity;
		$scope.promoterType=promoterType;
		$scope.search={
			userId:"",
			nickname:"",
			sex:"",
			mobile:"",
			userType:""
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

	/** 列表查询 */
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
						}else if(cellvalue == "MEMBER"){
							return "会员";
						}else if(cellvalue == "SPOKESMAN"){
							return "代言人";
						}else if(cellvalue == "ORDER_MAN"){
							return "家庭服务师";
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
						var authenticationType = rowObject.authenticationType;
						var opts = "";
						opts = opts + "<a href='javascript:void(0);' ng-click='show( "+rowObject.applyId+" )' class='btn btn-primary shiny fa fa-eye btn-sm td-compile'>详情</a> ";
						if (status === "WAIT_AUDIT" ) {
							opts = opts + "<a href='javascript:void(0);' ng-click='audit( " + rowObject._rowId + " )' class='btn btn-darkorange fa fa-edit btn-sm td-compile'>审核</a> ";
						}
						if(authenticationType=== 'ORDER_MAN'){
							opts = opts + "<a href='javascript:void(0);' ng-click='edit( " + rowObject.applyId + " )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
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

	/** 禁用或者启用 */
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

	/** 新增，编辑 */
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
			// 获取次id的用户信息
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

	/** 保存 */
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

	/** 查看 */
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

	// /** 查看 */
	// function edit( id ){
	// 	var url = urls.ms + "/user/authentication/edit.do?";
	// 	if( id ){
	// 		url = url + $.param( {applyId:id} );
	// 	}
	// 	templateform.open({
	// 		title:"用户信息显示",
	// 		buttons:[],
	// 		backdrop: true,
	// 		keyboard: true,
	// 		url:url
	// 	});
	// }
	function edit(applyId) {
		console.log(applyId);
		// 获取商品信息
		$.ajax({
			url:urls.ms+"/user/authentication/edit.do",
			data: {
				applyId: applyId
			},
			dataType: "json",
			type: "post",
			success: function (data) {
				openEdit(data.data);
			}
		});
	}

	var identity;

	function identity() {
		identity = $("#identity").val();
		console.log(identity);
	}

	function promoterType(){
		var promoterType = $("#promoterType").val();
		if(!(promoterType =="" || promoterType == null)){
			$.ajax({
				type : 'post',
				url : urls.ms + "/promoter/promoter/getPromoterListByType.do",
				data:{"promoterType":promoterType},
				dataType : 'json',
				success:function(data){
					$("#partDiv").show();
					$("#part").empty();
					for(var i in data.data){
						$("#part").append("<option value=" + data.data[i].promoterId+ ">" + data.data[i].name + "</option>");
					}
				}
			});
		}else{
			$("#partDiv").hide();
		}
	}

	/** 审核 */
	function audit(rowId){
		var rowData = $("#AuthenticationApplyList").jqGrid("getRowData",rowId);
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
						data : {"promoterId":$("#part").val(),"identity":identity,"userId":userId,"realName":realName,"mobile":mobile,"idCardFront":idCardFront,"idCardBack":idCardBack,"status":'AUDIT_SUCCESS',"applyId":applyId,"authenticationType":authenticationType,"smallCommunityId":smallCommunityId,"supermarketId":supermarketId},
						dataType : "JSON",
						success:function(data){
							identity = '';
							if(data.respCode === "S"){
								list();
								$("#correctList").trigger("reloadGrid");
								$modalInstance.close();
								toaster.success( "","操作成功",3000 );
							}else{
								list();
								$("#correctList").trigger("reloadGrid");
								$modalInstance.close();
								toaster.error( "",data.errorMsg,3000 );
							}
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
						data : {"identity":identity,"userId":userId,"realName":realName,"mobile":mobile,"idCardFront":idCardFront,"idCardBack":idCardBack,"status":'AUDIT_FAIL',"applyId":applyId,"authenticationType":authenticationType,"smallCommunityId":smallCommunityId},
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
				// 获取数据
				$scope.apply1 = {
					realName:rowData.realName,
					mobile:rowData.mobile,
					idCardFront:rowData.idCardFront,
					idCardBack:rowData.idCardBack,
					validStart:rowData.validStart,
					userId:rowData.userId,
					address:rowData.address,
					supermarketName:rowData.supermarketName,
					authenticationType:rowData.authenticationType
				}
				if(rowData.authenticationType == '家庭服务师'){
					$scope.apply1.orderMan = "true";
				}
			}
		},function( $modalInstance,data, $scope ){
		});

	}


	// 批量禁用
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

	function openEdit(data) {

		var url = urls.ms + "/jsp/user/AuthenticationEdit.jsp";
		templateform.open({
			title: "修改用户信息",
			url: url,
			scope: $scope,
			data: data,
			dataName: "data",
			onOpen: function ($modalInstance, data, $scope) {
				$scope.apply1 = {
					realName: data.realName,
					mobile: data.mobile,
					idCardBack: data.idCardBack,
					idCardFront: data.idCardFront,
					applyId: data.applyId
				}
			}
		}, function ($modalInstance, data, $scope) {
			save1($modalInstance, $scope.apply1, $scope);
		});
	}

	/** 保存 */
	function save1($modalInstance, data, $scope) {
		try {
			$('#goodsSkuInfoForm').form("submit", {
				url: urls.ms + "/user/authentication/save.do",
				onSubmit: function (param) {
					for (var key in data) {
						param[key] = data[key]
					}
				},
				success: function (datas) {
					datas = JSON.parse(datas);
					if (datas.data === "success") {
						$("#supermarketList").trigger("reloadGrid");
						$scope.$apply(function () {
							$modalInstance.close();
							toaster.success("", "操作成功", 3000);
						});
						init();
					} else if (datas.data === "nameRepeat") {
						$("#supermarketList").trigger("reloadGrid");
						$scope.$apply(function () {
							toaster.success("", "商品名称存在", 3000);
						});
					} else if (datas.data === "barcodeRepeat") {
						$("#supermarketList").trigger("reloadGrid");
						$scope.$apply(function () {
							toaster.success("", "商品条形码存在", 3000);
						});
					} else {
						$scope.$apply(function () {
							toaster.error("", datas.msg, 3000);
						});
					}
				}
			});

			/*if( !goodsSku.produceDate ){
                toaster.error( "", "请填写生产日期", 3000 );
                return;
            }
            if( !goodsSku.validDate ){
                toaster.error( "", "请填写有效日期", 3000 );
                return;
            }
            if( !goodsSku.goodsName ){
                toaster.error( "", "请填写商品标题", 3000 );
                return;
            }
            if( !goodsSku.goodsName ){
                toaster.error( "", "请填写商品标题", 3000 );
                return;
            }*/

			/*<div ng-if="goods.goodImageFirst.firstImage == 1 || goods.goodImageFirst.firstImage == null">
                    <div  name="firstImages" style="width: 100%;" ui-imagebox ng-model="goods.goodImageFirst.imagePath"></div>
                    </div>*/
			// 处理图片
			/*angular.forEach( goods.goodsImageList, function( item, index ){
                if( item.imagePath == "resource/image/addImg.png" ){
                    item.imagePath = "";
                }
            })

            $('#goods-edit-form').form("submit",{
                url:"goods/goods/save.do",
                onSubmit: function( param ){
                    param.goods = angular.toJson( goods );
                },
                success:function(data){
                    data= data.substring(0,data.indexOf('}')+1);
                    console.log(data);
                    data = angular.fromJson( data );
                    if( data.success ){
                        $("#goodsList").trigger("reloadGrid");
                        $scope.$apply(function(){
                            $modalInstance.close();
                            toaster.success( "","操作成功",3000 );
                        });
                    }else{
                        $scope.$apply(function(){
                            toaster.error( "",data.msg,3000 );
                        });
                    }
                }
            });   */
		} catch (e) {
			console.error(e);
			toaster.error("", typeof e == "string" ? //
				e : e.msg ? //
					e.msg : "出错了", 3000);
		}
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

	// 初始化
	init();
});