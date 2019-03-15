package com.linayi.dao.user;


import com.linayi.entity.user.Message;

import java.util.List;

public interface MessageMapper {

    /**
     * 将自定义消息插入消息表
     * @param message
     * @return
     */
    Integer sendAllMessage(Message message);

    /**
     * 通过用户id查询消息
     * @param userId
     * @return
     */
    List<Message> selectMessageByUserId(Integer userId);

    /**
     * 修改单个消息状态
     * @param message
     */
    Integer updateMessageStatusByMessageId(Message message);

}