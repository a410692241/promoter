package com.linayi.dao.promoter;

import com.linayi.entity.promoter.Reward;
import com.linayi.entity.promoter.RewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RewardMapper {
    long countByExample(RewardExample example);

    int deleteByExample(RewardExample example);

    int deleteByPrimaryKey(Integer rewardId);

    int insert(Reward record);

    int insertSelective(Reward record);

    List<Reward> selectByExample(RewardExample example);

    Reward selectByPrimaryKey(Integer rewardId);

    int updateByExampleSelective(@Param("record") Reward record, @Param("example") RewardExample example);

    int updateByExample(@Param("record") Reward record, @Param("example") RewardExample example);

    int updateByPrimaryKeySelective(Reward record);

    int updateByPrimaryKey(Reward record);
}