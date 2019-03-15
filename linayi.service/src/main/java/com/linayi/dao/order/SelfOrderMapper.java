package com.linayi.dao.order;


import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.entity.supermarket.Supermarket;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface SelfOrderMapper {

    /**
     * 获取自定义下单了列表
     *
     * @return
     */
    List<SelfOrder> getList(SelfOrder selfOrder);

    /**
     * 自定义下单三框插入
     *
     * @param selfOrder
     * @return
     */
    Integer insertUserMessage(SelfOrder selfOrder);

    /**
     * 根据超市id获取超市信息
     *
     * @param supermarketId
     * @return
     */
    Supermarket findSupermarketById(Integer supermarketId);

    /**
     * 根据用户Id获取自定义列表
     *
     * @param userId
     * @return
     */
    List<SelfOrder> getSelfOrderByUserId(GoodsSku goodsSku);

    /**
     * 根据主键id查询自定义下单表
     *
     * @param selfOrderId
     * @return
     */
    SelfOrder selectByPrimaryKey(Long selfOrderId);

    /**
     * 根据主键id修改状态并更新时间
     *
     * @param selfOrderId
     * @param status
     * @param updateTime
     * @return
     */
    Integer updateSelfOrderStatusByPrimaryKey(@Param("selfOrderId") Long selfOrderId, @Param("status") String status, @Param("updateTime") Date updateTime);

    SelfOrder getSelfOrder(SelfOrder selfOrder);

    Integer updateSelfOrder(SelfOrder selfOrder);

    List<Map> listSelfOrderMessage(@Param("selfOrderId") Long selfOrderId);

}