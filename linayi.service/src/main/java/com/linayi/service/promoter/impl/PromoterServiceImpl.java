package com.linayi.service.promoter.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.dao.promoter.PromoterMapper;
import com.linayi.entity.promoter.Promoter;
import com.linayi.service.promoter.PromoterService;

@Service
public class PromoterServiceImpl implements PromoterService {
	
    @Autowired
    private PromoterMapper promoterMapper;

	@Override
	public List<Promoter> getPromoterListByType(Promoter promoter) {
		return promoterMapper.getPromoterListByType(promoter);
	}
    
}