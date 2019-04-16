package com.linayi.dao.spokesman;

import com.linayi.entity.spokesman.Vote;

public interface VoteMapper {
    int insert(Vote record);

    int insertSelective(Vote record);
}