package com.linayi.service.goods;

import com.linayi.entity.goods.CommunityGoods;

public interface CommunityGoodsService {
    /**
     * 根据商品Id查社区商品
     * @param goodsSkuId
     * @return
     */
    CommunityGoods getCommunityGoodsByGoodsId(Integer goodsSkuId);

    CommunityGoods getCommunityGoods(CommunityGoods communityGoods);
}
