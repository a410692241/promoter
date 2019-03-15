package com.linayi.service.goods.impl;

import com.linayi.dao.goods.GoodsAttrValueMapper;
import com.linayi.entity.goods.GoodsAttrValue;
import com.linayi.service.goods.GoodsAttrValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsAttrValueServiceImpl implements GoodsAttrValueService {
    @Resource
    private GoodsAttrValueMapper goodsAttrValueMapper;

    @Override
    public GoodsAttrValue addAttrValue(GoodsAttrValue goodsAttrValue) {
        goodsAttrValueMapper.insert(goodsAttrValue);
        return goodsAttrValue;
    }

    @Override
    public List<GoodsAttrValue> getGoodsAttrValueByGoodsId(Long goodsSkuId) {
        return goodsAttrValueMapper.getGoodsAttrValueByGoodsId(goodsSkuId);
    }
}
