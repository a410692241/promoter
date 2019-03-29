package com.linayi.service.promoter.impl;

import com.linayi.dao.promoter.PromoterMapper;
import com.linayi.dao.promoterSettle.PromoterSettleMapper;
import com.linayi.dto.PromoterSettleDTO;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.helper.PromoterSettleHelper;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.promoter.PromoterSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PromoterSettleServiceImpl implements PromoterSettleService {
    @Autowired
    private PromoterMapper promoterMapper;
    @Autowired
    private PromoterSettleMapper promoterSettleMapper;
    @Autowired
    private PromoterOrderManService promoterOrderManService;


    /**
     * 查询所有的推广商结算列表
     * [推广商信息，订单信息，当前推广商的收益]
     *
     * @return
     */
    @Override
    public List<PromoterSettleDTO> getAllPromoterDto() {
        BigDecimal profit = new BigDecimal("0.00");
        // 推广商列表
        List<PromoterSettleDTO> promoterSettleDTOList = promoterMapper.getAllPromoter();
        PromoterOrderMan promoterOrderMan = null;
        if (promoterSettleDTOList != null && !promoterSettleDTOList.isEmpty()) {
            for (PromoterSettleDTO promoterSettleDTO : promoterSettleDTOList) {
                // 推广商下的所有订单
                promoterOrderMan = promoterOrderManService.getStatisALL(promoterSettleDTO.getPromoterId(), "ALL", "ALL");
                if (promoterOrderMan != null) {
                    if (promoterSettleDTO.getPromoterId().equals(promoterOrderMan.getOrderManId())) {
                        // 订单数
                        promoterSettleDTO.setSumOrderNo(promoterOrderMan.getNumberOfOrders());
                        // 订单总额
                        promoterSettleDTO.setSumOrderAmount(BigDecimal.valueOf(promoterOrderMan.getTotalSum()));
                        // TODO 被投诉或违规推广次数:暂时默认为0
                        promoterSettleDTO.setViolationOfPromotionNo(0);
                        //  推广商等级
                        String level = promoterSettleDTO.getPromoterLevel();
                        // 订单数
                        BigDecimal orderAmount = BigDecimal.valueOf(promoterOrderMan.getTotalHundredSum());
                        // 单笔订单额>=100的合计：有可能等于0
                        BigDecimal totalSum = new BigDecimal(promoterOrderMan.getTotalSum());
                        // 收益：订单收益+单笔订单额>=100合计后的收益
                        profit = PromoterSettleHelper.calculateEarnings(level, totalSum, orderAmount);
                    }
                }
                promoterSettleDTO.setProfit(profit);
            }
            // 将当前记录插入到结算表
//            PromoterSettle promoterSettle = new PromoterSettle();
//            promoterSettleMapper.insertSelective(promoterSettle);
        }
        return promoterSettleDTOList;
    }
}
