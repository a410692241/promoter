package com.linayi.dao.goods;

import com.linayi.entity.goods.CateBrandVal;
import org.apache.ibatis.annotations.Param;

public interface CateBrandValMapper {
    int insert(CateBrandVal record);

    int insertSelective(CateBrandVal record);

    CateBrandVal getCateBrandVal(@Param("categoryId") Integer categoryId, @Param("brandId") Integer brandId, @Param("valueId") Integer valueId);
}