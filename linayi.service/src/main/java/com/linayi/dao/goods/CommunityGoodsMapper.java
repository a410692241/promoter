package com.linayi.dao.goods;

import org.apache.ibatis.annotations.Param;

import com.linayi.entity.goods.CommunityGoods;

import java.util.List;

public interface CommunityGoodsMapper {
    int insert(CommunityGoods record);

    int insertSelective(CommunityGoods record);

    CommunityGoods getCommunityGoods(CommunityGoods communityGoods);


    /**
             * 通过社区id删除社区商品表信息
     *
     * @param communityId
     * @return
     */
    Integer delectCommunityGoods(@Param("communityId")Integer communityId ,@Param("goodsSkuId")Integer goodsSkuId);

    CommunityGoods getCommunityGoodsByGoodsId(Integer goodsSkuId);

    List<CommunityGoods> getAllCommunityGood();
}
