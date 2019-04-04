package com.linayi.service.promoter.impl;

import com.linayi.dao.promoter.OpenMemberInfoMapper;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.enums.MemberLevel;
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

    @Override
    public MemberLevel getCurrentMemberLevel(Integer userId) {
        OpenMemberInfo openMemberInfos = getTheLastOpenMemberInfo(userId);
        if(openMemberInfos == null){
            return MemberLevel.NOT_MEMBER;
        }

        if(MemberLevel.NORMAL.toString().equals(openMemberInfos.getMemberLevel())){
            return MemberLevel.NORMAL;
        }

        if(MemberLevel.SENIOR.toString().equals(openMemberInfos.getMemberLevel())){
            return MemberLevel.SENIOR;
        }

        if(MemberLevel.SUPER.toString().equals(openMemberInfos.getMemberLevel())){
            return MemberLevel.SUPER;
        }

        return MemberLevel.NOT_MEMBER;
    }
}
