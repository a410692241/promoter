package com.linayi.dao.api;

import com.linayi.entity.api.ApiLogs;

public interface ApiLogsMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(ApiLogs record);

    int insertSelective(ApiLogs record);

    ApiLogs selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(ApiLogs record);

    int updateByPrimaryKey(ApiLogs record);

}
