package com.linayi.dao.goods;
import java.util.List;

import com.linayi.entity.goods.SkuClickNum;
import java.util.Date;
import org.apache.ibatis.annotations.Param;

public interface SkuClickNumMapper {
    int deleteByPrimaryKey(@Param("goodsSkuId") Integer goodsSkuId, @Param("clickDate") Date clickDate);

    int insert(SkuClickNum record);

    int insertSelective(SkuClickNum record);

    SkuClickNum selectByPrimaryKey(@Param("goodsSkuId") Integer goodsSkuId, @Param("clickDate") Date clickDate);

    List<SkuClickNum> selectByAll(SkuClickNum skuClickNum);

    int updateByPrimaryKeySelective(SkuClickNum record);

    int updateByPrimaryKey(SkuClickNum record);
}