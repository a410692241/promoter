package com.linayi.service.order.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linayi.dao.order.SelfOrderMessageMapper;
import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.entity.user.User;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.order.SelfOrderMessageService;
import com.linayi.service.user.UserService;

@Service("selfOrderMessageService")
public class SelfOrderMessageServiceImpl implements SelfOrderMessageService {

    @Autowired
    private SelfOrderMessageMapper selfOrderMessageMapper;
    @Autowired
    private UserService userService;

    @Override
    public Integer sendBuyerMessage(SelfOrderMessage selfOrderMessage){
        return selfOrderMessageMapper.sendBuyerMessage(selfOrderMessage);
    }

    @Override
    public Integer updateSelfOrderMessageStatusByPrimaryKey(String viewStatus,String status, Integer messageId){
        Date now = new Date();
        return selfOrderMessageMapper.updateSelfOrderMessageStatusByPrimaryKey(viewStatus,status,messageId,now);
    }

    @Override
    public List<SelfOrderMessage> selectSelfOrderMessageBySelfOrderId(Long selfOrderMessageId){
        return selfOrderMessageMapper.selectSelfOrderMessageBySelfOrderId(selfOrderMessageId);
    }


	@Override
	public List<SelfOrderMessage> selectByUserId(SelfOrderMessage selfOrderMessage) {
		return selfOrderMessageMapper.selectByUserId(selfOrderMessage);
	}

	@Override
	public Boolean isSharer(Integer userId) {
		User currentUser = userService.selectUserByuserId(userId);
		if("FALSE".equals(currentUser.getIsSharer())){
			return false;
		}
			return true;
	}

	
}
