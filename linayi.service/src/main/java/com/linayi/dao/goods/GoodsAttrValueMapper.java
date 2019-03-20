package com.linayi.dao.goods;

import com.linayi.entity.goods.GoodsAttrValue;

import java.util.List;

public interface GoodsAttrValueMapper {
    int insert(GoodsAttrValue record);

    int insertSelective(GoodsAttrValue record);

    List<GoodsAttrValue> getGoodsAttrValueByGoodsId(Long goodsSkuId);

    void deleteById(Integer goodsAttrValueId);

    /**
     * 通过商品Id删除商品属性
     * @param goodsSkuId
     */
    void deleteByGoodsSkuId(Integer goodsSkuId);

    /**
     * 根数商品Id和属性类别查询
     * @param goodsAttrValue
     * @return
     */
    List<GoodsAttrValue> getGoodsAttrValue(GoodsAttrValue goodsAttrValue);
}