package com.linayi.service.promoter.impl;

import com.linayi.dao.promoter.OpenMemberInfoMapper;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.service.promoter.OpenMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenMemberInfoServiceImpl implements OpenMemberInfoService {

    @Autowired
    private OpenMemberInfoMapper openMemberInfoMapper;


    @Override
    public OpenMemberInfo getTheLastOpenMemberInfo(Integer userId) {
        OpenMemberInfo openMemberInfos = openMemberInfoMapper.getOpenMemberInfo(userId);
        return openMemberInfos;
    }
}
