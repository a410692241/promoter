package com.linayi.service.redis;

public interface RedisService {


    /**
     * @param accountId
     * @return 生成系统token并保存在redis中
     */
    public String GenerationToken( Integer accountId);


    String generationTokenAdmin(Integer accountId);

    void saveAccessToken(Long accountId, String accessToken);

    void saveAccessTokenAdmin(Long accountId, String accessToken);

    boolean checkAccessToken(String tokenOrUserId);
    
    public Integer getAccountIdByToken(String accessToken);

    String getAccessTokenAdmin(String tokenOrUserId);

    void deleteAccessToken(Long accountId);

    void deleteAnotherAccessToken(String accessToken);

    /**
     * @return 生成短信验证码
     */
    String generationValidCode(String mobile);

    /**
     * @param mobile 手机号
     * @param validCode 验证码
     * @return 验证是否成功
     */
    boolean validValidCode(String mobile,String validCode);
}
