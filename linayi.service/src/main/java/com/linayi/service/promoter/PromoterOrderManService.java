package com.linayi.service.promoter;

import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.user.AuthenticationApply;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    PromoterOrderMan getStatisALL(Integer manId, String range, String type, Orders orders);

    /**
     * 统计会员  订单数 金额
     *
     * @param orderManId
     * @param userId
     * @param range
     * @return
     */
    PromoterOrderMan getStatisVIP(Integer orderManId, Integer userId, String range);
    /**
     * 推广商发展下单员
     * @param openOrderManInfo 需传推广商ID，下单员ID，下单员身份，下单员等级
     */
    void openOrderManByPromoter(OpenOrderManInfo openOrderManInfo);

    /**
     * 后台管理系统 审核通过家庭服务师
     * @param userId 用户ID
     * @param promoterId 推广商ID
     * @param identity 推广商类型
     */
    void applyOrderManInWeb(String status,Integer applyId,Integer userId,Integer promoterId,String identity);

    /**
     * 邀请家庭服务师（扫二维码版本）
     * @param apply
     * @param file
     * @return
     */
    public void inviteOrderMan(AuthenticationApply apply, MultipartFile[] file) throws Exception;

    /**
     * 审核家庭服务师（2019.6.22更新）
     * @param apply
     */
    void auditOrderMan(AuthenticationApply apply);


    /**
     * 家庭服务师列表(新)
     * @return
     */
    List<PromoterOrderMan> getOpenOrderManInfoList(PromoterOrderMan promoterOrderMan);

    /**
     * 我的团队订单列表
     * @param promoterOrderMan
     * @return
     */
    PromoterOrderMan getOpenOrderManOrderList(PromoterOrderMan promoterOrderMan);

    /**
     * 首页数据统计
     * @param promoterOrderMan
     * @return
     */
    PromoterOrderMan getIndexData(PromoterOrderMan promoterOrderMan);


    /**
     * 代顾客下单数据统计
     * @return
     */
    PromoterOrderMan getPersonalOrderProfit(PromoterOrderMan promoterOrderMan);


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
     * 通过用户id获取会员和家庭服务师身份（根据结束时间查询身份）
     * @param userId
     * @return
     */
    Map getMemberAndOrderMan(Integer userId);


    /**
     * 会员订单列表
     * @param PromoterOrderMan
     * @return
     */
    List<Orders> getMemberOrderList(PromoterOrderMan PromoterOrderMan);

    /**
     * 会员订单统计
     * @param PromoterOrderMan
     * @return
     */
    PromoterOrderMan getMemberOrderData(PromoterOrderMan PromoterOrderMan);
}
