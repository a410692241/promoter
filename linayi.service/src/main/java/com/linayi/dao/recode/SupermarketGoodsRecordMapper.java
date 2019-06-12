package com.linayi.dao.recode;

import com.linayi.entity.recode.SupermarketGoodsRecord;

public interface SupermarketGoodsRecordMapper {
    int deleteByPrimaryKey(Integer supermarketGoodsRecordId);

    int insert(SupermarketGoodsRecord record);

    int insertSelective(SupermarketGoodsRecord record);

    SupermarketGoodsRecord selectByPrimaryKey(Integer supermarketGoodsRecordId);

    int updateByPrimaryKeySelective(SupermarketGoodsRecord record);

    int updateByPrimaryKey(SupermarketGoodsRecord record);

    SupermarketGoodsRecord getSupermarketGoodsRecord(SupermarketGoodsRecord supermarketGoodsRecord);
}