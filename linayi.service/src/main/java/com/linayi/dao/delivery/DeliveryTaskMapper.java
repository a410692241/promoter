package com.linayi.dao.delivery;


import com.linayi.entity.delivery.DeliveryTask;

import java.util.List;

public interface DeliveryTaskMapper {
	/**
	 * 获取所有已装箱的配送任务
	 * @return 配送任务集合
	 */
	List<DeliveryTask> getListDeliveryTask(DeliveryTask deliveryTask);
	/**
	 * 通过配送箱ID修改状态为配送中
	 * @param deliveryBoxId
	 * @return
	 */
	Integer updateStatusByDeliveryBoxId(Long deliveryBoxId);

	/**
	 * 根据userId和status查询派送完成的任务
	 * @param deliveryTask
	 * @return
	 */
    List<DeliveryTask> getDeliveryTaskByUserIdAndStatus(DeliveryTask deliveryTask);

	/**
	 * 根据userId和status查询派送完成的任务
	 * @param deliveryTask
	 * @return
	 */
	List<DeliveryTask> getDeliveryTaskByUserId(DeliveryTask deliveryTask);

	/**
	 * 根据userId和ordersId修改派送单状态
	 * @param deliveryTask
	 * @return
	 */
	Integer updateDeliveryTaskStatusByOrdersId(DeliveryTask deliveryTask);
	 /**根据筛选条件查询派送任务*/
    List<DeliveryTask> selectAll(DeliveryTask deliveryTask);
    /**
     * 通过配送任务ID获取配送任务
     * @param deliveryTaskId
     * @return
     */
    DeliveryTask getDeliveryTaskById(Long deliveryTaskId);
    
    /**
   	 * 插入
   	 * @param DeliveryBoxGoods
   	 * @return
   	 */
   	Integer insert(DeliveryTask deliveryTask);

	/**
	 * 通过订单ID获取配送任务详情
	 * @param ordersId
	 * @return
	 */
	DeliveryTask getDeliveryTaskByOrdersId(Long ordersId);
}