package com.linayi.service.order;

import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.order.OrdersSku;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.util.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 新增订单
     * @param param
     */
    void addOrder(Map<String, Object> param);

    /**
     * 根据条件查询所有订单
     * @param orders
     * @return
     */
    Object getOrderList(Orders order);

    /**
     * 获取订单详情
     * @param orders
     * @return
     */
    Orders getOrderDetails(Orders orders, HttpServletRequest request);

    void updateOrderStatus(Orders orders);

    /**
     * 通过用户id查询已拒收和已退货的订单
     * @param userId
     * @return
     */
    List<Orders> getAfterSaleOrders(Integer userId);

    /**
     * 再来一单
     * @param orders
     */
    String againOrders(Orders orders);

    List<Orders> getOrderAll(Orders orders);

    /**
     * 根据订单id获取订单详情
     * @param ordersSku
     * @return
     */
    List<OrdersSku> getGoodsOrderSku(OrdersSku ordersSku);

    /**
     * 查询状态为采买中和已采买的采买任务
     * @param procurementTask
     * @return
     */
    List<ProcurementTask> selectProcuringByOrdersId(ProcurementTask procurementTask);

    /**
     * 获取所有订单详情
     * @param
     * @return
     */
    List<OrdersSku> getAllGoodsOrderSku(OrdersSku ordersSku);

    /**
     * 查询所有的采买任务
     * @return
     */
    List<ProcurementTask> selectAllProcurementTask(ProcurementTask procurementTask);

    /**
     * 根据订单id改成要改的状态
     * @param orders
     * @return
     */
    Integer updateOrderById(Orders orders);


    Object getBuyOrderList(OrdersGoods ordersGoods);
    /**
     * 获取超市价格Json
     * @return
     */
    OrdersSku getOrderSupermarketList(OrdersSku ordersSku);

    /**
     * 获取订单商品详情
     * @param userId
     * @param param
     * @return
     */
    OrdersGoods getOrderGoodsDetailsById(Integer userId, Map<String, Object> param);

    /**
     * 采买列表下拉数据
     * @param SupermarketList
     * @return
     */
    List<SupermarketGoods> showAllPurchase(OrdersSku SupermarketList);

    /**
     * 根据订单商品id查询采买任务
     * @param procurementTask
     * @return
     */
    List<ProcurementTask> selectSIdPriceByOgId(ProcurementTask procurementTask);

    /**
     * 订单商品按次高价购买
     * @param ordersSku
     */
    void buySecondHeigh(ProcurementTask procurementTask);

    /**
     * 订单商品取消购买
     * @param ordersSku
     */
    OrdersGoods cancelBuyGoods(OrdersSku ordersSku);

    OrdersGoods getOrderGoods(OrdersSku ordersSku);

    List<ProcurementTask> procurementTaskList(OrdersSku ordersSku);

    /**
     * 查询装箱列表
     */
    List<Orders> getBoxingList(Orders order);


    /**
     * 根据订单Id查询采买任务然后修改为已收货状态（通用）
     */
    void updateOrderReceivedStatus(Long ordersId);

    /**
     * 根据用户id或者收货地址id查询所有订单列表
     * @param orders
     * @return
     */
    PageResult<Orders> getOrdersList(Orders orders);

    /**
     * 采买任务订单列表
     * @param orders
     * @return
     */
    PageResult<Orders> getProcureOrderList(Orders orders);

    OrdersGoods generateOrdersGoods(Orders order, List<SupermarketGoods> supermarketGoodsList,List<SupermarketGoods> supermarketGoodsList1, Integer num, Integer goodsSkuId);
}
