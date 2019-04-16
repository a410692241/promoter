package com.linayi.service.spokesman.impl;

import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.spokesman.SpokesmanMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.spokesman.Spokesman;
import com.linayi.service.spokesman.SpokesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpokesmanServiceImpl implements SpokesmanService {
	@Autowired
	private SpokesmanMapper spokesmanMapper;
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public Spokesman addSpokesman(Spokesman spokesman) {
		spokesmanMapper.insert(spokesman);
		return null;
	}

	@Override
	public Spokesman selectSpokesmanBySpokesmanId(Integer spokesmanId) {
		Spokesman spokesman = spokesmanMapper.selectSpokesmanBySpokesmanId(spokesmanId);
		//获取省市区街道小区
		String areaCode = spokesman.getAreaCode();
		String addressTwo = spokesman.getName();

		Area area = new Area();
		String areaName = "";
		while (true) {
			area.setCode(areaCode);
			Area areaInfo = areaMapper.getAreaInfo(area);
			areaName = areaInfo.getName() + areaName;
			if (areaInfo.getParent().equals("1000")) {
				break;
			}
			areaCode = areaInfo.getParent();
		}
		spokesman.setReceiverAddress(areaName + addressTwo);

		return spokesman;
	}

	@Override
	public Spokesman selectSpokesmanByUserId(Integer userId) {
		Spokesman spokesman = spokesmanMapper.selectSpokesmanByUserId(userId);
		return spokesman;
	}

	@Override
	public void updateSpokesmanStatus(Spokesman spokesman) {
		spokesmanMapper.updateSpokesmanStatus(spokesman);
	}

}
