package com.linayi.dao.order;


import com.linayi.entity.order.SelfOrderMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SelfOrderMessageMapper {

    /**
     *将消息发送给每个采买员
     * @param selfOrderMessage 消息表
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
    Integer updateSelfOrderMessageStatusByPrimaryKey(@Param("viewStatus")String viewStatus,@Param("status") String status, @Param("messageId")Integer messageId, @Param("updateTime")Date updateTime);

    /**
     * 通过自定义下单id查自定义下单消息列
     * @param selfOrderMessageId
     * @return
     */
    List<SelfOrderMessage> selectSelfOrderMessageBySelfOrderId(Long selfOrderMessageId);

    void insert(SelfOrderMessage selfOrderMessage);

    List<SelfOrderMessage> selectByAll(SelfOrderMessage selfOrderMessage);

    int updateByPrimaryKey(SelfOrderMessage record);


}