package com.linayi.dao.community;

import java.util.List;

import com.linayi.entity.community.CommunitySupermarket;
import org.apache.ibatis.annotations.Param;

public interface CommunitySupermarketMapper {
    int insert(CommunitySupermarket record);

    int insertSelective(CommunitySupermarket record);
    /**
     * 通过社区Id获取超市Id集合
     * @param communityId
     * @return
     */
    List<Integer> getSupermarketIdList(Integer communityId);
    /**
     * 删除数据，通过超市ID和社区ID
     * @param communitySupermarket
     * @return
     */
    Integer deleteCommunitySupermarket(CommunitySupermarket communitySupermarket);
    
   

    /**
     * 通过超市id查找社区超市表集合
     * @param supermarketId
     * @return
     */
    List<Integer> getCommunityIdBysupermarketId(Integer supermarketId);

    /**
     * 通过社区ID和超市ID查找超市排序
     * @param communityId
     * @param supermarketId
     * @return
     */
    Integer getRankByCommunityIdAndSupermarketId(@Param("communityId")Integer communityId,
                                                 @Param("supermarketId")Integer supermarketId);
}