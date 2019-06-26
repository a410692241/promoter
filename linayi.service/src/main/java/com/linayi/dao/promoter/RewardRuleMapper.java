package com.linayi.dao.promoter;

import com.linayi.entity.promoter.RewardRule;
import com.linayi.entity.promoter.RewardRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RewardRuleMapper {
    long countByExample(RewardRuleExample example);

    int deleteByExample(RewardRuleExample example);

    int deleteByPrimaryKey(Integer rewardRuleId);

    int insert(RewardRule record);

    int insertSelective(RewardRule record);

    List<RewardRule> selectByExample(RewardRuleExample example);

    RewardRule selectByPrimaryKey(Integer rewardRuleId);

    int updateByExampleSelective(@Param("record") RewardRule record, @Param("example") RewardRuleExample example);

    int updateByExample(@Param("record") RewardRule record, @Param("example") RewardRuleExample example);

    int updateByPrimaryKeySelective(RewardRule record);

    int updateByPrimaryKey(RewardRule record);
}