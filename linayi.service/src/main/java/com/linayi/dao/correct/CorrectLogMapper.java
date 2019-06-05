package com.linayi.dao.correct;

import com.linayi.entity.correct.CorrectLog;

import java.util.List;

public interface CorrectLogMapper {
    int insert(CorrectLog record);

    int insertSelective(CorrectLog record);

    int updateByPrimaryKeySelective(CorrectLog record);

    List<CorrectLog> selectByAll(CorrectLog correctLog);
}