'use strict';

app.controller('correctProfitCtrl', function($scope,toaster,correctService,messager,templateform ) {
	
	function init(){
		$scope.correctProfit = {
				sharerPercent:'',
				supplierPercent:'',
				communityPercent:'',
				cardServiesPercent:'',
		};
		
		getInitData();
	}
	
	function getInitData(){
		$.ajax({
			url : urls.ms + "/goods/correct/getProfitInfo.do",
			async : false,
			type : "post",
			dataType : "JSON",
			success : function(data){
				if(data.success){
					console.log(data)
					$scope.correctProfit = {
						sharerPercent:data.data.sharerPercent,
						supplierPercent:data.data.supplierPercent,
						communityPercent:data.data.communityPercent,
						cardServiesPercent:data.data.cardServiesPercent,
				};
				}
			}
		})
	}
	
	$scope.saveProfitRule = function(){
		if($scope.correctProfit.sharerPercent == ''){
			toaster.error( "","分享员占比不能为空",3000 );
			return;
		}
		if($scope.correctProfit.supplierPercent == ''){
			toaster.error( "","收取供应商服务费占比不能为空",3000 );
			return;
		}
		if($scope.correctProfit.communityPercent == ''){
			toaster.error( "","社区网点占比不能为空",3000 );
			return;
		}
		if($scope.correctProfit.cardServiesPercent == ''){
			toaster.error( "","信用卡刷卡手续费占比不能为空",3000 );
			return;
		}
		messager.confirm("确认修改收益规则？",function( $modalInstance ){
    		$.ajax({
				url : urls.ms + "/goods/correct/saveProfit.do",
				async : false,
				type : "post",
				data : {"sharerPercent":$scope.correctProfit.sharerPercent,"supplierPercent":$scope.correctProfit.supplierPercent,
						"communityPercent":$scope.correctProfit.communityPercent,"cardServiesPercent":$scope.correctProfit.cardServiesPercent},
				dataType : "JSON",
				success : function(data){
					if(data.success){
						$modalInstance.close();
						toaster.success( "","修改成功",3000 );
					}
				}
			})
    		
    	});
	}
	
	
    //初始化
    init();
});