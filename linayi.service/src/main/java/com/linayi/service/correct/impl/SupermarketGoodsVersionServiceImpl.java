package com.linayi.service.correct.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.dao.correct.SupermarketGoodsVersionMapper;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.service.correct.SupermarketGoodsVersionService;

@Service
public class SupermarketGoodsVersionServiceImpl implements SupermarketGoodsVersionService {
    @Autowired
    private SupermarketGoodsVersionMapper supermarketGoodsVersionMapper;

	@Override
	public int insert(SupermarketGoodsVersion record) {
		return supermarketGoodsVersionMapper.insert(record);
	}
	
	@Override
	public int updateVersion(SupermarketGoodsVersion record){
		return supermarketGoodsVersionMapper.updateVersion(record);
	}

	@Override
	public SupermarketGoodsVersion getVersion(SupermarketGoodsVersion record) {
		return supermarketGoodsVersionMapper.getVersion(record);
	}

}
