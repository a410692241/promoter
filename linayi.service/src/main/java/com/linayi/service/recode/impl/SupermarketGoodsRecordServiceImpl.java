package com.linayi.service.recode.impl;

import com.linayi.dao.recode.SupermarketGoodsRecordMapper;
import com.linayi.entity.recode.SupermarketGoodsRecord;
import com.linayi.service.recode.SupermarketGoodsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class SupermarketGoodsRecordServiceImpl implements SupermarketGoodsRecordService {
    @Autowired
    private SupermarketGoodsRecordMapper supermarketGoodsRecordMapper;


    @Transactional
    @Override
    public void insert(SupermarketGoodsRecord supermarketGoodsRecord) {
        if (supermarketGoodsRecordMapper.getSupermarketGoodsRecord(supermarketGoodsRecord)==null){
            supermarketGoodsRecord.setCreateTime(new Date());
            supermarketGoodsRecordMapper.insert(supermarketGoodsRecord);
        }
    }
}
