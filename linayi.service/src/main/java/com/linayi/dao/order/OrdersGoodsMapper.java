package com.linayi.dao.order;

import com.linayi.entity.order.OrdersGoods;

import java.util.List;

public interface OrdersGoodsMapper {
    int insert(OrdersGoods record);

    int insertSelective(OrdersGoods record);

    /**
     * @param ordersId
     * @return
     */
    List<OrdersGoods> getOrdersGoodsByOrdersId(Long ordersId);

    void updateOrdersGoodsById(OrdersGoods ordersGoods);

    OrdersGoods getOrdersGoodsById(Long ordersId);

    List<OrdersGoods> getOrdersGoodsByOrdersGoods(OrdersGoods ordersGoods);

    List<OrdersGoods> query(OrdersGoods ordersGoods);

    List<OrdersGoods> getNoProcumentTaskOrders();

    OrdersGoods getOrdersGoods(Integer goodsSkuId, long ordersId);

    /**
     * 进行中的改成已完成
     * @param ordersGoods
     */
    void updateOrdersGoodsCanceled(OrdersGoods ordersGoods);
}