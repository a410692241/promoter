package com.linayi.service.community;

import java.util.List;
import java.util.Map;

import com.linayi.entity.area.Area;
import com.linayi.exception.ErrorType;
import org.apache.ibatis.annotations.Param;

import com.linayi.entity.community.Community;

public interface CommunityService {

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
    /**
     * 新增或修改社区信息
     * @param community
     * @return
     */
    Community addOrUpdateCommunity(Community community);
    
    /**
     * 通过社区Id改变状态 删除社区商品表，重新添加社区商品表达到删除社区功能
     */
    Integer removeCommunity(Integer communityId);

    /** 通过areaCode获取社区列表
     * @param community
     * @return
     */
    List<Community> getCommunityByAreaCode(Community community) throws Exception;

    Community getCommunityById(Integer communityId);
}