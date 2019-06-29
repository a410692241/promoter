package com.linayi.service.community;

import com.linayi.entity.community.SmallCommunityReq;

import java.util.List;

public interface SmallCommunityReqService {
    /**新增待添加小区
     * @param smallCommunityReq
     */
    void addSmallCommunityReq(SmallCommunityReq smallCommunityReq);

    /**获取待添加小区列表
     * @param smallCommunityReq
     * @return
     */
    List<SmallCommunityReq> getSmallCommunityReqList(SmallCommunityReq smallCommunityReq);

    void updateStatus(SmallCommunityReq smallCommunityReq);

    SmallCommunityReq get(SmallCommunityReq smallCommunityReq);

    void batchUpdateStatus(List<Integer> idList);
}
