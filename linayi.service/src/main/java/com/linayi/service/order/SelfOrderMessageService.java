package com.linayi.service.order;

import java.util.Date;
import java.util.List;

import com.linayi.entity.order.SelfOrderMessage;

public interface SelfOrderMessageService {

    /**
     * 自定义下单信息
     * @param selfOrderMessage
     * @return
     */
    Integer sendBuyerMessage(SelfOrderMessage selfOrderMessage);

    /**
     * 通过用户id查询自定义下单消息表
     * @param userId
     * @return
     */
    List<SelfOrderMessage> selectByUserId(SelfOrderMessage selfOrderMessage);

    /**
     * 通过主键修改状态
     * @param status
     * @param messageId
     * @return
     */
    Integer updateSelfOrderMessageStatusByPrimaryKey(String viewStatus,String status, Integer messageId);

    /**
     * 通过自定义下单id查自定义下单消息列
     * @param selfOrderMessageId
     * @return
     */
    List<SelfOrderMessage> selectSelfOrderMessageBySelfOrderId(Long selfOrderMessageId);
    
    /**
     * 判断是否为分享员
     * @param userId
     * @return
     */
    Boolean isSharer(Integer userId);


}
