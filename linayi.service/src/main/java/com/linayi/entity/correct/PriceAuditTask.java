package com.linayi.entity.correct;

import com.linayi.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * price_audit_task
 * @author 
 */
@Data
public class PriceAuditTask extends BaseEntity {
    /**
     * [任务ID]
     */
    private Integer taskId;

    /**
     * [价格状态]   待审核：WAIT_AUDIT  已生效：AFFECTED
     */
    private String priceType;

    /**
     * []
     */
    private Long correctId;

    /**
     * [任务日期]
     */
    private Date taskDate;

    /**
     * [创建时间]
     */
    private Date createTime;


    //任务总数量
    private Integer totalQuantity;

    private Integer supermarketId;

    //已完成数量
    private Integer completeQuantity;
}