package com.linayi.service.promoter;

import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.enums.MemberLevel;

public interface OpenMemberInfoService {

    /**
     * 根据userId获取最后一条开通会员的生效信息
     * @param userId
     * @return
     */
    OpenMemberInfo getTheLastOpenMemberInfo(Integer userId);

    public MemberLevel getCurrentMemberLevel(Integer userId);
}
