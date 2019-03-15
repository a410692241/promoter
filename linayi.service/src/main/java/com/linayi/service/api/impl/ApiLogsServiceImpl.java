package com.linayi.service.api.impl;

import com.linayi.dao.api.ApiLogsMapper;
import com.linayi.entity.api.ApiLogs;
import com.linayi.service.api.ApiLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApiLogsServiceImpl implements ApiLogsService {


    @Autowired
    private ApiLogsMapper apiLogsMapper;

    @Override
    public void updateLog(ApiLogs apiLogs) {
        apiLogsMapper.updateByPrimaryKeySelective(apiLogs);
    }

    @Override
    public void insertLog(ApiLogs apiLogs) {
        apiLogsMapper.insert(apiLogs);
    }
}
