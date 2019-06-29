package com.linayi.entity.promoter;

import com.linayi.entity.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class    OpenOrderManInfo extends BaseEntity {
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

    private String salesName;

    private String nickName;

    private String sex;

    private String mobile;

    private String isOrderMan;

    private String realName;

    /**
     * 查询创建时间起
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTimeStart;

    /**
     * 查询时间止
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTimeEnd;

}
