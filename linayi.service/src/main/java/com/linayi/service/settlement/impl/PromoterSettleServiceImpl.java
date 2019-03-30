package com.linayi.service.settlement.impl;

import com.linayi.dao.promoter.PromoterMapper;
import com.linayi.dto.PromoterSettleDTO;
import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.helper.PromoterSettleHelper;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.settlement.PromoterSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PromoterSettleServiceImpl implements PromoterSettleService {
    @Autowired
    private PromoterMapper promoterMapper;
    @Autowired
    private PromoterOrderManService promoterOrderManService;


    /**
     * 查询所有的推广商结算列表
     * [推广商信息，订单信息，当前推广商的收益]
     *
     * @return
     */
    @Override
    public List<PromoterSettleDTO> getAllPromoterDto(Orders orders) {
        orders = PromoterSettleHelper.getDefaultSearchTime(orders);
        BigDecimal profit = new BigDecimal("0.00");
        // 推广商列表
        List<PromoterSettleDTO> promoterSettleDTOList = promoterMapper.getAllPromoter(orders);
        PromoterOrderMan promoterOrderMan = null;
        if (promoterSettleDTOList != null && !promoterSettleDTOList.isEmpty()) {
            for (PromoterSettleDTO promoterSettleDTO : promoterSettleDTOList) {
                // 推广商下的所有订单
                promoterOrderMan = promoterOrderManService.getStatisALL(promoterSettleDTO.getOrderManId(), "ALL", "ALL", orders);
                if (promoterOrderMan != null) {
                    // 订单数
                    Integer sumOrderNo = promoterOrderMan.getNumberOfOrders();
                    //  推广商等级
                    String level = promoterSettleDTO.getPromoterLevel();
                    if (sumOrderNo == null || sumOrderNo == 0) {
                        promoterSettleDTO.setSumOrderNo(0);
                        promoterSettleDTO.setSumOrderAmount(new BigDecimal("0"));
                        promoterSettleDTO.setProfit(new BigDecimal("0"));
                    } else {
                        BigDecimal totalSum = new BigDecimal(promoterOrderMan.getTotalSum());
                        // 订单数
                        promoterSettleDTO.setSumOrderNo(sumOrderNo);
                        // 订单合计总额
                        promoterSettleDTO.setSumOrderAmount(totalSum.divide(new BigDecimal("100")));
                        // 单笔订单额>=100的合计：有可能等于0
                        BigDecimal orderAmount = BigDecimal.valueOf(promoterOrderMan.getTotalHundredSum()).divide(new BigDecimal("100"));
                        // 收益：订单收益+单笔订单额>=100合计后的收益
                        profit = PromoterSettleHelper.calculateEarnings(level, totalSum, orderAmount);
                    }
                }
                // TODO 被投诉或违规推广次数:暂时默认为0
                promoterSettleDTO.setViolationOfPromotionNo(0);
                promoterSettleDTO.setProfit(profit);
            }
        }
        return promoterSettleDTOList;
    }
}
