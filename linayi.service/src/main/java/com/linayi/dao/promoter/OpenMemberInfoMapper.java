package com.linayi.dao.promoter;

import java.util.List;

import com.linayi.entity.promoter.OpenMemberInfo;

public interface OpenMemberInfoMapper {
    int insert(OpenMemberInfo record);
    /**
     * 根据主键Id更新会员有效时间
     * @param openMemberInfo
     * @return
     */
    Integer updateValidTimeById(OpenMemberInfo openMemberInfo);

    List<OpenMemberInfo> getMemberInfo(OpenMemberInfo openMemberInfo);

    void updateById(OpenMemberInfo memberInfo);

    /**
     * 通过会员id获取会员开通信息（开始时间倒序）
     * @param userId
     * @return
     */
    List<OpenMemberInfo> getMemberStartTimeByUserId(OpenMemberInfo openMemberInfo);

    /**
     * 根据userId查询有效的开通会员信息
     * @param userId
     * @return
     */
    OpenMemberInfo getOpenMemberInfo(Integer userId);
}
