package com.linayi.dao.promoter;

import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.entity.promoter.PromoterOrderMan;

import java.util.List;

import java.util.List;

public interface OpenOrderManInfoMapper {
    int insert(OpenOrderManInfo record);

    /**
     * 根据id查询开通下单员信息
     * @param openOrderManInfoId
     * @return
     */
    OpenOrderManInfo getOpenOrderManInfoById(Integer openOrderManInfoId);

    List<OpenOrderManInfo> getOpenOrderManInfoByEndTime(Integer userId);


    /**
     * 家庭服务师列表(新)
     * @return
     */
    List<PromoterOrderMan> getOpenOrderManInfoList(PromoterOrderMan promoterOrderMan);

    List<OpenOrderManInfo> getOpenOrderManInfoByOrderManId(Integer orderManId);


    /**
     * 首页数据统计
     * @param promoterOrderMan
     * @return
     */
   PromoterOrderMan getPersonalOrder(Integer userId);

    /**
     * 代顾客下单数据统计
     * @return
     */
    PromoterOrderMan getPersonalOrderProfit(PromoterOrderMan promoterOrderMan);

    /**
     * 根据order_man_id和sales_id查询家庭服务师信息
     * @param promoterOrderMan
     * @return
     */
    List<OpenOrderManInfo> selectByOrderManId(OpenOrderManInfo openOrderManInfo);

    /**
     * 修改下单员表信息
     * @param openOrderManInfo
     * @return
     */
    Integer updateOpenOrderManInfo(OpenOrderManInfo openOrderManInfo);


    /**
     * 获取下级会员列表
     * @param
     * @return
     */
    List<PromoterOrderMan> getMemberData(PromoterOrderMan PromoterOrderMan);

    /**
     * 根据家庭服务师id获取订单数据和date
     * @return
     */
    PromoterOrderMan getOrderManData(PromoterOrderMan PromoterOrderMan);


    /**
     * 会员订单列表
     * @param PromoterOrderMan
     * @return
     */
    List<Orders> getMemberOrderList(PromoterOrderMan PromoterOrderMan);
}