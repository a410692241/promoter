package com.linayi.service.recode.impl;

import com.linayi.dao.recode.SupermarketGoodsRecordMapper;
import com.linayi.entity.recode.SupermarketGoodsRecord;
import com.linayi.service.recode.SupermarketGoodsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SupermarketGoodsRecordServiceImpl implements SupermarketGoodsRecordService {
    @Autowired
    private SupermarketGoodsRecordMapper supermarketGoodsRecordMapper;


    @Transactional
    @Override
    public void insert(SupermarketGoodsRecord supermarketGoodsRecord) {
        supermarketGoodsRecordMapper.insert(supermarketGoodsRecord);
    }
}
