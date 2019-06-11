package com.linayi.dao.promoter;

import java.util.List;

import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.promoter.PromoterOrderMan;

public interface OrderManMemberMapper {
    int insert(OrderManMember record);

    /**
     * 查询符合条件有效的下单员会员
     * @param orderManMember
     * @return
     */
    List<OrderManMember> getOrderManMemberList(OrderManMember orderManMember);

    /**
     * 通过下单员id获取OrderManMember列表
     * @param orderManId
     * @return
     */
    List<OrderManMember> getOrderManMemberListByOrderManId(PromoterOrderMan promoterOrderMan);

    List<OrderManMember> getOrderManMemberListByPromoterId(Integer promoterId);

    List<OrderManMember> selectByAll(OrderManMember orderManMember);

    int updateByPrimaryKeySelective(OrderManMember record);

}