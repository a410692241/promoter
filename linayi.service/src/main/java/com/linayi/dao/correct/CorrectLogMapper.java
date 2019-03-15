package com.linayi.dao.correct;

import com.linayi.entity.correct.CorrectLog;

public interface CorrectLogMapper {
    int insert(CorrectLog record);

    int insertSelective(CorrectLog record);
}