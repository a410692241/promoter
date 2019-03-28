package com.linayi.service.promoter;

import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.promoter.PromoterOrderMan;

import java.util.List;

public interface PromoterOrderManService {
    /*推广商首页*/
    PromoterOrderMan promoterIndex(PromoterOrderMan promoterOrderMan);

    /*我的团队-订单统计*/
    PromoterOrderMan myTeamOrderStatistics(PromoterOrderMan promoterOrderMan);

    /*我的团队-下单员列表*/
    List<PromoterOrderMan> orderManList(PromoterOrderMan promoterOrderMan);

    /*会员列表-订单统计*/
    PromoterOrderMan memberListOrderStatistics(PromoterOrderMan promoterOrderMan);

    /*会员列表-会员列表*/
    List<OrderManMember> memberList(PromoterOrderMan promoterOrderMan);


    /**
     * 统计用户的订单数 金额 会员数等
     *
     * @param manId
     * @param range 时间范围  全部:ALL 本月:MONTH
     * @param type  时间范围   代替顾客: CUSTOMER 所有: ALL  会员:VIP
     * @return
     */
    PromoterOrderMan getStatisALL(Integer manId, String range, String type);

    /**
     * 统计会员  订单数 金额
     *
     * @param orderManId
     * @param userId
     * @param range
     * @return
     */
    PromoterOrderMan getStatisVIP(Integer orderManId, Integer userId, String range);

}
