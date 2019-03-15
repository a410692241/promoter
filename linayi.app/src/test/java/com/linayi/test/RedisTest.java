package com.linayi.test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.community.CommunitySupermarketMapper;
import com.linayi.dao.order.SelfOrderMapper;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.exception.ErrorType;
import com.linayi.service.redis.RedisService;
import com.linayi.util.RedisUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class RedisTest {
   
	@Resource
    private RedisUtil redisUtil;
    @Resource(name="redisTemplate")
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;
    @Resource
    private SelfOrderMapper selfOrderMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Resource
    private CommunitySupermarketMapper communitySupermarketMapper;

    public SelfOrder insertUserMessage(SelfOrder selfOrder){
        selfOrderMapper.insertUserMessage(selfOrder);
        return selfOrder;
    }
    
    @Test
    public void testSpringRedis() {
    	
//    	redisTemplate.delete("myStr");
    	//通过用户id获取社区id
    	String sharers;
    	SelfOrder selfOrder = new SelfOrder();
    	selfOrder.setGoodsName("woainimaa");
    	selfOrder.setAttrValue("11111L");
    	selfOrder.setBrandName("ooooooo");
        Integer communityId = communityMapper.getcommunityIdByuserId(2);
        System.out.println("communityId=="+communityId);
        //根据社区id获取绑定的超市id集合
        List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);
        int index = 0;
        sharers = "[";
        for (Integer sid:supermarketIdList){
            index++;
            Supermarket supermarket = selfOrderMapper.findSupermarketById(sid);
            if(supermarket!=null){
            	Integer supermarketId = supermarket.getSupermarketId();
                Integer uid = supermarket.getUserId();
                System.out.println("supermarketId==="+supermarketId);
                System.out.println("uid==="+uid);
                sharers += "{'supermarket_id':"+supermarket.getSupermarketId()+",'user_id':"+supermarket.getUserId()+"}";
                if (index==supermarketIdList.size()){
                    break;
                }
                sharers +=",";
            }
        }
        sharers += "]";
        System.out.println("sharers==="+sharers);
        selfOrder.setSharers(sharers);
        selfOrder.setUpdateTime(new Date());
        selfOrder.setCreateTime(new Date());
        SelfOrder soid = insertUserMessage(selfOrder);
        System.out.println("soid==="+soid.getGoodsName());
        System.out.println("soid==="+soid.getSelfOrderId());
        for (Integer s:supermarketIdList){
            Supermarket sm = selfOrderMapper.findSupermarketById(s);
            if(sm!=null){
            	SelfOrderMessage selfOrderMessage = new SelfOrderMessage(soid.getSelfOrderId(),"WAIT_DEAL",sm.getUserId(),new Date(),new Date());
//                selfOrderMapper.sendBuyerMessage(selfOrderMessage);
            }
        }

        
    }

//    @Test
//    public void validCodeTest() {
//        SimpleMessageUtil.send("15797862687","6174");
//    }

    @Test
    public void enumTest() {
        ErrorType systemError = ErrorType.SYSTEM_ERROR;
        System.out.println(systemError.getErrorMsg()+systemError.getErrorCode());
    }

    @Test
    public void redisTest() {
        ReceiveAddress receiveAddress = new ReceiveAddress();
        receiveAddress.setReceiveAddressId(1);
        System.out.println(JSON.toJSON(receiveAddressMapper.query(receiveAddress)));
    }

    @Test
    public void validTest() {
         redisTemplate.opsForValue().set("whereBuy:validate_code:16675157754","1234");
    }

    @Test
    public void jj() {
    	SelfOrder s = new SelfOrder();
    	s.setBrandName("aaaa");
    	s.setAttrValue("asdadsa");
    	s.setGoodsName("5555");
    	s.setSharers("aaaa");
    	selfOrderMapper.insertUserMessage(s);
    	System.out.println("id:"+s.getSelfOrderId());

        
    }
}