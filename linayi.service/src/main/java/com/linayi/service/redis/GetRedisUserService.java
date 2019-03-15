package com.linayi.service.redis;

public interface GetRedisUserService {
    /***
     * 通过token值获得账号信息
     */
    int getAccountIdByToken(String token);
}
