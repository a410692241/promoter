package com.linayi.dao.community;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.linayi.entity.community.Community;

public interface CommunityMapper {
    int insert(Community record);

    int insertSelective(Community record);
    /**
     * 获取社区信息集合
     * @param community
     * @return
     */
    List<Community> getCommunityList(Community community);
    /**
     * 获取社区信息
     * @param community
     * @return
     */
    Community getCommunity(Community community);

    List<Community> getCommunityByCommunityIdList(@Param("communityIdList") List<Integer> communityIdList);

    /**
     * 修改社区信息
     * @param community
     * @return
     */
    Integer updateCommunity(Community community);
    /**
     * 通过社区名搜索社区Id 
     */
    List<Integer> getCommunityIdList(String name);
    /**
     * 通过社区Id改变状态 达到删除社区功能
     */
    Integer removeCommunity(Integer communityId);



    /**
     * 根据用户id获取社区id
     * @param userId
     * @return
     */
    Integer getcommunityIdByuserId(Integer userId);


    List<Community> getCommunityByAreaCode(String areaCode);

}