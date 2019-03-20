package com.linayi.service.goods;

import com.linayi.entity.goods.CateBrandVal;

public interface CateBrandValService {

    /**
     * 新增分类品牌属性值中间表
     * @param
     * @return
     */
    String addCateBrandVal(String categoryName, String brandName, String attrStr,Integer goodsSkuId);

    /**
     * 根据分类Id品牌Id属性值Id查询中间表
     * @param categoryId
     * @param brandId
     * @param valueId
     * @return
     */
    CateBrandVal getCateBrandVal(Integer categoryId, Integer brandId, Integer valueId);


}
