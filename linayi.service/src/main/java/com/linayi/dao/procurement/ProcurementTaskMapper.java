package com.linayi.dao.procurement;


import java.util.List;
import java.util.Date;
import org.apache.ibatis.annotations.Param;

import com.linayi.entity.procurement.ProcurementTask;

public interface ProcurementTaskMapper {

    ProcurementTask selectByPrimaryKey(Long primaryKey);

    void insert(ProcurementTask procurementTask);


    List<ProcurementTask> getProcurementTaskList(ProcurementTask procurementTask);

    List<ProcurementTask>  getProcurementTaskListAsc(ProcurementTask procurementTask);

    Integer updateProcurementTaskById(ProcurementTask procurementTask);

    List<ProcurementTask> getProcurementList(ProcurementTask procurementTask);

    ProcurementTask getProcurementById(Long procurementTaskId);

    List<ProcurementTask> selectSIdPriceByOgId(ProcurementTask procurementTask);

    List<ProcurementTask> selectAllProcurementTask(ProcurementTask procurementTask);

    /**
     * 根据订单id和实际采买数量大于0查询采买任务表
     * @param orderId
     * @return
     */
    List<ProcurementTask> getProcurementTaskByOrderIdAndActualQuantity(ProcurementTask procurementTask);

    List<ProcurementTask> selectProcuringByOrdersId(ProcurementTask procurementTask);

    /**
     * 根据orderId查询采买完成的然后去采买任务中获取数量大于0的收货状态
     * @param ordersId
     * @return
     */
    List<String> getReceiveStatusByOrderId(Long ordersId);


    /**
     * 获取状态为未收货并且数量大于0的
     * @param procurementTask
     * @return
     */
    List<ProcurementTask> getProcurementTaskStatus(ProcurementTask procurementTask);

    List<ProcurementTask> getProcurementTaskByOrdersId(Long ordersId);
    /**
     * 通过订单Id和箱号实际数量大于0获取商品id和数量
     * @param ordersId
     * @return
     */
    List<ProcurementTask> getListByOrdersId(@Param("ordersId")Long ordersId,@Param("boxNo")String boxNo);

    /**
     * 查采买员所在社区Id为comunityId社区的采买任务,如有同一件商品,合在一起
     * @return
     */
    List<ProcurementTask> getCommunityProcurementList(ProcurementTask procurementTask);

    /**
     * 获取在社区同一个超市商品一样的所有的采买任务
     * @param goodsSkuId
     * @return
     */
    List<ProcurementTask> getProcurements(@Param("goodsSkuId")Integer goodsSkuId, @Param("communityId")Integer communityId);

    /**
     * 查询中此采买员所有的采买任务
     * @param procurTask
     * @return
     */
    List<ProcurementTask> getProcurementLists(ProcurementTask procurTask);

    /**
     * 流转中心任务 查询出未收货的商品列表
     * @param procurTask
     * @return
     */
    List<ProcurementTask> getNotReceivingGoods(ProcurementTask procurTask);
    /**
     * 流转中心任务 对未收货的商品进行收货操作
     * @param accessTime
     * @param goodsSkuId
     * @return
     */
    void confirmGoods(@Param("accessTime") Date accessTime,@Param("goodsSkuId")Integer goodsSkuId);
    /**
     * 流转中心任务 查询出未发货的商品列表
     * @param procurTask
     * @return
     */
    List<ProcurementTask> getNotDeliverGoods(ProcurementTask procurTask);
    /**
     * 流转中心任务 对收货的商品进行发货操作
     * @param communityId
     * @param goodsSkuId
     * @return
     */
    void confirmDeliverGoods(@Param("communityId") Integer communityId,@Param("goodsSkuId")Integer goodsSkuId);

}
