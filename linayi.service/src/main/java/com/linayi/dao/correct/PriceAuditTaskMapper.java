package com.linayi.dao.correct;

import com.linayi.entity.correct.PriceAuditTask;
import com.linayi.entity.correct.PriceAuditTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PriceAuditTaskMapper {
    long countByExample(PriceAuditTaskExample example);

    int deleteByExample(PriceAuditTaskExample example);

    int deleteByPrimaryKey(Integer taskId);

    int insert(PriceAuditTask record);

    int insertSelective(PriceAuditTask record);

    List<PriceAuditTask> selectByExample(PriceAuditTaskExample example);

    PriceAuditTask selectByPrimaryKey(Integer taskId);

    int updateByExampleSelective(@Param("record") PriceAuditTask record, @Param("example") PriceAuditTaskExample example);

    int updateByExample(@Param("record") PriceAuditTask record, @Param("example") PriceAuditTaskExample example);

    int updateByPrimaryKeySelective(PriceAuditTask record);

    int updateByPrimaryKey(PriceAuditTask record);
}