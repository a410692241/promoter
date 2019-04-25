package com.linayi.service.promoter.impl;

import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.promoter.OpenMemberInfoMapper;
import com.linayi.dao.promoter.OrderManMemberMapper;
import com.linayi.dao.promoter.PromoterOrderManMapper;
import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.entity.promoter.OrderManMember;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.user.UserService;
import com.linayi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PromoterOrderManServiceImpl implements PromoterOrderManService {
    @Autowired
    private PromoterOrderManMapper promoterOrderManMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderManMemberMapper orderManMemberMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OpenMemberInfoMapper openMemberInfoMapper;
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;

    @Override
    public PromoterOrderMan promoterIndex(PromoterOrderMan promoterOrderMan) {
        //通过推广商id获取订单数，订单总金额，下单员数等信息
        PromoterOrderMan currentPromoterOrderMan = getStatisALL(promoterOrderMan.getOrderManId(),"MONTH","ALL",null);
        PromoterOrderMan currentPromoterOrderMan2 = getStatisALL(promoterOrderMan.getOrderManId(),"ALL","ALL",null);
        //总收入
        currentPromoterOrderMan.setHomePageIncome(currentPromoterOrderMan2.getTotalSum() == null ? 0 : currentPromoterOrderMan2.getTotalSum());
        //会员数
        currentPromoterOrderMan.setNumberOfMembers(currentPromoterOrderMan2.getNumberOfMembers() == null ? 0 : currentPromoterOrderMan2.getNumberOfMembers());

        if(currentPromoterOrderMan2.getNumberOfMembers() == null) {
            currentPromoterOrderMan.setNumberOfMembers(0);
        }

        //获取头像
        User user = userService.selectUserByuserId(promoterOrderMan.getOrderManId());
        //图片处理
        if(user.getHeadImage() == null) {
            currentPromoterOrderMan.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
        }else {
            String headImage = user.getHeadImage();
            currentPromoterOrderMan.setHeadImage(headImage);
        }


        return currentPromoterOrderMan;
    }

    @Override
    public PromoterOrderMan myTeamOrderStatistics(PromoterOrderMan promoterOrderMan) {
        //通过推广商id获取订单数，订单总金额，下单员数等信息
        PromoterOrderMan currentPromoterOrderMan = getStatisALL(promoterOrderMan.getOrderManId(), promoterOrderMan.getDate(),"ALLVIP",null);
        currentPromoterOrderMan.setOrderStatisticsData3(currentPromoterOrderMan.getNumberOfOrderMan());
        if(currentPromoterOrderMan.getNumberOfOrderMan() == null) {
            currentPromoterOrderMan.setOrderStatisticsData3(0);
        }
        return currentPromoterOrderMan;
    }

    @Override
    public List<PromoterOrderMan> orderManList(PromoterOrderMan promoterOrderMan) {
        // 获取当前下单员的推广商id，根据推广商id查询该推广商下的所有下单员信息
        PromoterOrderMan currentPromoterOrderMan = promoterOrderManMapper.getPromoterOrderManByOrderManId(promoterOrderMan.getOrderManId());
        promoterOrderMan.setPromoterId(currentPromoterOrderMan.getPromoterId());
        promoterOrderMan.setOrderManId(null);
        List<PromoterOrderMan> orderManList = promoterOrderManMapper.getPromoterOrderManList(promoterOrderMan);
        String range = "ALL";
        // 遍历所有下单员，获取相应信息
        for (PromoterOrderMan orderMan : orderManList) {
            // 获取下单员昵称头像
            User user = userService.selectUserByuserId(orderMan.getOrderManId());
            orderMan.setNickname(user.getNickname());
            if(user.getHeadImage() == null) {
                orderMan.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
            }else {
                String headImage = user.getHeadImage();
                orderMan.setHeadImage(headImage);
            }

            // 获取下单员的会员数、订单数、订单总金额,将信息set进OrderMan对象
            PromoterOrderMan paramPromoterOrderMan = getStatisALL(orderMan.getOrderManId(), range,"VIP",null);
            orderMan.setNumberOfMembers(paramPromoterOrderMan.getNumberOfMembers());
            orderMan.setNumberOfOrders(paramPromoterOrderMan.getNumberOfOrders());
            orderMan.setTotalSum(paramPromoterOrderMan.getTotalSum());
        }
        return orderManList;
    }

    @Override
    public PromoterOrderMan memberListOrderStatistics(PromoterOrderMan promoterOrderMan) {
        //通过推广商id获取订单数，订单总金额，会员数等信息
        PromoterOrderMan currentPromoterOrderMan = getStatisALL(promoterOrderMan.getOrderManId(), promoterOrderMan.getDate(),"VIP",null);
        currentPromoterOrderMan.setOrderStatisticsData3(currentPromoterOrderMan.getNumberOfMembers());
        if(currentPromoterOrderMan.getNumberOfMembers() == null) {
            currentPromoterOrderMan.setOrderStatisticsData3(0);
        }
        return currentPromoterOrderMan;
    }

    @Override
    public List<OrderManMember> memberList(PromoterOrderMan promoterOrderMan) {
        // 根据下单员id查询他的所有会员
        promoterOrderMan.setCreateTime(new Date());
        List<OrderManMember> memberList = orderManMemberMapper.getOrderManMemberListByOrderManId(promoterOrderMan);
        // 遍历所有会员，获取相应信息
        for (OrderManMember member : memberList) {
            // 获取会员昵称头像
            User user = userService.selectUserByuserId(member.getMemberId());
            member.setNickname(user.getNickname());
            if(user.getHeadImage() == null) {
                member.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
            }else {
                String headImage = user.getHeadImage();
                member.setHeadImage(headImage);
            }
            // 获取会员的订单数、订单总金额
            PromoterOrderMan currentPromoterOrderMan = getStatisVIP(promoterOrderMan.getOrderManId(),member.getMemberId(), "ALL");
            //将查询处理的订单数和金额set进member里
            member.setNumberOfOrders(currentPromoterOrderMan.getNumberOfOrders());
            member.setTotalSum(currentPromoterOrderMan.getTotalSum());
            //获取开通会员时间
            OpenMemberInfo openMemberInfo =new OpenMemberInfo();
            openMemberInfo.setUserId(member.getMemberId());
            List<OpenMemberInfo> currentOpenMemberInfo = openMemberInfoMapper.getMemberStartTimeByUserId(openMemberInfo);
            member.setOpenMemberTime(currentOpenMemberInfo.get(0).getStartTime());
        }
        return memberList;
    }

    /**
     * 在那个下单员邀请成为会员时所下的单
     * @param orderManId
     * @param userId
     * @return
     */
    @Override
    public PromoterOrderMan getStatisVIP(Integer orderManId, Integer userId, String range) {
        Integer numberOfOrders = 0;    //订单数(通用)
        Integer totalSum = 0;    //订单合计金额(通用)
        Integer totalHundredSum = 0;    //订单合计大于100金额(通用)
        PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
        Orders orders = getTimeRange(range);
        //下单员会员订单数
        //orders.setOrderManId(orderManId);
        orders.setUserId(userId);
        List<Orders> ordersList = ordersMapper.getOrdersByUserIdAndOrderManId(orders);
        if(ordersList != null && ordersList.size() > 0){
            numberOfOrders +=ordersList.size();
            for (Orders orders1 : ordersList) {
                if(orders1.getAmount() >= 100){
                    totalHundredSum += orders1.getAmount();
                }
                totalSum += orders1.getAmount();
            }
        }
        promoterOrderMan.setTotalHundredSum(totalHundredSum);
        promoterOrderMan.setNumberOfOrders(numberOfOrders);
        promoterOrderMan.setTotalSum(totalSum);
        return promoterOrderMan;
    }


    @Override
    public PromoterOrderMan getStatisALL(Integer manId, String range, String type, Orders order) {
        Orders orders = getTimeRange(range);
        if (order != null){
            orders.setCreateTimeStart(order.getCreateTimeStart());
            orders.setCreateTimeEnd(order.getCreateTimeEnd());
        }
        PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
        promoterOrderMan.setOrderManId(manId);
        promoterOrderMan = promoterOrderManMapper.getPromoterOrderMan(promoterOrderMan);
        if (promoterOrderMan != null) {
            //不是会员
            String identity = promoterOrderMan.getIdentity();
            Integer numberOfOrderMan = 0;    //下单员数量(通用)
            Integer numberOfOrders = 0;    //订单数(通用)
            Integer totalSum = 0;    //订单合计金额(通用)
            Integer numberOfMembers = 0;    //会员数量(通用)
            Integer totalHundredSum = 0;    //订单合计大于100金额(通用)
            if ("ORDER_MAN".equals(identity)) {
                //下单员
                promoterOrderMan = orderManTongji(manId, orders,type,identity);
                promoterOrderMan.setIdentity(identity);
            } else if ("LEGAL_MAN".equals(identity)) {
                //将parentType的值赋值给identity
                promoterOrderMan.setIdentity(promoterOrderMan.getParentType());
                //法人
                PromoterOrderMan promoterOrderMan1 = new PromoterOrderMan();
                promoterOrderMan1.setPromoterId(promoterOrderMan.getPromoterId());
                if (!"ALL".equals(type) && !"ALLVIP".equals(type)){
                    promoterOrderMan1.setOrderManId(manId);
                }
                //此推广商下的有效的下单员(包含法人)
                List<PromoterOrderMan>  promoterOrderManList = promoterOrderManMapper.getPromoterOrderManList(promoterOrderMan1);

                if (promoterOrderManList != null && promoterOrderManList.size() > 0) {
                    numberOfOrderMan = promoterOrderManList.size();
                    //下单员自己下的单
                    PromoterOrderMan tongji = orderManTongji(manId, orders,type,identity);
                    numberOfMembers += tongji.getNumberOfMembers() == null ? 0 : tongji.getNumberOfMembers();
                    totalSum += tongji.getTotalSum();
                    totalHundredSum += tongji.getTotalHundredSum();
                    numberOfOrders += tongji.getNumberOfOrders();
                    promoterOrderMan.setTotalHundredSum(totalHundredSum);
                    promoterOrderMan.setNumberOfOrderMan(numberOfOrderMan);
                    promoterOrderMan.setNumberOfMembers(numberOfMembers);
                    promoterOrderMan.setTotalSum(totalSum);
                    promoterOrderMan.setNumberOfOrders(numberOfOrders);
                }
            }
        }
        return promoterOrderMan;
    }

    private PromoterOrderMan orderManTongji(Integer orderManId, Orders orders,String type,String identity) {
        Integer numberOfOrders = 0;    //订单数(通用)
        Integer totalSum = 0;    //订单合计金额(通用)
        Integer numberOfMembers = 0;    //会员数量(通用)
        Integer totalHundredSum = 0;    //订单合计大于100金额(通用)
        PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
        if ("ALL".equals(type)){
            //统计所有
            ReceiveAddress receiveAddress = new ReceiveAddress();
            OrderManMember orderManMember = new OrderManMember();
            //推广商
            promoterOrderMan.setOrderManId(orderManId);
            List<PromoterOrderMan>  promoterOrderManList = promoterOrderManMapper.getPromoterOrderManList(promoterOrderMan);
            if(promoterOrderManList != null && promoterOrderManList.size() > 0){
                promoterOrderMan = promoterOrderManList.get(0);
            }
            orders.setAddressType("CUSTOMER");
            List<Orders> allOrder = null;

            List<OrderManMember> orderManMemberList = null;
            if("ORDER_MAN".equals(identity)){
                //下单员
                orderManMember.setOrderManId(orderManId);
                receiveAddress.setUserId(orderManId);
                orders.setUserId(orderManId);
                orders.setOrderManId(orderManId);
                allOrder = ordersMapper.getOrdersByOrderMan(orders);
                //查找所有会员
                orderManMemberList = orderManMemberMapper.getOrderManMemberListByOrderManId(promoterOrderMan);
            }else if("LEGAL_MAN".equals(identity)){
                //法人
                orders.setPromoterId(promoterOrderMan.getPromoterId());
                receiveAddress.setPromoterId(promoterOrderMan.getPromoterId());
                allOrder = ordersMapper.getOrderByPromoter(orders);
                //查找所有会员
                orderManMemberList = orderManMemberMapper.getOrderManMemberListByPromoterId(promoterOrderMan.getPromoterId());
            }
            //顾客
            receiveAddress.setAddressType("CUSTOMER");
            List<ReceiveAddress> receiveAddresses = receiveAddressMapper.getAddressListByPromoter(receiveAddress);
            if (receiveAddresses != null && receiveAddresses.size() > 0){
                promoterOrderMan.setNumberOfMembers(receiveAddresses.size());
                if (allOrder != null && allOrder.size() > 0){
                    numberOfOrders += allOrder.size();
                    for (Orders orders2 : allOrder) {
                        if(orders2.getAmount() >= 100){
                            totalHundredSum += orders2.getAmount();
                        }
                        totalSum += orders2.getAmount();
                    }
                }
            }

            if (orderManMemberList != null && orderManMemberList.size() > 0){
                numberOfMembers += orderManMemberList.size();
            }
            //所有下单员会员所有的订单数
            List<Orders> ordersList = ordersMapper.getOrdersByPromoter(orders);
            if(ordersList != null && ordersList.size() > 0){
                numberOfOrders +=ordersList.size();
                for (Orders orders1 : ordersList) {
                    if(orders1.getAmount() >= 100){
                        totalHundredSum += orders1.getAmount();
                    }
                    totalSum += orders1.getAmount();
                }

            }
        }else if("CUSTOMER".equals(type)){
            //统计代顾客下单
            ReceiveAddress receiveAddress = new ReceiveAddress();
            receiveAddress.setUserId(orderManId);
            receiveAddress.setAddressType("CUSTOMER");
            receiveAddress.setCreateTimeStart(orders.getCreateTimeStart());
            receiveAddress.setCreateTimeEnd(orders.getCreateTimeEnd());
            List<ReceiveAddress> receiveAddresses = receiveAddressMapper.getAddressListByAddress(receiveAddress);
            if (receiveAddresses != null && receiveAddresses.size() > 0){
                numberOfMembers += receiveAddresses.size();
                orders.setAddressType("CUSTOMER");
                orders.setUserId(orderManId);
                List<Orders> allOrder = ordersMapper.getALLOrder(orders);
                if (allOrder != null && allOrder.size() > 0){
                    numberOfOrders += allOrder.size();
                    for (Orders orders2 : allOrder) {
                        totalSum += orders2.getAmount();
                        if(orders2.getAmount() >= 100){
                            totalHundredSum += orders2.getAmount();
                        }
                    }
                }
            }

        }else if("VIP".equals(type)){
            //会员
            OrderManMember orderManMember = new OrderManMember();
            orderManMember.setOrderManId(orderManId);
            orderManMember.setCreateTimeEnd(orders.getCreateTimeEnd());
            orderManMember.setCreateTimeStart(orders.getCreateTimeStart());
            //查找所有会员
            List<OrderManMember>  orderManMemberList = orderManMemberMapper.getOrderManMemberList(orderManMember);
            if (orderManMemberList != null && orderManMemberList.size() > 0){
                numberOfMembers += orderManMemberList.size();
            }
            //下单员会员订单数
            orders.setOrderManId(orderManId);
            List<Orders> ordersList = ordersMapper.getOrdersByPromoter(orders);
            if(ordersList != null && ordersList.size() > 0){
                numberOfOrders +=ordersList.size();
                for (Orders orders1 : ordersList) {
                    totalSum += orders1.getAmount();
                    if(orders1.getAmount() >= 100){
                        totalHundredSum += orders1.getAmount();
                    }
                }
            }
        }else if("ALLVIP".equals(type)){
            //推广商
            promoterOrderMan.setOrderManId(orderManId);
            List<PromoterOrderMan>  promoterOrderManList = promoterOrderManMapper.getPromoterOrderManList(promoterOrderMan);
            if(promoterOrderManList != null && promoterOrderManList.size() > 0){
                promoterOrderMan = promoterOrderManList.get(0);
            }
            //会员
            //查找所有会员
            List<OrderManMember> orderManMemberList = orderManMemberMapper.getOrderManMemberListByPromoterId(promoterOrderMan.getPromoterId());
            if (orderManMemberList != null && orderManMemberList.size() > 0){
                numberOfMembers += orderManMemberList.size();
            }
            orders.setPromoterId(promoterOrderMan.getPromoterId());
            //下单员会员订单数
            List<Orders> ordersList = ordersMapper.getOrdersByPromoter(orders);
            if(ordersList != null && ordersList.size() > 0){
                numberOfOrders +=ordersList.size();
                for (Orders orders1 : ordersList) {
                    totalSum += orders1.getAmount();
                    if(orders1.getAmount() >= 100){
                        totalHundredSum += orders1.getAmount();
                    }
                }
            }
        }
        promoterOrderMan.setNumberOfOrders(numberOfOrders);
        promoterOrderMan.setTotalSum(totalSum);
        promoterOrderMan.setNumberOfMembers(numberOfMembers);
        promoterOrderMan.setTotalHundredSum(totalHundredSum);
        return promoterOrderMan;
    }


    private Orders getTimeRange(String range) {
        Orders orders =new Orders();
        if ("MONTH".equals(range)) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(new Date());
            cl.set(Calendar.DAY_OF_MONTH, 1);
            cl.set(Calendar.HOUR_OF_DAY, 0);
            cl.set(Calendar.MINUTE, 0);
            cl.set(Calendar.SECOND, 0);
            orders.setCreateTimeStart(DateUtil.date2String(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
            cl.add(Calendar.MONTH, 1);
            orders.setCreateTimeEnd(DateUtil.date2String(cl.getTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        return orders;
    }
}
