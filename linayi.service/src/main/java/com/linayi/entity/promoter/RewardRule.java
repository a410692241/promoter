package com.linayi.entity.promoter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * reward_rule
 * @author 
 */
public class RewardRule implements Serializable {
    private Integer rewardRuleId;

    /**
     * [规则描述]
     */
    private String ruleDescription;

    /**
     * [生效类型]'OR'代表满足该条件将视为满足所有条件
     */
    private String effectiveType;

    /**
     * [奖励金额]
     */
    private Integer rewardAmount;

    private Integer rewardId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getRewardRuleId() {
        return rewardRuleId;
    }

    public void setRewardRuleId(Integer rewardRuleId) {
        this.rewardRuleId = rewardRuleId;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getEffectiveType() {
        return effectiveType;
    }

    public void setEffectiveType(String effectiveType) {
        this.effectiveType = effectiveType;
    }

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}