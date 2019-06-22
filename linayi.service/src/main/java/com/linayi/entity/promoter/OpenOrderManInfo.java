package com.linayi.entity.promoter;

import lombok.Data;

import java.util.Date;

@Data
public class OpenOrderManInfo {
    private Integer openOrderManInfoId;

    private Integer promoterId;

    private Integer orderManId;

    private String identity;

    private String orderManLevel;

    private Date startTime;

    private Date endTime;

    private Date updateTime;

    private Date createTime;

    private String parentType;

    private Integer salesId;

}
