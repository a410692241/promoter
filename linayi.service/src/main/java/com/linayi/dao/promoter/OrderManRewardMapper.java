package com.linayi.dao.promoter;

import com.linayi.entity.promoter.OrderManReward;
import com.linayi.entity.promoter.OrderManRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderManRewardMapper {
    long countByExample(OrderManRewardExample example);

    int deleteByExample(OrderManRewardExample example);

    int deleteByPrimaryKey(Integer orderManRewardId);

    int insert(OrderManReward record);

    int insertSelective(OrderManReward record);

    List<OrderManReward> selectByExample(OrderManRewardExample example);

    OrderManReward selectByPrimaryKey(Integer orderManRewardId);

    int updateByExampleSelective(@Param("record") OrderManReward record, @Param("example") OrderManRewardExample example);

    int updateByExample(@Param("record") OrderManReward record, @Param("example") OrderManRewardExample example);

    int updateByPrimaryKeySelective(OrderManReward record);

    int updateByPrimaryKey(OrderManReward record);
}