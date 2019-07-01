package com.linayi.service.area;

import java.util.List;
import java.util.Map;

import com.linayi.entity.BaseEntity;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.exception.ErrorType;

public interface SmallCommunityService {
	/**
	 * 添加小区
	 * @param record
	 * @return
	 */
	 int insert(SmallCommunity record);
	 /**
	 * 根据搜索条件获取小区信息
	 * @param smallCommunity
	 * @return
	 */
	 List<SmallCommunity> getSmallCommunity(
			String streetName,SmallCommunity smallCommunity,Integer communityId,String type);
	/**
	 * 获取所有的社区
	 * @param community
	 * @return
	 */
	 List<Community> getAllCommunity(Community community);
	 /**
	  * 通过小区Id获取小区信息
	  * @param smallCommunity
	  * @return
	  */
	 SmallCommunity getSmallCommunityBySmallCommunityId(SmallCommunity smallCommunity);
	 /**
	  * 获取省市区信息
	  * @param area
	  * @return
	  */
	 Area provinceCityAndRegion(String areaCode);
	 /**
	 * 修改小区信息
	 */
	 Integer updateSmallCommunity(SmallCommunity smallCommunity);
	 /**
	 * 解除社区与小区的绑定关系
	 * @param smallCommunity
	 * @return
	*/
	Integer removeBind(SmallCommunity smallCommunity);
	/**
	    * 根据小区ID修改状态为已删除 实现删除数据效果
	    * @param smallCommunityId
	 * @return
	    */
	   Integer removeSmallCommunity(Integer smallCommunityId);


	/**
	 * @param areaCode
	 * @return 通过areaCode查询小区
	 */
	List<SmallCommunity> getSmallCommunityByAreaCode(String areaCode) throws Exception;

	/**用户端添加小区
	 * @param smallCommunity
	 */
	Integer addSmallCommunity(SmallCommunity smallCommunity);
}
