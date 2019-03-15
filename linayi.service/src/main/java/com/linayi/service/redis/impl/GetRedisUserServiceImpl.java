package com.linayi.service.redis.impl;

import com.linayi.config.RedisConstant;
import com.linayi.service.redis.GetRedisUserService;
import com.linayi.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GetRedisUserServiceImpl implements GetRedisUserService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public int getAccountIdByToken(String token) {
        String accountId = "";
        try {
            System.out.println(RedisConstant.Keys.ACCESS_TOKEN + token);
            accountId = redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN + token) + "";
            if(accountId.equals("")||accountId==null){{
                return -1;
            }}
        } catch (Exception e) {
            accountId = "-1";
        }
        return Integer.parseInt(accountId);
    }
}
