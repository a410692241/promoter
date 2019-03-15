package com.linayi.service.goods;

import com.linayi.entity.goods.GoodsAttrValue;

import java.util.List;

public interface GoodsAttrValueService {
    /**
     * 新增商品属性值
     * @param goodsAttrValue
     * @return
     */
    GoodsAttrValue addAttrValue(GoodsAttrValue goodsAttrValue);

    /**
     * 根据商品id查询所有商品属性值中间表
     * @param goodsSkuId
     * @return
     */
    List<GoodsAttrValue> getGoodsAttrValueByGoodsId(Long goodsSkuId);
}
