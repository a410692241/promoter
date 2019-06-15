package com.linayi.service.goods.impl;

import com.linayi.service.goods.SkuClickNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.linayi.entity.goods.SkuClickNum;

import java.util.*;
import java.util.stream.Collectors;

import com.linayi.dao.goods.SkuClickNumMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkuClickNumServiceImpl implements SkuClickNumService {

    @Resource
    private SkuClickNumMapper skuClickNumMapper;

    @Override
    @Transactional
    public void updateClickNum(Long goodsSkuId) {
        SkuClickNum record = new SkuClickNum();
        record.setGoodsSkuId(goodsSkuId);
        record.setClickDate(new Date());
        SkuClickNum skuClickNum = skuClickNumMapper.selectByAll(record).stream().findFirst().orElse(null);
        //没有今日该商品的点击记录,新增
        if (skuClickNum == null) {
            record.setNum(1);
            skuClickNumMapper.insert(record);
        } else {
            //更改最新的点击数
            skuClickNum.setNum(skuClickNum.getNum() + 1);
            skuClickNumMapper.updateByPrimaryKey(skuClickNum);
        }
    }


    @Override
    public List<Long> getSkuIdsByClientNum(SkuClickNum skuClickNum) {
        List<SkuClickNum> skuClickNums = skuClickNumMapper.selectConcatNum(skuClickNum);
        List<Long> result = skuClickNums.stream().collect(Collectors.mapping(SkuClickNum::getGoodsSkuId, Collectors.toList()));
        return result;
    }

}
