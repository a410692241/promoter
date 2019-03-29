package com.linayi.helper;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 财务结算：推广商结算
 */
@Service
public class PromoterSettleHelper {
    /**
     * 订单数>=100
     */
    private static final BigDecimal ORDER_QUANTITY = new BigDecimal("100");
    /**
     * 单笔订单金额>=100的所有订单金额：101+102 = 203
     */
    private static final BigDecimal IS_ORDER_AMOUNT = new BigDecimal("100");
    /**
     * 一级推广商基数
     */
    private static final BigDecimal LEVEL_ONE = new BigDecimal("350");
    /**
     * 二级推广商基数
     */
    private static final BigDecimal LEVEL_TWO = new BigDecimal("640");
    /**
     * 三级推广商基数
     */
    private static final BigDecimal LEVEL_THREE = new BigDecimal("800");
    /**
     * 一级推广商收益率
     */
    private static final BigDecimal YIELD_RATE_ONE = new BigDecimal("1.5");
    /**
     * 二级推广商收益率
     */
    private static final BigDecimal YIELD_RATE_TWO = new BigDecimal("1.8");
    /**
     * 三级推广商收益率
     */
    private static final BigDecimal YIELD_RATE_THREE = new BigDecimal("2");
    /**
     * 一级推广商单笔订单>=100收益率
     */
    private static final BigDecimal YIELD_RATE_ONE_AMOUNT = new BigDecimal("0.5");
    /**
     * 二级推广商单笔订单>=100收益率
     */
    private static final BigDecimal YIELD_RATE_TWO_AMOUNT = new BigDecimal("0.8");
    /**
     * 三级推广商单笔订单>=100收益率
     */
    private static final BigDecimal YIELD_RATE_THREE_AMOUNT = new BigDecimal("1");

    /**
     * 计算推广商收益
     *
     * @param promoterLevel 推广商等级
     * @param totalSum      订单数
     * @param orderAmount   单笔订单额
     * @return 收益
     */
    public static BigDecimal calculateEarnings(String promoterLevel, BigDecimal totalSum, BigDecimal orderAmount) {
        BigDecimal earnings = new BigDecimal("0.00");
        if ("1".equals(promoterLevel)) {
            // 单笔订单金额>=100的所有订单金额：101+102 = 203
            if (!(orderAmount.compareTo(IS_ORDER_AMOUNT) == -1)) {
                earnings = orderAmount.multiply(YIELD_RATE_ONE_AMOUNT);
            }
            // 订单数>=100
            if (!(totalSum.compareTo(ORDER_QUANTITY) == -1)) {
                // 大于等于100,基数+订单数*收益率
                earnings = LEVEL_ONE.add(totalSum.multiply(YIELD_RATE_ONE));
            } else {
                earnings = totalSum.multiply(YIELD_RATE_ONE);
            }
        } else if ("2".equals(promoterLevel)) {
            // 单笔订单金额>=100的所有订单金额：101+102 = 203
            if (!(orderAmount.compareTo(IS_ORDER_AMOUNT) == -1)) {
                earnings = orderAmount.multiply(YIELD_RATE_TWO_AMOUNT);
            }
            // 订单数>=100
            if (!(totalSum.compareTo(ORDER_QUANTITY) == -1)) {
                // 大于等于100,基数+订单数*收益率
                earnings = LEVEL_TWO.add(totalSum.multiply(YIELD_RATE_TWO));
            } else {
                earnings = totalSum.multiply(YIELD_RATE_TWO);
            }
        } else if ("3".equals(promoterLevel)) {
            // 单笔订单金额>=100的所有订单金额：101+102 = 203
            if (!(orderAmount.compareTo(IS_ORDER_AMOUNT) == -1)) {
                earnings = orderAmount.multiply(YIELD_RATE_THREE_AMOUNT);
            }
            // 订单数>=100
            if (!(totalSum.compareTo(ORDER_QUANTITY) == -1)) {
                // 大于等于100,基数+订单数*收益率
                earnings = LEVEL_THREE.add(totalSum.multiply(YIELD_RATE_THREE));
            } else {
                earnings = totalSum.multiply(YIELD_RATE_THREE);
            }
        }
        return earnings;
    }
}
