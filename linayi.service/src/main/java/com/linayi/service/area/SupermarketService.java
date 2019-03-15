package com.linayi.service.area;

import java.util.List;

import com.linayi.entity.supermarket.Supermarket;

public interface SupermarketService {

	List<Supermarket> getSupermarketByAreaCode(String areaCode);
	
}
