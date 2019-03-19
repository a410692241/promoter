package com.linayi.dao.order;

import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdersMapper {
    int insert(Orders record);

    int insertSelective(Orders record);

    List<Orders> getOrderList(Orders orders);

    /**
     * 获取下单员下会员所下的订单数
     * @param orders
     * @return
     */
    List<Orders> getALLOrderByOrderMan(Orders orders);

    int updateOrderById(Orders orders);

    /**
     *
     * @param userId
     * @param refusReceive 已拒收
     * @param refunded 已退款
     * @return
     */
    List<Orders> getAfterOrderList(@Param("userId") Integer userId, @Param("refusReceive") String refusReceive, @Param("refunded") String refunded);

    /**
     * 根据订单id获取订单详情
     * @param ordersSku
     * @return
     */
    List<OrdersSku> getGoodsOrderSku(OrdersSku ordersSku);

    /**
     * 获取该采买员所在超市对应的订单
     * @param supermarketId
     * @return
     */
    List<Orders> getBuyOrderList(Integer supermarketId);

    /**
     * 获取所有订单详情
     * @param
     * @return
     */
    List<OrdersSku> getAllGoodsOrderSku(OrdersSku ordersSku);

    /**
     * 获取超市价格Json
     * @return
     */
    OrdersSku getOrderSupermarketList(OrdersSku ordersSku);

    Orders getOrderById(Long ordersId);
    
    /**
     * 根据社区id和社区状态为已收货  查询订单表
     * @param order
     * @return
     */
    List<Orders> getOrderListByCommunityIdAndRECEIVED(Orders order);
    /**
     * 通过订单Id修改状态为配送中
     * @param ordersId
     * @return
     */
    Integer updateStatusByOrdersId(Long ordersId);

    /**
     * 通过订单Id修改状态为配送完成
     * @param ordersId
     * @return
     */
    Integer updateFinishedStatusByOrdersId(Long ordersId);
    
    
    /**
     * 通过订单ordersId查询订单信息
     * @param ordersId
     * @return
     */
   Orders getOrdersByOrdersId(Long ordersId);
   
   /**
    * 通过订单Id修改状态为已装箱PACKED,插入配送员id
    * @param ordersId
    * @return
    */
   Integer updateStatusByOrdersId2(Long ordersId);

    /**
     * 通过配送员ID和配送状态查询配送订单列表
     * @param orders
     * @return
     */
   List<Orders> getOrdersBySmallCommunityIdAndStatus(Orders orders);

    /**
     * 根据userId和成功状态查询派送任务
     * @param orders
     * @return
     */
    List<Orders> getOrdersByUserIdAndFinishStatus(Orders orders);

    /**
     * 根据ordersId修改订单状态
     * @param orders
     * @return
     */
    Integer updateOrdersCommunityStatusByOrdersId(Orders orders);
    
    List<Orders> getOrderListDelivery(Orders orders);

    /**
     * 查询下单员会员下的单数
     * @param orders
     * @return
     */
    List<Orders> getOrdersByOrderMan(Orders orders);

    List<Orders> getALLOrder(Orders orders);

    /**
     * 查询推广商下所有的代下单
     * @param orders
     * @return
     */
    List<Orders> getOrderByPromoter(Orders orders);

    /**
     * 获取下单员或推广商所下的订单
     * @param orders
     * @return
     */
    List<Orders> getOrdersByPromoter(Orders orders);

    /**
     * 通过用户的会员的Id和下单员id查找订单
     * @param orders
     * @return
     */
    List<Orders> getOrdersByUserIdAndOrderManId(Orders orders);
}