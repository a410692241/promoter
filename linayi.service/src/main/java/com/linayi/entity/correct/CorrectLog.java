package com.linayi.entity.correct;

import java.util.Date;

public class CorrectLog {
    @Override
    public String toString() {
        return "CorrectLog{" +
                "correctLogId=" + correctLogId +
                ", correctId=" + correctId +
                ", operateStatus='" + operateStatus + '\'' +
                ", operatorId=" + operatorId +
                ", operatorType='" + operatorType + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    private Long correctLogId;

    private Long correctId;

    private String operateStatus;

    private Integer operatorId;

    private String operatorType;

    private Date createTime;

    public Long getCorrectLogId() {
        return correctLogId;
    }

    public void setCorrectLogId(Long correctLogId) {
        this.correctLogId = correctLogId;
    }

    public Long getCorrectId() {
        return correctId;
    }

    public void setCorrectId(Long correctId) {
        this.correctId = correctId;
    }

    public String getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(String operateStatus) {
        this.operateStatus = operateStatus == null ? null : operateStatus.trim();
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType == null ? null : operatorType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}