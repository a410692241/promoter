package com.linayi.service.goods.impl;

import com.linayi.service.goods.SkuClickNumService;
import org.springframework.beans.factory.wiring.BeanWiringInfo;
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
    public Map<Long, Integer> getSkuIdsByClientNum(SkuClickNum skuClickNum) {
        Date startTime = skuClickNum.getStartTime();
        Date endTime = skuClickNum.getEndTime();
        //时间清为当天00:00:00
        if (startTime != null && endTime != null) {
            skuClickNum.setStartTime(setTimeZero(startTime));
            skuClickNum.setEndTime(setTimeZero(endTime));
        }

        List<SkuClickNum> skuClickNums = skuClickNumMapper.selectConcatNum(skuClickNum);
        //转化集合key=goodsSKuId,value=clickNum的map
        HashMap<Long, Integer> map = new LinkedHashMap<>();
        skuClickNums.parallelStream().collect(Collectors.groupingBy(SkuClickNum::getGoodsSkuId,
                Collectors.mapping(SkuClickNum::getNum, Collectors.toList()))).forEach((k, v) -> {
            Integer num = v.stream().findFirst().orElse(null);
            map.put(k, num);
        });
        //对map通过clickNum进行排序
        HashMap<Long, Integer> sortedMap = new LinkedHashMap<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue((i1, i2) -> i2 - i1))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    private Date setTimeZero(Date startTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

}
