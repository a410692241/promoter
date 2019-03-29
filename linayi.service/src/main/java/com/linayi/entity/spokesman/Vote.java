package com.linayi.entity.spokesman;

import java.util.Date;

public class Vote {
    private Integer voteId;

    private Integer userId;

    private Integer spokesmanId;

    private Date updateTime;

    private Date createTime;

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpokesmanId() {
        return spokesmanId;
    }

    public void setSpokesmanId(Integer spokesmanId) {
        this.spokesmanId = spokesmanId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}