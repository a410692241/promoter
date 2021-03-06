package com.linayi.service.procurement;

import com.linayi.entity.community.Community;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.util.ResponseData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    void updateOrderStatus(ProcurementTask procurementTask);

    /**
     * 根据订单id和实际采买数量大于0查询采买任务表
     * @param procurementTask
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

    /**
     * 查询采买任务合并列表
     * @param procurementTask
     * @return
     */
    List<ProcurementTask> getCommunityProcurement(ProcurementTask procurementTask);

    ResponseData updateProcurmentStatus(ProcurementTask procurementTask);

    /**
     * 查询采买任务详情列表
     * @param procurementTask
     * @return
     */
    ProcurementTask getprocurementDeatil(ProcurementTask procurementTask);

    /**
     * 采买任务改发货状态
     * @param procurementTask
     */
    void updateProcurReceiveStatus(ProcurementTask procurementTask);

    /**
     * 流转中心任务 查询出未收货的商品列表
     * @param procurTask
     * @return
     */
    List<ProcurementTask> getNotReceivingGoods(ProcurementTask procurTask);
    /**
     * 流转中心任务 对未收货的商品进行收货操作
     * @param procurementTaskIdList
     * @return
     */
    void confirmGoods(String procurementTaskIdList);
    /**
     * 流转中心任务 查询出未发货的商品列表
     * @param procurTask
     * @return
     */
    List<ProcurementTask> getNotDeliverGoods(ProcurementTask procurTask);
    /**
     * 流转中心任务 对收货的商品进行发货操作
     * @param procurementTaskIdList
     * @return
     */
    void confirmDeliverGoods(String procurementTaskIdList);

    List<ProcurementTask> getCommunityProcurements(ProcurementTask procurementTask);
    /**
     * 流转中心任务 查询出未发货的商品列表
     * @param procurTask
     * @return
     */
    List<ProcurementTask> getDeliverGoodsList(ProcurementTask procurTask);

    /**
     *  后台管理系统 流转中心查询出已发货信息
     * @param procurementTask
     * @return
     */
    List<ProcurementTask> getProcurementsByRECEIVEDFLOW(ProcurementTask procurementTask);
    /**
     *  后台管理系统 流转中心批量发货
     * @param procurementTaskIdList
     * @return
     */
    void batchDelivery(List<Long> procurementTaskIdList);

    /**
     * 后台管理系统 流转中心导出数据
     * @param procurementTask
     * @param request
     * @param response
     * @throws Exception
     */
    void exportData(ProcurementTask procurementTask, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<ProcurementTask> getProcurements(ProcurementTask procurementTask);

    String createProcureMergeNo();

    /**
     * 采买任务商品装箱
     * @param procureMergeNo
     * @param quantity
     */
    void packingSkuGoods(String procureMergeNo, Integer quantity,String box_no);
}
