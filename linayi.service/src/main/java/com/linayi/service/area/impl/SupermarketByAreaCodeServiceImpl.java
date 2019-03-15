package com.linayi.service.area.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.service.area.SupermarketService;

@Service
public class SupermarketByAreaCodeServiceImpl implements SupermarketService {

	@Resource
	private SupermarketMapper supermarketMapper;
	
	@Override
	public List<Supermarket> getSupermarketByAreaCode(String areaCode) {
		Supermarket supermarket = new Supermarket();
		supermarket.setAreaCode(areaCode);
		List<Supermarket> supermarketList = supermarketMapper.selectSupermarketByAreaCode(supermarket);
		return supermarketList;
	}
	
}
