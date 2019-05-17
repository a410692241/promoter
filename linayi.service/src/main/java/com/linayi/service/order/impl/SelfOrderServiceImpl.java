package com.linayi.service.order.impl;

import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.community.CommunitySupermarketMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.order.SelfOrderMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.order.SelfOrder;
import com.linayi.entity.order.SelfOrderMessage;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.Message;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.enums.MemberLevel;
import com.linayi.enums.SelfOrderStatus;
import com.linayi.service.goods.BrandService;
import com.linayi.service.goods.GoodsSkuService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.order.OrderService;
import com.linayi.service.order.SelfOrderMessageService;
import com.linayi.service.order.SelfOrderService;
import com.linayi.service.promoter.OpenMemberInfoService;
import com.linayi.service.user.MessageService;
import com.linayi.util.MemberPriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("selfOrderService")
public class SelfOrderServiceImpl implements SelfOrderService {

    @Autowired
    private SelfOrderMapper selfOrderMapper;
    @Autowired
    private SelfOrderMessageService selfOrderMessageService;
    @Autowired
    private CommunityMapper communityMapper;
    @Resource
    private CommunitySupermarketMapper communitySupermarketMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;
    @Autowired
    SmallCommunityMapper smallCommunityMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;
    @Autowired
    private OrdersGoodsMapper ordersGoodsMapper;
    @Autowired
    SupermarketMapper supermarketMapper;
    @Autowired
    private ProcurementTaskMapper procurementTaskMapper;
    @Autowired
    private OpenMemberInfoService openMemberInfoService;


    /* 内容格式 */
    String sharers;

    public List<SelfOrder> getList(SelfOrder selfOrder) {
        if ("处理成功".equals(selfOrder.getStatus())) {
            selfOrder.setStatus("SUCCESS");
        } else if ("处理失败".equals(selfOrder.getStatus())) {
            selfOrder.setStatus("FAIL");
        } else if ("等待处理".equals(selfOrder.getStatus())) {
            selfOrder.setStatus("WAIT_DEAL");
        } else if ("采价中".equals(selfOrder.getStatus())) {
            selfOrder.setStatus("PROCESSING");
        }
        return selfOrderMapper.getList(selfOrder);
    }

    public SelfOrder insertUserMessage(SelfOrder selfOrder) {
        selfOrderMapper.insertUserMessage(selfOrder);
        return selfOrder;
    }

    /**
     * 自定义下单三框插入
     *
     * @param selfOrder
     * @return
     */
    @Transactional
    @Override
    public void insertAllUserMessage(SelfOrder selfOrder) {
        selfOrder.setStatus("WAIT_DEAL");
//        Integer communityId = communityMapper.getcommunityIdByuserId(selfOrder.getUserId());
//        selfOrder.setReceivedAddressId(communityId);
//        通知分享员功能留给客服操作
//        通过用户id获取社区id
//        Integer communityId = communityMapper.getcommunityIdByuserId(selfOrder.getUserId());
//        根据社区id获取绑定的超市id集合
//        List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);
//        int index = 0;
//        int num = 0;
//        for (Integer i : supermarketIdList) {
//            Supermarket supermarket = selfOrderMapper.findSupermarketById(i);
//            if (supermarket != null) {
//                num++;
//            }
//        }
//        sharers = "[";
//        for (Integer sid : supermarketIdList) {
//            Supermarket supermarket = findSupermarketById(sid);
//            if (supermarket != null) {
//                index++;
//                sharers += "{\"supermarket_id\":" + supermarket.getSupermarketId() + ",\"user_id\":" + supermarket.getUserId() + "}";
//                if (index == num) {
//                    break;
//                }
//                sharers += ",";
//            }
//        }
//        sharers += "]";
//        selfOrder.setSharers(sharers);
        selfOrder.setSharers("");
        selfOrder.setUpdateTime(new Date());
        selfOrder.setCreateTime(new Date());
        insertUserMessage(selfOrder);
//        SelfOrder soid = insertUserMessage(selfOrder);
//        for (Integer s : supermarketIdList) {
//            Supermarket sm = findSupermarketById(s);
//            if (sm != null) {
//                SelfOrderMessage selfOrderMessage = new SelfOrderMessage(soid.getSelfOrderId(), "WAIT_DEAL", sm.getUserId(), new Date(), new Date());
//                Message msg = new Message("SELF_ORDER", "您有一条新消息,请注意查收!", null, sm.getUserId(), "SHARER", "NOT_VIEW", new Date(), new Date());
//                selfOrderMessageService.sendBuyerMessage(selfOrderMessage);
//                messageService.sendAllMessage(msg);
//            }
//        }
    }

    @Override
    public Supermarket findSupermarketById(Integer supermarketId) {
        return selfOrderMapper.findSupermarketById(supermarketId);
    }

    //根据用户Id获取自定义列表
    @Override
    public List<SelfOrder> getSelfOrderByUserId(GoodsSku goodsSku) {
        //根据userId获取社区信息
        Community community = new Community();
        community.setCommunityId(communityMapper.getcommunityIdByuserId(goodsSku.getUserId()));
        Community community1 = communityMapper.getCommunity(community);
        List<SelfOrder> selfOrderList = selfOrderMapper.getSelfOrderByUserId(goodsSku);
        for (SelfOrder selfOrder : selfOrderList) {
            selfOrder.setCommunityPhone(community1.getMobile());
        }
        return selfOrderList;
    }

    //据主键id查询自定义下单表
    @Override
    public SelfOrder selectByPrimaryKey(Long selfOrderId) {

        return selfOrderMapper.selectByPrimaryKey(selfOrderId);
    }

    public void updateSelfOrderStatusByPrimaryKey(SelfOrder selfOrder) {
        selfOrderMapper.updateSelfOrderStatusByPrimaryKey(selfOrder);
    }

    @Override
    public SelfOrder getSelfOrder(SelfOrder selfOrder) {
        return selfOrderMapper.getSelfOrder(selfOrder);
    }


    @Override
    public SelfOrder updateSelfOrder(SelfOrder selfOrder) {
        if (selfOrder.getSelfOrderId() != null) {
            Date date = new Date();
            selfOrder.setUpdateTime(date);
            selfOrderMapper.updateSelfOrder(selfOrder);
        }
        return selfOrder;
    }

    @Override
    @Transactional
    public SelfOrder sharePrice(SelfOrder selfOrder) {
        //通过用户id获取社区id
        Integer communityId = communityMapper.getcommunityIdByuserId(selfOrder.getUserId());
        //根据社区id获取绑定的超市id集合
        List<Integer> supermarketIdList = communitySupermarketMapper.getSupermarketIdList(communityId);
        MemberLevel memberLevel = openMemberInfoService.getCurrentMemberLevel(selfOrder.getUserId());
        int index = 0, num = MemberPriceUtil.levelAndSupermarketNum.get(memberLevel.toString());
        supermarketIdList = supermarketIdList.size() > num ? supermarketIdList.subList(0, num) : supermarketIdList;
        sharers = "[";
        for (Integer sid : supermarketIdList) {
            Supermarket supermarket = findSupermarketById(sid);
            if (supermarket != null) {
                index++;
                if (supermarket.getUserId() != null) {
                    sharers += "{\"supermarket_id\":" + supermarket.getSupermarketId() + ",\"user_id\":" + supermarket.getUserId() + "}";
                }
                if (index == num) {
                    break;
                } else {
                    if (supermarket.getUserId() != null) {
                        sharers += ",";
                    }
                }
            }
        }
        sharers += "]";
        selfOrder.setSharers(sharers);
        for (Integer s : supermarketIdList) {
            Supermarket supermarket = findSupermarketById(s);
            if (supermarket != null) {
                if (supermarket.getUserId() != null){
                    SelfOrderMessage message = new SelfOrderMessage(selfOrder.getSelfOrderId(), "WAIT_DEAL", supermarket.getUserId(), new Date(), new Date());
                    Message msg = new Message("SELF_ORDER", "您有一条新消息,请注意查收!", null, supermarket.getUserId(), "SHARER", "NOT_VIEW", new Date(), new Date());
                    selfOrderMessageService.sendBuyerMessage(message);
                    messageService.sendAllMessage(msg);
                }
            }
        }
        selfOrder.setStatus("PROCESSING");
        selfOrder.setSharers(sharers);
        updateSelfOrder(selfOrder);
        return selfOrder;
    }

    @Override
    public List<Map> listSelfOrderMessage(Long selfOrderId) {
        return selfOrderMapper.listSelfOrderMessage(selfOrderId);
    }

    @Override
    public List<GoodsSku> searchGoods(GoodsSku goodsSku) {
        Integer communityId = communityMapper.getcommunityIdByuserId(goodsSku.getUserId());
        goodsSku.setCommunityId(communityId);
        return goodsSkuMapper.customSearch(goodsSku);
    }

    @Override
    @Transactional
    public void turnSelfOrderToOrder(
            Long selfOrderId,
            Integer userId,
            Integer goodsSkuId,
            Integer num,
            Integer amount,
            Integer saveAmount,
            Integer serviceFee,
            Integer extraFee
    ) {
        User user = userMapper.selectUserByuserId(userId);
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        String payWay = "CASH_PAY";

        //获取收货地址
        ReceiveAddress receiveAddress = new ReceiveAddress();
        receiveAddress.setReceiveAddressId(receiveAddressId);
        List<ReceiveAddress> receiveAddresses = receiveAddressMapper.query(receiveAddress);
        SmallCommunity smallCommunity = new SmallCommunity();
        if (receiveAddresses != null && receiveAddresses.size() > 0) {
            receiveAddress = receiveAddresses.get(0);
            smallCommunity.setSmallCommunityId(receiveAddress.getAddressOne());
            smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);
        }
        MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(userId);
        Orders order = OrderServiceImpl.generateOrders(userId, payWay, "", amount, saveAmount, extraFee, serviceFee, num, receiveAddress, smallCommunity, receiveAddressId, null);
        order.setOrderType("'SELF_ORDER'");
        order.setAddressType("MINE");
        ordersMapper.insert(order);

        List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(goodsSkuId, smallCommunity.getCommunityId());
        MemberPriceUtil.supermarketPriceByLevel(currentMemberLevel, supermarketGoodsList);

        OrdersGoods ordersGoods = orderService.generateOrdersGoods(order, MemberPriceUtil.supermarketGoods, MemberPriceUtil.allSpermarketGoodsList, num, goodsSkuId);
        ordersGoodsMapper.insert(ordersGoods);
        SupermarketGoods supermarketGoods = MemberPriceUtil.allSpermarketGoodsList.get(MemberPriceUtil.allSpermarketGoodsList.size() - 1);
        Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(supermarketGoods.getSupermarketId());
        supermarket.setPrice(supermarketGoods.getPrice());
        supermarket.setSupermarketId(supermarket.getSupermarketId());

        //待采买任务
        ProcurementTask procurementTask = OrderServiceImpl.generateProcurementTask(smallCommunity, ordersGoods, supermarket);
        procurementTaskMapper.insert(procurementTask);

        GoodsSku goodsSku = goodsSkuMapper.getGoodsById(goodsSkuId);
        goodsSku.setSoldNum(goodsSku.getSoldNum() == null ? 0 : goodsSku.getSoldNum() + num);
        goodsSkuMapper.update(goodsSku);

        SelfOrder selfOrder = new SelfOrder();
        selfOrder.setSelfOrderId(selfOrderId);
        selfOrder.setIsOrderSuccess("SUCCESS");
        updateSelfOrder(selfOrder);
    }

    @Override
    public User selectUserById(Integer userId) {
        return userMapper.selectUserByuserId(userId);
    }
}
