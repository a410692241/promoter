package com.linayi.entity.promoter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order_man_reward
 * @author 
 */
public class OrderManReward implements Serializable {
    private Integer orderManRewardId;

    private Integer orderManId;

    private Integer rewardRuleId;

    /**
     * [实际奖励金额]
     */
    private Integer actualAmount;

    /**
     * [状态]INIT初始化；FINISH完成；SETTLEMENT已结算
     */
    private String status;

    /**
     * [结算时间]
     */
    private Date settlementTime;

    /**
     * [完成时间]
     */
    private Date finishTime;

    /**
     * [创建时间]
     */
    private Date createTime;

    private String ruleDescription;

    private static final long serialVersionUID = 1L;

    public Integer getOrderManRewardId() {
        return orderManRewardId;
    }

    public void setOrderManRewardId(Integer orderManRewardId) {
        this.orderManRewardId = orderManRewardId;
    }

    public Integer getOrderManId() {
        return orderManId;
    }

    public void setOrderManId(Integer orderManId) {
        this.orderManId = orderManId;
    }

    public Integer getRewardRuleId() {
        return rewardRuleId;
    }

    public void setRewardRuleId(Integer rewardRuleId) {
        this.rewardRuleId = rewardRuleId;
    }

    public Integer getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }
}