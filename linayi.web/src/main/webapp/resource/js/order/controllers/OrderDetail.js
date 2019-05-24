'use strict';

app.controller('orderDetailCtrl', function($scope) {

    function init(){

        var procurementTaskId = $("#procurementTaskId").val();
        $scope.search= {
            procurementTaskId: procurementTaskId,
        }
        $scope.list = list;
        $scope.list();
    }

    /** 列表查询 */
    function list(){
        var $grid = $("#orderSupermarketList");
        if( $grid[0].grid ){
            $grid.jqGrid('setGridParam', {
                page : 1,
                postData:$scope.search,

            }).trigger("reloadGrid");
            return;
        }
        $grid.jqGrid({
            url : urls.ms+"/procurement/procurement/procurementList.do",
            postData:$scope.search,
            colModel : [
                {name:'procurementTaskId',label:'采买任务编号',sortable:false,width:10},
                {name:'supermarketName',label:'超市名称',sortable:false,width:20},
                {name:'price',label:'超市价格(元)',sortable:false,width:30,formatter:function( cellValue, options, rowObject ){
                        return cellValue ? cellValue / 100 : "";
                    },editable:true,edittype:"text"},
                {name:'procureQuantity',label:'实际采买数量',sortable:false,width:30},
                {name:'procureStatus',label:'采买状态',sortable:false,width:20,formatter:function(value,row,rowObject){
                        switch (value) {
                            case 'LACK':
                                return '缺货';
                                break;
                            case 'PRICE_HIGH':
                                return '价高未买';
                                break;
                            case 'BOUGHT':
                                return '已买';
                                break;
                            case 'PROCURING':
                                return '采买中';
                                break;
                            default:
                                return '';
                                break;
                        }}
                }
            ]
        });


    }

    // 初始化
    init();
});