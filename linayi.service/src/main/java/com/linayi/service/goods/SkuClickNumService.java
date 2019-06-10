package com.linayi.service.goods;

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
    void updateClickNum(Integer goodsSkuId);

    /**获取从多到少的某个日期段的商品id
     * @param date
     * @return
     */
    List<Integer> getSkuIdsByClientNum(SkuClickNum skuClickNum);
}
