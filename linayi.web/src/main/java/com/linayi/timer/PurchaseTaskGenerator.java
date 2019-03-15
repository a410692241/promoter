package com.linayi.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import com.linayi.service.order.OrdersGoodsService;

/**
 * 采买任务生成定时器
 */
public class PurchaseTaskGenerator extends QuartzJobBean implements Job {

    @Autowired
    private OrdersGoodsService ordersGoodsService;
//    @Autowired
//    private ProcurementTaskMapper procurementTaskMapper;


    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("Method:开始发布采买任务");
//        //查询未发布的订单
//        List<OrdersGoods> ordersGoodsList = ordersGoodsService.getNoProcumentTaskOrders();
//        List<ProcurementTask> taskList = new ArrayList<>();
//        if(ordersGoodsList != null){
//            for (OrdersGoods  ordersGoodsDB : ordersGoodsList) {
//                ProcurementTask procurementTask = new ProcurementTask();
//                procurementTask.setOrdersId(ordersGoodsDB.getOrdersId());
//                procurementTask.setSupermarketId(ordersGoodsDB.getSupermarketId());
//                procurementTask.setGoodsSkuId(ordersGoodsDB.getGoodsSkuId());
//                procurementTask.setPrice(ordersGoodsDB.getPrice());
//                procurementTask.setQuantity(ordersGoodsDB.getQuantity());
//                procurementTask.setSupermarketList(ordersGoodsDB.getSupermarketList());
//                procurementTask.setMaxPrice(ordersGoodsDB.getMaxPrice());
//                procurementTask.setMaxSupermarketId(ordersGoodsDB.getMaxSupermarketId());
//                procurementTask.setStatus(ProcurementStates.WAIT_PROCURE.name());
//                procurementTask.setUpdateTime(new Date());
//                procurementTask.setCreateTime(new Date());
//                taskList.add(procurementTask);
//            }
//            procurementTaskMapper.insertBatch(taskList);
//        }
//
//        System.out.println("Method:发布采买任务完成");
    }
}
