package com.linayi.service.api;

import com.linayi.entity.api.ApiLogs;

public interface ApiLogsService {

    /**保存日志
     * @param apiLogs
     */
    void updateLog(ApiLogs apiLogs);

    void insertLog(ApiLogs apiLogs);
}
