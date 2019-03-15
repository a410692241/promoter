package com.linayi.service.order;

import com.linayi.entity.order.OrdersGoods;

import java.util.List;

public interface OrdersGoodsService {
    /**
     * 修改订单商品状态
     * @param ordersGoods
     */
    void updateOrdersGoodsStatus(OrdersGoods ordersGoods);


    List<OrdersGoods> query(OrdersGoods ordersGoods);

    List<OrdersGoods> getNoProcumentTaskOrders();
}
