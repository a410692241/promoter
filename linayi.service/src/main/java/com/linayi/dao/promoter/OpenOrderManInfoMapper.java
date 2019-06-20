package com.linayi.dao.promoter;

import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.entity.promoter.PromoterOrderMan;

import java.util.List;

public interface OpenOrderManInfoMapper {
    int insert(OpenOrderManInfo record);

    /**
     * 根据id查询开通下单员信息
     * @param openOrderManInfoId
     * @return
     */
    OpenOrderManInfo getOpenOrderManInfoById(Integer openOrderManInfoId);


    /**
     * 家庭服务师列表(新)
     * @return
     */
    List<PromoterOrderMan> getOpenOrderManInfoList(PromoterOrderMan promoterOrderMan);
}