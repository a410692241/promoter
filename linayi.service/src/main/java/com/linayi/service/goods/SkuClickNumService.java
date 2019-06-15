package com.linayi.service.goods;

import com.linayi.entity.goods.GoodsSku;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.linayi.entity.goods.SkuClickNum;
import java.util.Date;
import java.util.List;

import com.linayi.dao.goods.SkuClickNumMapper;
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
    List<Long> getSkuIdsByClientNum(SkuClickNum skuClickNum);

}
