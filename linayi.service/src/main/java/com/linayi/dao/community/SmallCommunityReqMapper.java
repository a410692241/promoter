package com.linayi.dao.community;
import org.apache.ibatis.annotations.Param;
import java.util.Date;

import com.linayi.entity.community.SmallCommunityReq;import java.util.List;

public interface SmallCommunityReqMapper {
    int deleteByPrimaryKey(Integer smallCommunityReqId);

    int insert(SmallCommunityReq record);

    int insertSelective(SmallCommunityReq record);

    SmallCommunityReq selectByPrimaryKey(Integer smallCommunityReqId);

    int updateByPrimaryKeySelective(SmallCommunityReq record);

    int updateByPrimaryKey(SmallCommunityReq record);

    List<SmallCommunityReq> selectByAll(SmallCommunityReq smallCommunityReq);


}