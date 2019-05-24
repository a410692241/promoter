package com.linayi.dao.promoter;

import com.linayi.entity.promoter.OpenOrderManInfo;

public interface OpenOrderManInfoMapper {
    int insert(OpenOrderManInfo record);

    /**
     * 根据id查询开通下单员信息
     * @param openOrderManInfoId
     * @return
     */
    OpenOrderManInfo getOpenOrderManInfoById(Integer openOrderManInfoId);
}