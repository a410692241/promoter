package com.linayi.service.user;

import com.linayi.entity.user.Message;

import java.util.List;

public interface MessageService {

    /**
     * 通过用户id查询消息
     * @param userId
     * @return
     */
    List<Message> selectMessageByUserId(Integer userId);
    /**
     * 插入自定义信息
     * @param message
     * @return
     */
    Integer sendAllMessage(Message message);

    /**
     * 修改单个消息状态
     * @param message
     */
    Integer updateMessageStatusByMessageId(Message message);
}
