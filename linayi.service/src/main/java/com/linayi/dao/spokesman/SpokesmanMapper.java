package com.linayi.dao.spokesman;

import com.linayi.entity.spokesman.Spokesman;

public interface SpokesmanMapper {
    Integer insert(Spokesman spokesman);

    /**
     * 根据主键id获取代言人信息
     * @param spokesmanId
     * @return
     */
    Spokesman selectSpokesmanBySpokesmanId(Integer spokesmanId);

    /**
     * 根据用户id获取代言人信息
     * @param userId
     * @return
     */
    Spokesman selectSpokesmanByUserId(Integer userId);

    /**
     * 修改代言人状态
     * @param userId
     * @return
     */
    Integer updateSpokesmanStatus(Spokesman spokesman);
}