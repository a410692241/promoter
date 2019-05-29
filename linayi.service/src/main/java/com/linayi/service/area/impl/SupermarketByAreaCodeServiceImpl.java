package com.linayi.service.area.impl;

import java.util.*;

import javax.annotation.Resource;

import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.area.SmallCommunityFullName;
import com.linayi.entity.supermarket.SupermarketFullName;
import com.linayi.service.order.OrderService;
import com.linayi.util.CheckUtil;
import com.linayi.util.PageResult;
import com.linayi.vo.promoter.PromoterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.service.area.SupermarketService;

@Service
public class SupermarketByAreaCodeServiceImpl implements SupermarketService {

	@Resource
	private SupermarketMapper supermarketMapper;
	@Autowired
	private OrderService orderService;

	@Override
	public List<Supermarket> getSupermarketByAreaCode(String areaCode) {
		Supermarket supermarket = new Supermarket();
		supermarket.setAreaCode(areaCode);
		List<Supermarket> supermarketList = supermarketMapper.selectSupermarketByAreaCode(supermarket);
		return supermarketList;
	}

	@Override
	public PageResult<SupermarketFullName> getSupermarketByKey(PromoterVo.SearchSmallCommunityByKey search) {
		String key = search.getKey();
		Integer currentPage = search.getCurrentPage();
		Integer pageSize = search.getPageSize();
		List<SupermarketFullName> supermarketFullNames = new ArrayList<>();
		//防止初始化超市列表加载过慢
		if (CheckUtil.isNullEmpty(key)) {
			return new PageResult<>(supermarketFullNames,0);
		}
		Supermarket supermarket = new Supermarket();
		supermarket.setName(key);
		supermarket.setCurrentPage(currentPage);
		supermarket.setPageSize(pageSize);
		//模糊查询关键字的超市列表
		List<Supermarket> supermarkets = supermarketMapper.selectAll(supermarket);
		List<SupermarketFullName> supermarketFullNameList = new ArrayList<>();
		supermarkets.stream().forEach(item -> {
			SupermarketFullName supermarketFullName = new SupermarketFullName();

			supermarketFullName.setSupermarketId(item.getSupermarketId());

			//设置超市省市区全名
			String areaCode = item.getAreaCode();
			String name = item.getName();
			String areaName = orderService.getAreaNameByAreaCode(areaCode);
			supermarketFullName.setFullName(areaName);

			//设置超市的名字
			supermarketFullName.setName(name);
			supermarketFullNameList.add(supermarketFullName);

		});
		PageResult<SupermarketFullName> pageResult = new PageResult<>(supermarketFullNameList, supermarket);
		return pageResult;
	}
}
