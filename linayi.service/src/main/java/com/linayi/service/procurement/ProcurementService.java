package com.linayi.service.procurement;

import com.linayi.entity.community.Community;
import com.linayi.entity.procurement.ProcurementTask;

import java.util.List;

public interface ProcurementService {
	public void addProcurementTask(ProcurementTask task);

    /**
     * 查询采买任务列表
     * @param procurementTask
     * @return
     */
    Object getProcurementList(ProcurementTask procurementTask);

    /**
     * 查询采买任务
     * @param procurementTask
     * @return
     */
    ProcurementTask getProcurementTask(ProcurementTask procurementTask);

    /**
     * 修改
     * @param procurementTask
     */
    void updateProcurement(ProcurementTask procurementTask);






    //根据任务id获取任务信息
    ProcurementTask getProcurementById(Long procurementTaskId);

    //返回任务列表和剩余的超市及价格
    List<ProcurementTask> showProcurementTaskAndOrderGoods(ProcurementTask procurementTask);

    //根据任务id获取orderId而后修改订单状态
    Integer updateOrderStatus(ProcurementTask procurementTask);

    /**
     * 根据订单id和实际采买数量大于0查询采买任务表
     * @param orderId
     * @return
     */
    List<ProcurementTask> getProcurementTaskByOrderIdAndActualQuantity(ProcurementTask procurementTask);

    void updateOrdersGoodsStatus(ProcurementTask procurementTask);

    ProcurementTask getProcurementTaskLast(ProcurementTask procurementTask);


    /**
     * 获取状态为未收货并且数量大于0的
     * @param procurementTask
     * @return
     */
    List<ProcurementTask> getProcurementTaskStatus(ProcurementTask procurementTask);

    List<Community> getProcurementCommunity(Integer userId);

    List<ProcurementTask> getCommunityProcurement(ProcurementTask procurementTask);

    void updateProcurmentStatus(Integer goodsSkuId, Integer quantity, Integer userId);
}
