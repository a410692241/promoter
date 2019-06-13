package com.linayi.entity.procurement;

import java.util.Date;

public class ProcureMergeTask {
    private Long procureMergeTaskId;
    private Date startTime;
    private Date createTime;
    private String procureMergeNo;



    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProcureMergeNo() {
        return procureMergeNo;
    }

    public void setProcureMergeNo(String procureMergeNo) {
        this.procureMergeNo = procureMergeNo;
    }


    public Long getProcureMergeTaskId() {
        return procureMergeTaskId;
    }

    public void setProcureMergeTaskId(Long procureMergeTaskId) {
        this.procureMergeTaskId = procureMergeTaskId;
    }
}
