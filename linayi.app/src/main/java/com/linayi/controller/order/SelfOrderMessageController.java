package com.linayi.controller.order;

import com.linayi.controller.BaseController;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.user.Message;
import com.linayi.enums.HandleType;
import com.linayi.enums.MessageType;
import com.linayi.enums.UserType;
import com.linayi.enums.ViewStatus;
import com.linayi.exception.ErrorType;
import com.linayi.service.order.SelfOrderService;
import com.linayi.service.user.MessageService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.service.order.SelfOrderMessageService;

@Controller
@RequestMapping("/order/custom")
public class SelfOrderMessageController extends BaseController{

    @Autowired
    private SelfOrderService selfOrderService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SelfOrderMessageService selfOrderMessageService;

    
    //判断是否为分享员
    @RequestMapping("/isShare.do")
    @ResponseBody
    public Object isShare() {
    	try {
    		Integer userId = getUserId();
        	Boolean isSharer = selfOrderMessageService.isSharer(userId);
        	if(isSharer){
        	return new ResponseData("YES").toString();
        	}
        	return new ResponseData("NO").toString();

		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
    }

    @RequestMapping("/insertMessage.do")
    @ResponseBody
    public ResponseData InsertMessage(@RequestBody Map<String, Object> param) {
        ResponseData rr;
        try {
            ParamValidUtil<SelfOrder> pvu = new ParamValidUtil<>(param);
            pvu.Exist("goodsName", "attrValue", "brandName");
            SelfOrder selfOrder = pvu.transObj(SelfOrder.class);
            selfOrder.setUserId(super.getUserId());
            selfOrderService.insertAllUserMessage(selfOrder);
            rr = new ResponseData(selfOrder);
            return rr;
        } catch (Exception e) {
            rr = new ResponseData(ErrorType.SYSTEM_ERROR);
            return rr;
        }
    }

    //消息列表
    @RequestMapping("/message.do")
    @ResponseBody
    public Object selectMessageByUserId() {
        try {
            List<Message> message = messageService.selectMessageByUserId(super.getUserId());
            return new ResponseData(message);
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    //修改单个消息状态
    @RequestMapping("/updateMessageStatus.do")
    @ResponseBody
    public Object updateMessageStatusByMessageId(@RequestBody Message message){
        try {
            return new ResponseData(messageService.updateMessageStatusByMessageId(message));
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }

    //点击有无价格
    @RequestMapping("/handleCustomOrder.do")
    @ResponseBody
    public Object Price(@RequestBody Map<String, Object> param) {
        try {
            String priceNum = (String) param.get("priceNum");
            Integer messageId = (Integer) param.get("messageId");
            Long selfOrderId = (Integer) param.get("selfOrderId") + 0L;
            String statusString;
            if ("有".equals(priceNum)) {
                //有价格
                statusString = HandleType.SUCCESS.toString();
            } else {
                //无价格
                statusString = HandleType.FAIL.toString();
            }
            selfOrderMessageService.updateSelfOrderMessageStatusByPrimaryKey(ViewStatus.VIEWED.toString(), statusString, messageId);
            //判断分享员是否全部判定完
            int i = 0;
            List<SelfOrderMessage> selfOrderMessageList = selfOrderMessageService.selectSelfOrderMessageBySelfOrderId(selfOrderId);
            for (SelfOrderMessage selfOrderMessage : selfOrderMessageList) {
                //分享员是否点击完毕
                if (HandleType.WAIT_DEAL.toString().equals(selfOrderMessage.getStatus())) {
                    i++;
                }
            }
            //分享员点击完毕，修改自定义下单状态
            if (i == 0) {
                for (SelfOrderMessage selfOrderMessage : selfOrderMessageList) {
                    if (HandleType.SUCCESS.toString().equals(selfOrderMessage.getStatus())) {
                        //当有一个处理成功状态
                        statusString = HandleType.SUCCESS.toString();
                        break;
                    } else {
                        statusString = HandleType.FAIL.toString();
                    }
                }
                selfOrderService.updateSelfOrderStatusByPrimaryKey(selfOrderId, statusString);
                //添加消息表数据
                Message message = new Message();
                Date now = new Date();
                message.setMessageType(MessageType.SELF_ORDER.toString());
                message.setTitle("您有一条新信息");
                message.setUserId(selfOrderService.selectByPrimaryKey(selfOrderId).getUserId());
                message.setUserType("USER");
                message.setViewStatus(ViewStatus.NOT_VIEW.toString());
                message.setCreateTime(now);
                messageService.sendAllMessage(message);
            }
            return new ResponseData("处理成功");
        } catch (Exception e) {
            return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
        }
    }


    /*信息分享列表*/
    @RequestMapping("/selfOrderMessage.do")
    @ResponseBody
    public Object SelfOrderMessage(@RequestBody SelfOrderMessage selfOrderMessage) {
        try {
        	if(selfOrderMessage.getPageSize() == null){
        		selfOrderMessage.setPageSize(8);
    		}
        	selfOrderMessage.setUserId(getUserId());
        List<SelfOrderMessage> selfOrderList = selfOrderMessageService.selectByUserId(selfOrderMessage);
        
        Integer totalPage = (int) Math.ceil(Double.valueOf(selfOrderMessage.getTotal())/Double.valueOf(selfOrderMessage.getPageSize()));
		if(totalPage <= 0){
			totalPage++;
		}
		Map<String , Object> map = new HashMap<>();
		map.put("data", selfOrderList);
		map.put("totalPage", totalPage);
		map.put("currentPage",selfOrderMessage.getCurrentPage() );
		return new ResponseData(map);
       	} catch (Exception e) {
            
       		return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
   		}
    }


    /*信息分享详情*/
    @RequestMapping("/messageDetails.do")
    @ResponseBody
    public Object MessageDetails(@RequestBody Map<String, Object> param) {
        try {
        SelfOrder selfOrder = selfOrderService.selectByPrimaryKey((Integer) param.get("selfOrderId") + 0L);
        return new ResponseData(selfOrder);
    	} catch (Exception e) {
    		return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
    }
}
