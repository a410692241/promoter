package com.linayi.entity.community;

import com.linayi.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "com.linayi.entity.community.SmallCommunityReq")
public class SmallCommunityReq extends BaseEntity {
    /**
     * 小区申请id
     */
    @ApiModelProperty(value = "小区申请id")
    private Integer smallCommunityReqId;

    @ApiModelProperty(value = "null")
    private String smallCommunity;

    /**
     * PROCESSED:已处理;NOTVIEWED:未查看
     */
    @ApiModelProperty(value = "PROCESSED:已处理;NOTVIEWED:未查看")
    private String status;

    /**
     * 创建时间
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date startTime;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date endTime;

    public Integer getSmallCommunityReqId() {
        return smallCommunityReqId;
    }

    public void setSmallCommunityReqId(Integer smallCommunityReqId) {
        this.smallCommunityReqId = smallCommunityReqId;
    }

    public String getSmallCommunity() {
        return smallCommunity;
    }

    public void setSmallCommunity(String smallCommunity) {
        this.smallCommunity = smallCommunity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}