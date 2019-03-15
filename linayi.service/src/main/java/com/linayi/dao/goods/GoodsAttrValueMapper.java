package com.linayi.dao.goods;

import com.linayi.entity.goods.GoodsAttrValue;

import java.util.List;

public interface GoodsAttrValueMapper {
    int insert(GoodsAttrValue record);

    int insertSelective(GoodsAttrValue record);

    List<GoodsAttrValue> getGoodsAttrValueByGoodsId(Long goodsSkuId);

    void deleteById(Integer attrValueId);
}