package com.linayi.service.redis.impl;

import java.lang.annotation.Retention;
import java.util.UUID;

import javax.annotation.Resource;

import com.linayi.config.RedisConstant;
import com.linayi.config.SystemPropert;
import com.linayi.util.Configuration;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.linayi.service.redis.RedisService;
import com.linayi.util.RedisUtil;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisUtil redisUtil;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String GenerationToken(Integer accountId) {
        String randomUUID = UUID.randomUUID().toString();
        String sysetemAccessToken = randomUUID;
        Long accountIdLong = Long.parseLong(accountId + "");
        saveAccessToken(accountIdLong,sysetemAccessToken);
        return sysetemAccessToken;
    }

    @Override
    public String generationTokenAdmin(Integer accountId) {
        String randomUUID = UUID.randomUUID().toString();
        String sysetemAccessToken = randomUUID;
        Long accountIdLong = Long.parseLong(accountId + "");
        saveAccessTokenAdmin(accountIdLong,sysetemAccessToken);
        return sysetemAccessToken;
    }


    @Override
    public void saveAccessToken(Long accountId, String accessToken) {
        //去除留下的旧accessToken
        String oldToken = redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN + accountId) + "";
        redisUtil.del(RedisConstant.Keys.ACCESS_TOKEN + accountId, RedisConstant.Keys.ACCESS_TOKEN_ADMIN + oldToken);
        //赋值新的accessToken
        int times = Integer.valueOf(Configuration.getConfig().getValue(SystemPropert.VALID_DAY)) * 60 * 60 * 24;
        redisUtil.set(RedisConstant.Keys.ACCESS_TOKEN + accountId, //
                accessToken, times);
        redisUtil.set(RedisConstant.Keys.ACCESS_TOKEN + accessToken, //
                accountId, times);

    }

    @Override
    public void saveAccessTokenAdmin(Long accountId, String accessToken) {
        int times = Integer.valueOf(Configuration.getConfig().getValue(SystemPropert.VALID_DAY)) * 60 * 60 * 24;
        //去除留下的旧accessToken
        String oldToken = redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN_ADMIN + accountId) + "";
        redisUtil.del(RedisConstant.Keys.ACCESS_TOKEN_ADMIN + accountId, RedisConstant.Keys.ACCESS_TOKEN_ADMIN + oldToken);

        //赋值新的accessToken
        redisUtil.set(RedisConstant.Keys.ACCESS_TOKEN_ADMIN + accountId, //
                accessToken, times);
        redisUtil.set(RedisConstant.Keys.ACCESS_TOKEN_ADMIN + accessToken, //
                accountId, times);

    }

    @Override
    public boolean checkAccessToken(String tokenOrUserId) {
        if(StringUtils.isBlank(tokenOrUserId)){
            return false;
        }
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        byte[] bytes = connection.get((RedisConstant.Keys.ACCESS_TOKEN + tokenOrUserId).getBytes());
        String value_2 = null;
        if(bytes != null){
            String value_1 = new String(bytes);
            byte[] bytes1 = connection.get((RedisConstant.Keys.ACCESS_TOKEN + value_1).getBytes());
            if(bytes1 != null){
                value_2 = new String(bytes1);
                //字符串可能有多余的双引号
                value_2 = value_2.replaceAll("\"", "");
            }
        }
        connection.close();
//        String value_1 = redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN + tokenOrUserId) + "";
//        String value_2 = redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN + value_1) + "";
        if (tokenOrUserId.equals(value_2)) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getAccountIdByToken(String accessToken) {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        byte[] bytes = connection.get((RedisConstant.Keys.ACCESS_TOKEN + accessToken).getBytes());
        String accountId= null;
        if(bytes != null){
            accountId = new String(bytes);
        }
        connection.close();
        return accountId == null || "null".equals(accountId) ? null : Integer.parseInt(accountId);
    }

    @Override
    public String getAccessTokenAdmin(String tokenOrUserId) {
        String value_1 =  redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN_ADMIN + tokenOrUserId)+"";
        System.out.println(value_1+"------");
        String value_2 = redisUtil.get(RedisConstant.Keys.ACCESS_TOKEN_ADMIN + value_1)+"";
        System.out.println(value_2+"------");
        if (tokenOrUserId.equals(value_2)) {
            return value_1;
        }
        return null;
    }

    @Override
    public void deleteAccessToken(Long accountId) {
        redisUtil.del(RedisConstant.Keys.ACCESS_TOKEN + accountId);
    }

    @Override
    public void deleteAnotherAccessToken(String accessToken) {
        redisUtil.del(RedisConstant.Keys.ACCESS_TOKEN + accessToken);
    }

    /**
     * @return 生成短信验证码
     */
    @Override
    public String generationValidCode(String mobile) {
        String code = (int) ((Math.random() * 9 + 1) * 1000) + "";
        //给该手机号设置2分钟的过期时间
        redisUtil.set(RedisConstant.Keys.VALIDATE_CODE + mobile, code, 2 * 60);
        return code;
    }

    @Override
    public boolean validValidCode(String mobile,String validCode) {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        byte[] bytes = connection.get((RedisConstant.Keys.VALIDATE_CODE + mobile).getBytes());
        String validCodeRedis = null;
        if(bytes != null){
            validCodeRedis = new String(bytes);
            if (validCode.equals(validCodeRedis.replaceAll("\"",""))) {
                return true;
            }
        }
        connection.close();
        return false;
    }

}
