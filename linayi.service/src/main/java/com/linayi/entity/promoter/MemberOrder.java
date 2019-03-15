package com.linayi.entity.promoter;

public class MemberOrder {
    private Integer memberOrderId;
    private Integer openMemberId;
    private Long ordersId;

    public Integer getMemberOrderId() {
        return memberOrderId;
    }

    public void setMemberOrderId(Integer memberOrderId) {
        this.memberOrderId = memberOrderId;
    }

    public Integer getOpenMemberId() {
        return openMemberId;
    }

    public void setOpenMemberId(Integer openMemberId) {
        this.openMemberId = openMemberId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }
}
