package com.linayi.service.order;

import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.User;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;

public interface SelfOrderService {

    List<SelfOrder> getList(SelfOrder selfOrder);

    /**
     * 自定义下单三框插入
     *
     * @param selfOrder
     * @return
     */
    SelfOrder insertUserMessage(SelfOrder selfOrder);

    /**
     * 根据超市id获取超市信息
     *
     * @param supermarketId
     * @return
     */
    Supermarket findSupermarketById(Integer supermarketId);

    /**
     * 封装信息
     *
     * @param selfOrder
     * @param uid
     * @return
     */
    void insertAllUserMessage(SelfOrder selfOrder);


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
     * @return
     */
    Integer updateSelfOrderStatusByPrimaryKey(Long selfOrderId, String status);

    SelfOrder getSelfOrder(SelfOrder selfOrder);

    SelfOrder updateSelfOrder(SelfOrder selfOrder);

    SelfOrder sharePrice(SelfOrder selfOrder);

    List<Map> listSelfOrderMessage(Long selfOrderId);

    List<GoodsSku> searchGoods(GoodsSku goodsSku);

    void turnSelfOrderToOrder(
            Long selfOrderId,
            Integer userId,
            Integer goodsSkuId,
            Integer num,
            Integer amount,
            Integer saveAmount,
            Integer ServiceFee,
            Integer extraFee
    );

    User selectUserById(Integer userId);

}
