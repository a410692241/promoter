package com.linayi.sync.dao;

import com.linayi.sync.entity.TOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOrdersMapper {
    int insert(TOrders record);

    int insertSelective(TOrders record);

    List<TOrders> getOrderList(TOrders TOrders);

    int updateOrderById(TOrders TOrders);

    /**
     *
     * @param userId
     * @param refusReceive 已拒收
     * @param refunded 已退款
     * @return
     */
    List<TOrders> getAfterOrderList(@Param("userId") Integer userId, @Param("refusReceive") String refusReceive, @Param("refunded") String refunded);


    /**
     * 获取该采买员所在超市对应的订单
     * @param supermarketId
     * @return
     */
    List<TOrders> getBuyOrderList(Integer supermarketId);


    TOrders getOrderById(Long TOrdersId);
}