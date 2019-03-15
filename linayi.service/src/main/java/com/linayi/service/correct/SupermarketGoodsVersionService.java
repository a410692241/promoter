package com.linayi.service.correct;

import com.linayi.entity.correct.SupermarketGoodsVersion;

public interface SupermarketGoodsVersionService {
    
    int insert(SupermarketGoodsVersion record);
    
    int updateVersion(SupermarketGoodsVersion record);
    
    SupermarketGoodsVersion getVersion(SupermarketGoodsVersion record);
}
