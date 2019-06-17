package com.linayi.service.goods;

import org.springframework.stereotype.Service;
import com.linayi.entity.goods.SkuClickNum;

import java.util.HashMap;
import java.util.Map;

@Service
public interface SkuClickNumService{
    /**增加指定商品的点击量
     * @param goodsSkuId
     */
    void updateClickNum(Long goodsSkuId);

    /**获取点击量从多到少的某个日期段的商品id
     * 可根据参数设置时间段和前多少个商品
     * @return
     * @param skuClickNum
     */
    Map<Long, Integer> getSkuIdsByClientNum(SkuClickNum skuClickNum);

}
