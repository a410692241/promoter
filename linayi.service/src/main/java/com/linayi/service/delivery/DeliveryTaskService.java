package com.linayi.service.delivery;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.linayi.entity.delivery.DeliveryTask;
import com.linayi.entity.order.Orders;

public interface DeliveryTaskService {
	/**
	 * 获取所有的配送任务
	 * @return 配送任务集合
	 */
	List<Orders> getListDeliveryTask(Orders orders);
	/**
	 * 通过deliveryBoxId和ordersId查看配送任务详情
	 * @param deliveryBoxId
	 * @param ordersId
	 * @return
	 */
	Orders toViewDeliveryTask(String boxNo,Long ordersId);
	/**
	 * 确认封箱
	 * @param deliveryBoxId
	 * @param ordersId
	 * @return
	 */
	Integer sealBox(String boxNo,Long ordersId,MultipartFile file);

	/**
	 * 根据userId和status查询派送任务
	 * @param orders
	 * @return
	 */
    List<Orders> getOrdersBydelivererIdAndStatus(Orders orders);

	/**
	 * 根据userId和ordersId修改订单状态
	 * @param orders
	 * @return
	 */
    Integer updateFinishedStatus(Orders orders);
    
    
    //根据筛选条件查询派送任务
    List<DeliveryTask> selectAll(DeliveryTask deliveryTask);

	/**
	 * 查看配送订单详情
	 * @param orders
	 * @return
	 */
    Orders getDeliveryTaskDetails(Orders orders);
    /**
     * 通过通过配送任务ID和配送箱ID获配送详情
     * @param deliveryBoxId
     * @param deliveryTaskId
     * @return
     */
    DeliveryTask getDeliveryTaskView(Long ordersId,Long deliveryTaskId);
}
