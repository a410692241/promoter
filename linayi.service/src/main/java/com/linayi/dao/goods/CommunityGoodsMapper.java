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

    /**
     * 通过id和网点id查询商品
     *
     * @param barcode
     * @param goodsSkuId
     * @return
     */
    CommunityGoods getCommunityGoodsByBarcode(@Param("goodsSkuId")Long goodsSkuId, @Param("communityId")Integer communityId);
}
