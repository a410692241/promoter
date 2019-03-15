package com.linayi.service.user.impl;

import com.linayi.dao.user.MessageMapper;
import com.linayi.entity.user.Message;
import com.linayi.enums.ViewStatus;
import com.linayi.service.user.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public List<Message> selectMessageByUserId(Integer userId){
        return messageMapper.selectMessageByUserId(userId);
    }

    @Override
    public Integer sendAllMessage(Message message){
        return messageMapper.sendAllMessage(message);
    }

    @Override
    public Integer updateMessageStatusByMessageId(Message message){
        Date now = new Date();
        message.setViewStatus(ViewStatus.VIEWED.toString());
        message.setUpdateTime(now);
        return messageMapper.updateMessageStatusByMessageId(message);
    }
}
