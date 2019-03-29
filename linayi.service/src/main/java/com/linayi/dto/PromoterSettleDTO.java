package com.linayi.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author czw
 */
@Data
public class PromoterSettleDTO {
    //推广商下单员ID
    private Integer promoterId;
    //订单Id也是订单编号
    private Long ordersId;
    //推广商下单员名
    private String name;
    //推广商下单员等级
    private String promoterLevel;
    //订单数
    private Integer sumOrderNo;
    // 订单总额
    private BigDecimal sumOrderAmount;
    // 被投诉或违规推广次数
    private Integer violationOfPromotionNo;
    //推广商收益ID
    private Integer promoterSettleId;
    // 推广商收益
    private BigDecimal profit;
    // 收益是否结算(true-已结算，false-未结算)
    private String profitIsBack;
}
