package com.linayi.entity.promoter;

import java.io.Serializable;
import java.util.Date;

/**
 * reward
 * @author 
 */
public class Reward implements Serializable {
    /**
     * [奖励id]
     */
    private Integer rewardId;

    /**
     * [奖励名称]
     */
    private String rewardName;

    /**
     * [父级规则id]
     */
    private Integer parentId;

    /**
     * [创建时间]
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}