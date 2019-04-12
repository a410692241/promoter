'use strict';
app.filter('skuAttributeSorter', function( ) {

	return function( skuAttributeList,salesAttributeList ) {
		// 根据商品属性排序
		angular.forEach( salesAttributeList, function( attribute,attributeIndex ){
			var attributeId = attribute.attributeId;
			var attrValueList = attribute.attrValueList;

			var currentSkuAttribute = skuAttributeList[attributeIndex];
			// 交换位置
			angular.forEach(skuAttributeList, function( skuAttribute, skuAttributeIndex){
				var skuAttributeId = skuAttribute.attributeId;
				if( attributeId == skuAttributeId ){
					var temp = skuAttributeList[skuAttributeIndex];
					skuAttributeList[attributeIndex] = temp;
					skuAttributeList[skuAttributeIndex] = currentSkuAttribute;
					// 获取属性值名称
					angular.forEach( attrValueList,function( valueItem,valueIndex ){
						if( temp.attrValueId == valueItem.attrValueId ){
							temp.valueName = valueItem.name;
						}
					} );
					return;
				}
			});

		});


		return skuAttributeList;
	};
});
app.controller('goodsCtrl'/**
     *
     */
    , function ($q, $http, $scope, toaster, goodsService, messager, templateform, $templateCache) {

        function init() {
            $scope.editeAttribute = editeAttribute;
            $scope.show = show;
            $scope.edit = edit;
            $scope.exportData = exportData;
            $scope.remove = remove;
            $scope.share = share;
            $scope.getPrice = getPrice;
            $scope.list = list;
            $scope.addUI = addUI;
            $scope.saveShare = saveShare;
            $scope.checkedCategory = checkedCategory;
            $scope.brandList = new Array({name: "全部", code: ""});
            $scope.selectedBarand = $scope.brandList[0];
            $scope.removeList = removeList;
            $scope.changeStatusList = changeStatusList;
            $scope.search = {
                goodsName: "",
                brandId: "",
                categoryId: "",
                createTimeEnd: "",
                createTimeStart: ""
            };
            $scope.changeStatus = changeStatus;
            $scope.importUI = importUI;
            $scope.supermarkets = [];
            list();
        }

        function importUI() {
            templateform.open({
                title: "商品导入",
                url: "jsp/goods/GoodsImportUI.jsp",
                buttons: [],
                onOpen: function ($modalInstance, data, $scope) {
                    $scope.importResult = {
                        data: []
                    }

                    $scope.downloadErrorData = function () {
                        if ($scope.importResult.file) {
                            $.download("goods/goods/downloadErrorData.do", {
                                "fileName": $scope.importResult.file
                            });
                        }

                    }

                    $scope.confirmImport = function () {
                        var defer = $q.defer();
                        $("<input name='file' type='file' style='visibility: hidden;'/>").click().change(function (value) {
                            // 提交表单
                            var $body = $("body:eq(0)");
                            var $form = $("<form action='#' enctype='multipart/form-data' method='post'>");
                            $body.append($form);
                            $form.append($(this)).form("submit", {
                                onSubmit: function (param) {
                                    $scope.promise = defer.promise;
                                },
                                success: function (data) {
                                    data = angular.fromJson(data);
                                    $scope.$apply(function () {
                                        if (data.success) {
                                            toaster.info("", data.msg, 3000);
                                            list();
                                        }

                                        $scope.importResult = data.data;
                                        defer.resolve(data);
                                        if (data.success && $scope.importResult.data.length == 0) {
                                            $modalInstance.close();
                                        }
                                    })
                                },
                                url: "goods/goods/importGoods.do"
                            });
                        });
                    }
                }
            }, function ($modalInstance, data, $scope) {
                $modalInstance.close()
            })

        }

        /***************************************************************************
         * 上架下架
         */
        function changeStatus(goodsId, status) {
            var message = status == 2 ? "确认下架？" : "确认上架？";
            messager.confirm(message, function ($modalInstance) {
                $.ajax({
                    url: "goods/goods/changeStatus.do",
                    data: {
                        goodsId: goodsId,
                        status: status
                    },
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        if (data.success) {
                            $("#goodsList").trigger("reloadGrid");
                        } else {
                            toaster.error("", data.msg, 3000);
                        }
                        $modalInstance.close();
                    }
                })
            });
        }

        function changeStatusList(status) {
            // 确认操作
            var selarrrow = $('#goodsList').jqGrid('getGridParam', 'selarrrow');
            if (selarrrow.length == 0) {
                toaster.error("", '请选择行', 3000);
                return;
            }
            var message = status == 2 ? "确认下架？" : "确认上架？";
            messager.confirm(message, function ($modalInstance) {
                for (var i = 0; i < selarrrow.length; i++) {
                    var obj = $('#goodsList').jqGrid('getRowData', selarrrow[i]);
                    var goodsId = obj.goodsId;
                    $.ajax({
                        url: "goods/goods/changeStatus.do",
                        data: {
                            goodsId: goodsId, status: status
                        }, dataType: "json", type: "post",
                        success: function (data) {
                            if (data.success) {
                                $("#goodsList").trigger("reloadGrid");
                            } else {
                                toaster.error("", data.msg, 3000);
                            }
                            $modalInstance.close();
                        }
                    })

                }

            });
        }


        /** 列表查询 */
        function list() {
            if ($scope.search.goodsName == "全部") {
                $scope.search.goodsName = "";
            }
            var $grid = $("#goodsList");
            if ($grid[0].grid) {
                $grid.jqGrid('setGridParam', {
                    page: 1,
                    postData: $scope.search,
                }).trigger("reloadGrid");
                return;
            }
            $grid.jqGrid({
                url: urls.ms + "/goods/goods/list.do",
                postData: $scope.search,
                pager: "#goodsPager",
                multiselect: true,
                colModel: [
                    {name: 'fullName', label: '商品名称', width: 120, sortable: false},
                    {name: 'categoryName', label: '类别', width: 60, sortable: false},
                    {name: 'brandName', label: '品牌', width: 60, sortable: false},
                    {name: 'barcode', label: '条形码', width: 60, sortable: false},
                    {name: 'createName', label: '添加人', width: 60, sortable: false},
                    {name: 'userName', label: '创建者账号', width: 60, sortable: false},
                    {
                        name: 'createTime',
                        label: '创建时间',
                        width: 80,
                        sortable: false,
                        formatter: function (cellvalue, options, rowObject) {
                            return cellvalue ? new Date(cellvalue).format("yyyy-MM-dd hh:mm:ss") : "";
                        }
                    },
                    {name: 'goodsSkuId', label: '商品编号', width: 60, sortable: false},
                    {
                        label: "操作",
                        name: "opt",
                        width: 180,
                        sortable: false,
                        formatter: function (cellvalue, options, rowObject) {
                            var opts = "";
                            /*	if( rowObject.status == 1 || rowObject.status==2 ){
                                    opts = opts + "<a href='javascript:void(0);' ng-click='changeStatus( "+rowObject.goodsId+","+3+")' class='btn btn-primary fa fa-eye btn-sm td-compile'>上架</a> ";
                                }else{
                                    opts = opts + "<a href='javascript:void(0);' ng-click='changeStatus( "+rowObject.goodsId+","+2+" )' class='btn btn-primary fa fa-eye btn-sm td-compile'>下架</a> ";
                                }*/
                            opts = opts + "<a href='javascript:void(0);' ng-click='show( " + rowObject.goodsSkuId + " )' class='btn btn-primary fa fa-show btn-sm td-compile'>查看</a> ";
                            opts = opts + "<a href='javascript:void(0);' ng-click='edit( " + rowObject.goodsSkuId + " )' class='btn btn-primary fa fa-edit btn-sm td-compile'>编辑</a> ";
                            /*opts = opts + "<a href='javascript:void(0);' ng-click='remove( "+rowObject.goodsSkuId+" )' class='btn btn-primary fa fa-remove btn-sm td-compile'>删除</a> ";*/
                            opts = opts + "<a href='javascript:void(0);' ng-click='share( " + rowObject.goodsSkuId + " )' class='btn btn-primary fa fa-show btn-sm td-compile'>价格分享</a> ";
                            return opts;
                        }
                    }
                ]
            });

        }

        /** 列表查询页面选择类别 */
        function checkedCategory($item) {
            try {
                $scope.search.categoryId = $item.code;
                $scope.brandList.splice(1, $scope.brandList.length - 1);

                angular.element("#brandId").scope().$select.select($scope.brandList[0]);
                if (!$item.code) {
                    return;
                }
                // 获取品牌信息
                goodsService.listBrand({
                    data: {categoryId: $scope.search.categoryId},
                    success: function (data) {
                        if (data instanceof Array) {
                            $scope.brandList = $scope.brandList.concat(data);
                        } else {
                            $scope.$apply(function () {
                                toaster.error("", data.msg, 3000);
                            });
                        }
                    }
                });
            } catch (e) {
                toaster.error("", typeof e == "string" ? //
                    e : e.msg ? //
                        e.msg : "出错了", 3000);
            }
        }

        /** 列表查询页面选择品牌 */
        function checkedbrand($item) {
            try {
                $scope.search.brandId = $item.code;
                $scope.brandList.splice(1, $scope.brandList.length - 1);

                angular.element("#brandId").scope().$select.select($scope.brandList[0]);
                if (!$item.code) {
                    return;
                }
                // 获取品牌信息
                goodsService.listBrand({
                    data: {categoryId: $scope.search.categoryId},
                    success: function (data) {
                        if (data instanceof Array) {
                            $scope.brandList = $scope.brandList.concat(data);
                        } else {
                            $scope.$apply(function () {
                                toaster.error("", data.msg, 3000);
                            });
                        }
                    }
                });
            } catch (e) {
                console.error(e);
                toaster.error("", typeof e == "string" ? //
                    e : e.msg ? //
                        e.msg : "出错了", 3000);
            }
        }


        /** 删除 */
        function remove(id) {
            messager.confirm("确认删除？", function ($modalInstance) {
                goodsService.remove({
                    "data": {goodsSkuId: id},
                    "success": function (data) {
                        if (data.data == "success") {
                            $("#goodsList").trigger("reloadGrid");
                            $scope.$apply(function () {
                                toaster.success("", "删除成功！", 3000);
                            });
                            $modalInstance.close();
                        } else {
                            $scope.$apply(function () {
                                toaster.error("", "删除失败!", 3000);
                            });
                        }
                    }
                });
            });
        }
        var supermarkets = [];

        /** 分享 */
        function share(id) {
            var url = urls.ms + "/goods/goods/shareEdit.do?";
            if (id) {
                url = url + $.param({goodsSkuId: id});
            }
            templateform.open({
                title: "价格分享",
                url: url,
                scope: $scope,
                onOpen: function ($modalInstance, data, $scope) {
                    var url = urls.ms + "/correct/correct/priceSupermarket.do?";
                    $.ajax({
                        url: url,
                        data: {"goodsSkuId": id},
                        dataType: "json",
                        success: function (datas) {
                            supermarkets = datas.data;
                        }
                    });
                }
            }, function ($modalInstance, data, $scope) {
                saveShare($modalInstance, data, $scope);
            });
        }
    /** 删除 */
    function removeList( ){
    	var selarrrow=$("#goodsList").jqGrid('getGridParam','selarrrow');
    	if(selarrrow.length==0){
			toaster.error("",'请选择行',3000 );
			return;
		}
    	messager.confirm("确认删除？",function( $modalInstance ){
    		for(var i = 0;i < selarrrow.length; i++){
    			var obj=$("#goodsList").jqGrid('getRowData',selarrrow[i]);
    			var goodsId = obj.goodsId;
    		goodsService.remove({
    			"data":{goodsId:goodsId},
    			"success":function( data ){
            		if( data.success ){
            			$("#goodsList").trigger("reloadGrid");
            			$scope.$apply(function(){
            				toaster.success( "", data.msg ,3000 );
						});
            			$modalInstance.close();
            		}else{
            			$scope.$apply(function(){
            				toaster.error( "", data.msg ,3000 );
						});
            		}
            	}
    		});
    		}
    	});
    }

    /**
	 * 编辑
	 *
	 * @param goodsId
	 *            商品ID
	 * @param productionId
	 *            产品ID
	 */
	function edit( goodsSkuId){
		// 获取商品信息
		$.ajax({
			url:"goods/goods/edit.do",
			data:{
				goodsSkuId: goodsSkuId
			},
			dataType:"json",
			type:"post",
			success:function (data) {
				openEdit( data.data );
			}
		});
	}

    /***************************************************************************
	 * 新增编辑页面
	 */
    function addUI(){
    	templateform.open({
			title:"产品搜索",
			url:"jsp/goods/GoodsCategorySearch.jsp",
			onOpen:function( $modalInstance, data ,$scope){
				$.ajax({
					url:"system/options/listOptions",
					data:{sqlId:"listCategory",level:3},
					dataType:"json",
					type:"post"
				}).then( function( data ){
					init( data );
				} );

				var search = $scope.search={
					categoryName:"",
					category:{},
					brandName:"",
					brand:{},
					productionName:"",
					production:{},

				}


				function init( data ){
					$scope.categoryList = data;
				}

				// 类别查询品牌
				$scope.listBrandList = function( category ){
					if( search.category == category ){
						return;
					}
					search.category = category;
					$scope.brandList = [];
					$scope.search.brand = {};
					$scope.productionList = [];
					$scope.search.production = {};
					$.ajax({
						url:"system/options/listOptions",
						data:{sqlId:"listBrand",categoryId:category.code},
						dataType:"json",
						type:"post"
					}).then( function( data ){
						$scope.$apply( function(){
							$scope.brandList = data;
						} );
					} );
					$.ajax({
						url:"system/options/listOptions",
						data:{sqlId:"listProduction",categoryId:category.code},
						dataType:"json",
						type:"post"
					}).then( function( data ){
						$scope.$apply( function(){
							$scope.productionList = data;
						} );
					} );
				}

				// 品牌查询产品
				$scope.listProduction = function( brand ){
					if( search.brand == brand ){
						return;
					}
					search.brand = brand;
					$scope.productionList = [];
					$scope.production = {};
					$.ajax({
						url:"system/options/listOptions",
						data:{
							sqlId:"listProduction",
							categoryId:search.category.code,
							brandId:search.brand.code,
						},
						dataType:"json",
						type:"post"
					}).then( function( data ){
						$scope.$apply( function(){
							$scope.productionList = data;
						} );
					} );
				}

				/** 选择产品 */
				$scope.selectedProduction = function( production ){
					search.production = production;
				}

				$scope.confirmPorudction = function(){

				}
			}
		},function( $modalInstance,data, $scope ){
			var production = $scope.search.production;
			if( ! production.code ){
				return toaster.error( "","请选择产品",3000 );
			}
			// 获取产品信息
			edit( null,production.code );
		});
    }
    /** 新增，编辑 */
    function openEdit( data ){

    	var url = urls.ms + "/jsp/goods/GoodsEdit.jsp";
		templateform.open({
			title:"修改商品信息",
			url:url,
			scope:$scope,
			data:data,
			dataName:"data",
			onOpen:function( $modalInstance, data ,$scope){
					$scope.goods = {
						goodsSkuId:data.goodsSkuId,
						fullName:data.fullName,
						name:data.name,
						categoryId:data.categoryId,
						brandId:data.brandId,
						image:data.image,
						model:data.model,
						createTimeStart:data.produceDate,
						createTimeEnd:data.validDate,
						attrValues:data.attrValues,
						otherAttribute:data.otherAttribute,
						barcode:data.barcode
					}
			}
		},function( $modalInstance,data, $scope ){
			save( $modalInstance,$scope.goods, $scope );
		});
    }


    /**
	 *
	 * 推荐商品列表查询
	 */
    function recommandModal( $modalInstance,data, $scope ){
    	templateform.open({
			title:"商品信息",
			data:data,
			url:"jsp/goods/GoodsRecommandList.jsp",
		},function( $modalInstance,data, $scope ){
    		var rowData =  $("#goodsRecommandList").jqGrid('getRowData');
    		var rowIndexArr =  $("#goodsRecommandList").jqGrid('getGridParam', 'selarrrow');
    		var selectedRows = [];
    		for( var i = 0 ; i < rowIndexArr.length; i++ ){
    			selectedRows.push( rowData[rowIndexArr[i]] );
    		}
    		data.recommendGoodsList = selectedRows;
    		console.info( selectedRows );
    		$modalInstance.close();
		})
    }

	/** 保存 */
    function save( $modalInstance,data, $scope ){
    	try{
    		if( !data.fullName ){
    			toaster.error( "", "请填写商品名称!", 3000 );
    			return;
    		}
			if( !data.barcode ){
				toaster.error( "", "请填写条形码!", 3000 );
				return;
			}

            if( data.barcode.length > 13 ){
                toaster.error( "", "条形码只有13位!", 3000 );
                return;
            }

			$('#goodsSkuInfoForm').form("submit",{
				url:urls.ms + "/goods/goods/save.do",
				onSubmit: function( param ){
					for(var key in data){
						param[key] = data[key]
					}
				},
				success:function(datas){
					datas = JSON.parse(datas);
					if( datas.data==="success" ){
						$("#supermarketList").trigger("reloadGrid");
						$scope.$apply(function(){
							$modalInstance.close();
							toaster.success( "","操作成功",3000 );
						});
					}else if( datas.data==="nameRepeat" ){
                        $("#supermarketList").trigger("reloadGrid");
                        $scope.$apply(function(){
                            toaster.success( "","商品名称存在",3000 );
                        });
                    }else if( datas.data==="barcodeRepeat" ){
                        $("#supermarketList").trigger("reloadGrid");
                        $scope.$apply(function(){
                            toaster.success( "","商品条形码存在",3000 );
                        });
                    }else{
						$scope.$apply(function(){
							toaster.error( "",datas.msg,3000 );
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
		}catch (e) {
			console.error( e );
			toaster.error( "",typeof e == "string" ? //
					e : e.msg ? //
							e.msg : "出错了",3000 );
		}
    }

        /** 查看 */
        function show(id) {
            var url = urls.ms + "/goods/goods/show.do?";
            if (id) {
                url = url + $.param({goodsSkuId: id});
            }
            templateform.open({
                title: "查看商品信息",
                buttons: [],
                backdrop: true,
                keyboard: true,
                url: url
            });
        }
        var isTrue =true;
        //商品列表确认分享
        function saveShare($modalInstance, data, $scope) {
            if(isTrue){
                isTrue =false;
            }else {
                return;
            }
            console.log($scope);
            var correct = $scope.correct;
            correct.price = ($("#price").val() * 100).toFixed(0);
            var priceType = correct.priceType;
            correct.correctType = $("#correctType").val();
            correct.correctId = $("#correctId").val();

            $.ajax({
                url: urls.ms + "/correct/correct/updatePriceForAdmin.do",
                data: $scope.correct,
                dataType: "json",
                type: "post",
                success: function (data) {
                    isTrue = true;
                    if (data.respCode === "S") {
					$modalInstance.close();
                        alert("修改价格成功,等待审核!");
                    } else {
                        alert("修改价格失败");
                    }
                }
            });
        }

        function editeAttribute(goodsSkuId) {
            var param = "?goodsSkuId=" + goodsSkuId;
            // 修改商品规格
            window.open(urls.ms + "/goods/goods/toAddSpecifications.do" + param, "修改商品规格", "height=800, width=1000,top=0,left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no")
        }

        //通过超市id获取该超市的分享纠错的价格
        function getPrice($item, supermarektMap) {
            var supermarketId = $item.code;
            // $scope.correct.supermarketId = supermarketId;
            //分享价格绑定数据
            console.log(supermarkets);
            for (var i = 0; i < supermarkets.length; i++) {
                var supermarket = supermarkets[i];
                if (supermarket.supermarketId === supermarketId) {
                    if (supermarket.goodsPrice != undefined) {
                        $("#price").val(supermarket.goodsPrice / 100);
                    }else{
                        $("#price").val('');
                    }
                    $("#correctType").val(supermarket.correctType);
                    $("#correctId").val(supermarket.correctId);

                }
            }
        }
        //导出数据
        function exportData() {
            var fullName = $scope.search.fullName;
            var categoryId = $scope.search.categoryId;
            var brandId = $scope.search.brandId;
            var barcode = $scope.search.barcode;
            var createName = $scope.search.createName;
            var userName = $scope.search.userName;
            var createTimeStart = $scope.search.createTimeStart;
            var createTimeEnd = $scope.search.createTimeEnd;
            var sum = 0;
            var data = '';
            if (fullName === undefined || fullName == '') {
                fullName = null;
                sum++;
            } else {
                data += '&fullName=' + fullName;
            }
            if (categoryId === undefined || categoryId == '') {
                categoryId = null;
                sum++;
            } else {
                data += '&categoryId=' + categoryId;
            }
            if (brandId === undefined || brandId == '') {
                brandId = null;
                sum++;
            } else {
                data += '&brandId=' + brandId;
            }
            if (barcode === undefined || barcode == '') {
                barcode = null;
                sum++;
            } else {
                data += '&barcode=' + barcode;
            }
            if (userName === undefined || userName == '') {
                userName = null;
                sum++;
            } else {
                data += '&userName=' + userName;
            }
            if (createName === undefined || createName == '') {
                createName = null;
                sum++;

            } else {
                data += '&createName=' + createName;
            }
            if (createTimeStart === undefined || createTimeStart == '') {
                createTimeStart = null;
                sum++;
            } else {
                data += '&createTimeStart=' + createTimeStart;
            }
            if (createTimeEnd === undefined || createTimeEnd == '') {
                createTimeEnd = null;
                sum++;
            } else {
                data += '&createTimeEnd=' + createTimeEnd;
            }

            if (sum == 8) {
                toaster.error("", "请输入搜索条件后导出!", 3000);
                return;
            }

            //window.location.href = urls.ms + "/goods/goods/exportGoodsData.do?" + data.replace("&", "");
            location.href = urls.ms + "/goods/goods/exportGoodsData.do?" + data.replace("&", "");
            toaster.success("", "导出成功!", 1000);


        }

        // 初始化
        init();
    });
