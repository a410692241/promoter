package com.linayi.test;

import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.community.CommunitySupermarketMapper;
import com.linayi.dao.correct.CorrectLogMapper;
import com.linayi.dao.correct.CorrectMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.goods.SupermarketGoodsMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.MessageMapper;
import com.linayi.entity.community.CommunitySupermarket;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.CorrectLog;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersSku;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.Message;
import com.linayi.enums.MessageType;
import com.linayi.service.account.AdminAccountService;
import com.linayi.service.community.CommunitySupermarketService;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.impl.CorrectServiceImpl;
import com.linayi.service.goods.GoodsSkuService;
import com.linayi.service.order.OrderService;
import com.linayi.service.order.SelfOrderMessageService;
import com.linayi.service.order.SelfOrderService;
import com.linayi.service.user.MessageService;
import com.linayi.util.DateUtil;
import com.linayi.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class RedisTest {


	@Resource
    private RedisUtil redisUtil;    
    @Resource(name="redisTemplate")
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Resource
    private GoodsSkuMapper goodsSkuMapper;

    @Resource
    private CorrectMapper correctMapper;
    @Resource
    private CorrectService correctService;
    @Resource
    private CorrectLogMapper coorectLogMapper;
    @Resource
    private SupermarketGoodsMapper supermarketGoodsMapper;
    @Resource
    private CommunitySupermarketMapper communitySupermarketMapper;
    @Resource
    private CommunitySupermarketService communitySupermarketService;
    @Resource
    private MessageService messageService;
    @Resource
    private SelfOrderMessageService selfOrderMessageService;
    @Resource
    private SelfOrderService selfOrderService;

    @Resource
    private OrderService orderService;

    @Resource
    private SupermarketMapper supermarketMapper;


	@Resource
    private AdminAccountService adminAccount;
//    @Autowired
//    private CustomLabel customLabel;




    @Test
    public void testSpringRedis() {
//    	redisTemplate.delete("myStr");
    	redisUtil.set("key3", "33333333335");
    	System.out.println();
        redisTemplate.opsForValue().set("myStr", "skyLine");
        System.out.println(redisTemplate.opsForValue().get("myStr"));
        System.out.println("---------------"+redisUtil.get("key3"));
    }

    @Test
    public void testTags() {
//        Object categoryList = customLabel.getCategoryList();
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.set(Calendar.DAY_OF_MONTH,1);
        cl.set(Calendar.HOUR_OF_DAY,0);
        cl.set(Calendar.MINUTE,0);
        cl.set(Calendar.SECOND,0);
        String date2String = DateUtil.date2String(cl.getTime(), "yyyy-MM-dd HH:mm:ss");
        cl.add(Calendar.MONTH,1);
        String s = DateUtil.date2String(cl.getTime(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(s);
    }

    @Test
    public void testInsert(){
        Correct correct = new Correct();
        Date now = new Date();
        correct.setSupermarketId(1);
        correct.setGoodsSkuId(22L);
        correct.setPrice(222);
        correct.setStartTime(now);
        correct.setEndTime(now);
        correct.setUserId(44444);
        correct.setPriceType("啧啧啧");
        correct.setImage("正常");
        correct.setStatus("哎哎哎");
        correct.setActualStartTime(now);
        correct.setActualEndTime(now);
        correct.setType("888");
        correct.setCreateTime(now);
        correct.setUpdateTime(now);
        int i= correctMapper.insert(correct);
        System.out.println(i);
    }


    @Test
    public void testgetCorrectByCoorectId(){
        Correct correct = correctService.selectByCorrectId(1L);
        System.out.println(correct);
    }

    @Test
    public void testInsertSelective(){
        CorrectLog correctLog = new CorrectLog();
        Date now = new Date();
        correctLog.setCorrectId(2L);
        correctLog.setOperateStatus("WAIT_AUDIT");
        correctLog.setOperatorType("admin");
        correctLog.setCreateTime(now);
        Integer num = coorectLogMapper.insert(correctLog);
        System.out.println(num);
    }


    @Test
    public void testUpdateCorrect(){
        System.out.println("开始testUpdateCorrect方法！！！");
        Correct correct = new Correct();
        Date now = new Date();
        correct.setCorrectId(23L);
        correct.setStatus("AFFECTED");
        correct.setActualStartTime(now);
        correct.setUpdateTime(now);
        System.out.println(correct);
        correctService.updateCorrect(correct);
        System.out.println("testUpdateCorrect方法结束！");
    }

    @Test
    public void testDeleteSupermarketGoods(){
        supermarketGoodsMapper.deleteSupermarketGoods(11,30L);

    }

    @Test
    public void testInsertSupermarketGoods(){
        SupermarketGoods goods = new SupermarketGoods();
        goods.setSupermarketId(1);
        goods.setGoodsSkuId(1L);
        goods.setPrice(111);
        goods.setCorrectId(1L);
        supermarketGoodsMapper.insert(goods);
    }

    @Test
    public void testGetCommunityIdBysupermarketId(){
        List<Integer> communityId = communitySupermarketService.getCommunityIdBysupermarketId(29);
        System.out.println(communityId.size());
        System.out.println(communityId);

    }

    @Test
    public void testSelectMessageByUserId(){
        List<Message> messages = messageService.selectMessageByUserId(101);
        System.out.println(messages.size());
    }

    @Test
    public void testUpdateSelfOrderMessageStatusByPrimaryKey(){
        selfOrderMessageService.updateSelfOrderMessageStatusByPrimaryKey("RIGHT","GOODS",2);
    }

    @Test
    public void testSelectSelfOrderMessageBySelfOrderId(){
        selfOrderMessageService.selectSelfOrderMessageBySelfOrderId(1L);
    }

    @Test
    public void testUpdateSelfOrderStatusByPrimaryKey(){
        selfOrderService.updateSelfOrderStatusByPrimaryKey(4L,"SUCCESS");
    }

    @Test
    public void testSendAllMessage(){
        OrdersSku ordersSku = new OrdersSku();
        ordersSku.setOrdersId(34);
        OrdersSku SupermarketList =orderService.getOrderSupermarketList(ordersSku);
        System.out.println("orderSList===="+SupermarketList.getSupermarketList());
        List<SupermarketGoods> sgList = new ArrayList<>();
        if(SupermarketList.getSupermarketList()==null||SupermarketList.getSupermarketList().equals("")){

        }else{
            String sL = SupermarketList.getSupermarketList();
            sL.replace("[{","");
            sL.replace("}]","");
            String[] split = sL.split(",");
            SupermarketGoods sg = new SupermarketGoods();
            for (String s:split){
                String[] split1 = s.split(",");
                for (int i=0;i<split1.length;i++){
                    if(i==0){
                        String[] split2 = split1[0].split(":");
                        Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(Integer.valueOf(split2[1]));
                        sg.setSupermarketName(supermarket.getName());
                    }
                    if(i==1){
                        String[] split2 = split1[0].split(":");
                        sg.setPrice(Integer.valueOf(split2[1]));
                    }
                    sgList.add(sg);
                }
            }
        }
    }
    @Test
    public void test(){
      redisTemplate.opsForValue().set("abv","vvba");
    }


}