package com.linayi.dao.promoter;

import java.util.List;

import com.linayi.entity.promoter.PromoterOrderMan;

import java.util.List;

public interface PromoterOrderManMapper {
    /**
     * 此推广商下所有有效的下单员
     * @param promoterOrderMan1
     * @return
     */
    List<PromoterOrderMan> getPromoterOrderManList(PromoterOrderMan promoterOrderMan1);

    int insert(PromoterOrderMan record);

    PromoterOrderMan getPromoterOrderMan(PromoterOrderMan promoterOrderMan);
    /**
     * 通过下单员Id获取PromoterOrderMan
     * @param orderManId
     * @return
     */
    PromoterOrderMan getPromoterOrderManByOrderManId(Integer orderManId);
    
    /**
     * 通过推广商id获取该推广商下的所有PromoterOrderMan
     * @param promoterId
     * @return
     */
    List<PromoterOrderMan> getOrderManListByPromoterId(PromoterOrderMan promoterOrderMan);
}