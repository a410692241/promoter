'use strict';

app.controller('categoryCtrl', function($scope, toaster, categoryService,
		messager, templateform) {

	function init() {
		$scope.show = show;
		$scope.edit = edit;
		$scope.add = add;
		$scope.removes = removes;
		$scope.list = list;
		$scope.expandAll = function() {
			$scope.$broadcast('angular-ui-tree:expand-all');
		}
		$scope.collapseAll = function() {
			$scope.$broadcast('angular-ui-tree:collapse-all');
		}
		
		$scope.search = {
			categoryId : "",
			name : "",
			code : "",
			parentCode : "",
			logo : "",
			rank : "",
		}
		
		$scope.categroyList = [];
		$scope.list();

	}

	/** 列表查询 */
	function list() {
		$.ajax({
			url : urls.ms + "/goods/category/list.do",
			data : {},
			dataType : "json",
			type : "post",
			success : function(data) {
				$scope.$apply(function() {
					$scope.categroyList = [ {
						name : "ROOT",
						categoryId : "",
						level : 0,
						rank : 1,
						children : data
					} ]
				});

			}
		})
	}
	var t = "";
	function getCategoryIdList(node) {
		if (node != null) {
			t += node.categoryId + ',';
		}
		var childrens = node.children;
		if (childrens.length == 0) {
			return t;
		} else {
			for ( var i in childrens) {
				getCategoryIdList(childrens[i]);
			}
		}
		return t;
	}

	/** 删除 */
	function removes($event, node) {
		t = '';
		var param = getCategoryIdList(node)
		messager.confirm("确认删除？", function($modalInstance) {
			categoryService.remove({
				"data" : {
					"categoryIdList" : param
				},
				"success" : function(data) {
					animate($event);
					if (data.respCode === "S") {
						$("#categoryList").trigger("reloadGrid");
						$scope.$apply(function() {
							$scope.list();
							$modalInstance.close();
							toaster.success("", data.data);
						});

					} else {
						$scope.$apply(function() {
							$modalInstance.close();
							toaster.error("", data.errorMsg);
						});
					}
				}
			});
		});
	}

	/** 编辑 */
	function edit($event, node, type) {
		var category = {};
		// 编辑
		category = angular.copy(node);
		
		var url = urls.ms + "/goods/category/updateCatergoy.do?categoryId="+category.categoryId;
		console.log(category);
		templateform.open({
			title : "类别信息",
			url : url,
			scope : $scope,
			dataName : "category",
			onOpen : function($modalInstance, data, $scope) {

			}
		}, function($modalInstance, data, $scope) {
			save($event, $modalInstance, data, $scope, type);
		});
	}
	
	/** 新增*/
	function add($event, node, type) {

		// 添加子节点
		var category = {};

			category = {
				parentId : node.categoryId ? node.categoryId : -1,
				parentName : node.name,
				level : node.level + 1,
				logo : "resource/image/addImg.png",
				name : "",
				rank : 1
			}


		var url = urls.ms + "/goods/category/addCatergoy.do?parentId="+category.parentId;
		templateform.open({
			title : "类别信息",
			url : url,
			scope : $scope,
			data : category,
			dataName : "category"
			
		}, function($modalInstance, data, $scope) {
			save($event, $modalInstance, data, $scope, type);
		});
	}

	/** 保存 */
	function save($event, $modalInstance, data, $scope, type) {
		
		delete data.updateTime;
		delete data.createTime;
		var category = data;
		try {
			if (!data.name) {
				toaster.error("", "请输入类别名称");
				return;
			}
			if (data.logo == "resource/image/addImg.png") {
				data.logo = "";
			}
			$('#category-edit-form').form(
					"submit",
					{
						url : "goods/category/save.do",
						onSubmit : function(param) {
							param.category = angular.toJson(data);
						},
						success : function(data) {
							
							// 这个data不知为何返回的数据带有一个Audio媒体标签,所以先截取掉
							data = angular.fromJson(data.substring(0, data
									.indexOf("}") + 1));
							if (data.respCode === "S") {
								animate($event);
								$scope.$apply(function() {
									$scope.list();
									$modalInstance.close();
									toaster.success("", data.data);
								});
							} else {
								$scope.$apply(function() {
									toaster.error("", data.errorMsg);
								});
							}
						}
					});
		} catch (e) {
			console.error(e);
			toaster.error("", typeof e == "string" ? //
			e : e.msg ? //
			e.msg : "出错了");
		}
	}

	/** 查看 */
	function show(id) {
		var url = urls.ms + "/goods/category/show.do?";
		if (id) {
			url = url + $.param({
				categoryId : id
			});
		}
		templateform.open({
			title : "Category",
			buttons : [],
			backdrop : true,
			keyboard : true,
			url : url
		});
	}

	// 初始化
	init();
	
	//平滑跳转页面
	function animate($event){
		var $div = $($event.target);
		$("html,body").animate({scrollTop:$div.offset().top-$(window).height()/2},1500);
		$div.css('background-color','red');
	}
});