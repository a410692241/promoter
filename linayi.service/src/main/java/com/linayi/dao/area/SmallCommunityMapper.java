package com.linayi.dao.area;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linayi.entity.area.SmallCommunity;

public interface SmallCommunityMapper {
    int insert(SmallCommunity record);

    int insertSelective(SmallCommunity record);
    /**
     * 根据搜索条件获取小区信息
     * @param smallCommunity
     * @return
     */
    List<SmallCommunity> getSmallCommunityList(SmallCommunity smallCommunity);


    /**
     * @param smallCommunity
     * @return 基本条件查询模板
     */
    List<SmallCommunity> query(SmallCommunity smallCommunity);

    /*查询所有具有绑定社区的小区*/
    List<SmallCommunity> queryAllCommunity(SmallCommunity smallCommunity);
    /**
     * 通过小区信息获取信息
     * @param smallCommunity
     * @return
     */
    SmallCommunity getSmallCommunity(SmallCommunity smallCommunity);
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
    * 获取社区ID为null的小区 即未绑定社区
    * @return
    */
   List<SmallCommunity> getCommunityIdIsNull(SmallCommunity smallCommunity);
   /**
    * 获取社区ID不为null的小区 即绑定社区
    * @return
    */
   List<SmallCommunity> getCommunityIdIsNotNull(SmallCommunity smallCommunity);
   
   /**
    * 根据订单id和社区id获取小区信息
    * @param communityId
    * @return
    */
  SmallCommunity getSmallCommunitybyCommunityId(@Param("communityId")Integer communityId,@Param("ordersId")Long ordersId);

    /**
     * 查询小区信息数
      * @return
     */
  List<SmallCommunity> getDeliverer(Integer delivererId);

    List<SmallCommunity> getBindedSmallCommunityList(SmallCommunity smallCommunity);
}