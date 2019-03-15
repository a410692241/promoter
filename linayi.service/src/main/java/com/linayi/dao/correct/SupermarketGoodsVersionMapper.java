package com.linayi.dao.correct;

import com.linayi.entity.correct.SupermarketGoodsVersion;

public interface SupermarketGoodsVersionMapper {
    int insert(SupermarketGoodsVersion record);
    
    int updateVersion(SupermarketGoodsVersion record);
    
    SupermarketGoodsVersion getVersion(SupermarketGoodsVersion record);
}