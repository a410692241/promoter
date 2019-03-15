package com.linayi.service.goods.impl;

import com.linayi.dao.goods.CommunityGoodsMapper;
import com.linayi.service.goods.CommunityGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityGoodsServiceImpl implements CommunityGoodsService {
    @Autowired
    private CommunityGoodsMapper communityGoodsMapper;
}
