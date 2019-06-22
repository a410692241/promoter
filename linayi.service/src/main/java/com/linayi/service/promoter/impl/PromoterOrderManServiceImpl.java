package com.linayi.service.promoter.impl;

import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.promoter.*;
import com.linayi.dao.user.AuthenticationApplyMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.order.Orders;
import com.linayi.entity.promoter.*;
import com.linayi.entity.user.AuthenticationApply;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.User;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.promoter.PromoterOrderManService;
import com.linayi.service.user.UserService;
import com.linayi.util.DateUtil;
import com.linayi.util.ImageUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    @Autowired
    private OpenOrderManInfoMapper openOrderManInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PromoterMapper promoterMapper;
    @Autowired
    private AuthenticationApplyMapper authenticationApplyMapper;

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
            String headImage = ImageUtil.dealToShow(user.getHeadImage());
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
                String headImage = ImageUtil.dealToShow(user.getHeadImage());
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
                String headImage = ImageUtil.dealToShow(user.getHeadImage());
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
            member.setOpenMemberTime(currentOpenMemberInfo.get(0).getCreateTime());
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
            if("ORDER_MAN".equals(identity.trim())){
                //下单员
                orderManMember.setOrderManId(orderManId);
                receiveAddress.setUserId(orderManId);
                orders.setUserId(orderManId);
                orders.setOrderManId(orderManId);
                allOrder = ordersMapper.getOrdersByOrderMan(orders);
                //查找所有会员
                orderManMemberList = orderManMemberMapper.getOrderManMemberListByOrderManId(promoterOrderMan);
            }else if("LEGAL_MAN".equals(identity.trim())){
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
    @Override
    @Transactional
    public void openOrderManByPromoter(OpenOrderManInfo openOrderManInfo) {
        PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
        promoterOrderMan.setOrderManId(openOrderManInfo.getOrderManId());
        promoterOrderMan.setPromoterId(openOrderManInfo.getPromoterId());
        promoterOrderMan.setIdentity(openOrderManInfo.getIdentity());
        promoterOrderMan.setCreateTime(new Date());
        promoterOrderMan.setParentType(openOrderManInfo.getParentType());
        promoterOrderManMapper.insert(promoterOrderMan);

        Calendar c = Calendar.getInstance();  //得到当前日期和时间
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND,0);
        Date startTime = c.getTime();
        openOrderManInfo.setStartTime(startTime);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND,0);
        Date endTime = cal.getTime();
        openOrderManInfo.setEndTime(endTime);
        openOrderManInfo.setCreateTime(new Date());
        openOrderManInfoMapper.insert(openOrderManInfo);

        User user = new User();
        user.setUserId(openOrderManInfo.getOrderManId());
        user.setOpenOrderManInfoId(openOrderManInfo.getOpenOrderManInfoId());
        user.setIsSharer("FALSE");
        user.setIsProcurer("FALSE");
        user.setIsOrderMan("TRUE");
        userMapper.updateUserByuserId(user);
    }

    @Override
    @Transactional
    public void applyOrderManInWeb(String status,Integer applyId,Integer userId,Integer promoterId,String identity){
        AuthenticationApply apply = new AuthenticationApply();
        apply.setStatus(status);
        apply.setApplyId(applyId);
        if("AUDIT_SUCCESS".equals(status)){
            Promoter promoter = promoterMapper.getPromoterById(promoterId);
            apply.setPromoterId(promoterId);
            apply.setIdentity(identity);
            authenticationApplyMapper.updateApplyOrederManInfoById(apply);

            PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
            promoterOrderMan.setOrderManId(userId);
            promoterOrderMan.setPromoterId(promoterId);
            promoterOrderMan.setIdentity(identity);
            promoterOrderMan.setCreateTime(new Date());
            promoterOrderMan.setParentType(promoter.getPromoterType());
            promoterOrderManMapper.insert(promoterOrderMan);

            OpenOrderManInfo openOrderManInfo = new OpenOrderManInfo();
            Calendar c = Calendar.getInstance();  //得到当前日期和时间
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND,0);
            Date startTime = c.getTime();
            openOrderManInfo.setStartTime(startTime);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR,1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.MILLISECOND,0);
            Date endTime = cal.getTime();
            openOrderManInfo.setEndTime(endTime);
            openOrderManInfo.setCreateTime(new Date());
            openOrderManInfo.setOrderManLevel("1");
            openOrderManInfo.setPromoterId(promoterId);
            openOrderManInfo.setOrderManId(userId);
            openOrderManInfo.setIdentity(identity);
            openOrderManInfoMapper.insert(openOrderManInfo);

            User userInfo = userMapper.selectUserByuserId(userId);
            User user = new User();
            if("FALSE".equals(userInfo.getIsMember())){
                OpenMemberInfo info = new OpenMemberInfo();
                info.setMemberLevel("NORMAL");
                info.setStartTime(startTime);
                info.setEndTime(endTime);
                info.setUserId(userId);
                info.setOrderManId(userId);
                info.setFreeTimes(0);
                info.setCreateTime(new Date());
                info.setOpenOrderManInfoId(openOrderManInfo.getOpenOrderManInfoId());
                openMemberInfoMapper.insert(info);

                OrderManMember orderManMember = new OrderManMember();
                orderManMember.setMemberId(userId);
                orderManMember.setOrderManId(userId);
                orderManMember.setCreateTime(new Date());
                orderManMemberMapper.insert(orderManMember);

                user.setIsMember("TRUE");
                user.setOpenMemberInfoId(info.getOpenMemberInfoId());
            }

            user.setUserId(userId);
            user.setOpenOrderManInfoId(openOrderManInfo.getOpenOrderManInfoId());
            user.setIsSharer("FALSE");
            user.setIsProcurer("FALSE");
            user.setIsOrderMan("TRUE");
            userMapper.updateUserByuserId(user);
        }else if("AUDIT_FAIL".equals(status)){
            authenticationApplyMapper.updateApplyOrederManInfoById(apply);
        }
    }

    //家庭服务师列表（新）
    @Override
    public List<PromoterOrderMan> getOpenOrderManInfoList(PromoterOrderMan promoterOrderMan) {
        List<PromoterOrderMan> promoterOrderMEN = openOrderManInfoMapper.getOpenOrderManInfoList(promoterOrderMan);
        for (PromoterOrderMan promoterOrderMAN : promoterOrderMEN) {
            if(promoterOrderMAN.getHeadImage() == null) {
                promoterOrderMAN.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
            }else {
                String headImage = ImageUtil.dealToShow(promoterOrderMAN.getHeadImage());
                promoterOrderMAN.setHeadImage(headImage);
            }
        }
        return promoterOrderMEN;
    }

    @Override
    public PromoterOrderMan getOpenOrderManOrderList(PromoterOrderMan promoterOrderMan) {
        //获取家庭服务师列表
        List<PromoterOrderMan> promoterOrderMEN = openOrderManInfoMapper.getOpenOrderManInfoList(promoterOrderMan);
        Integer numberOfOrders = 0;
        Integer totalSum = 0;
        Integer orderProfit = 0;
        if (promoterOrderMEN.size()>0) {
            for (PromoterOrderMan promoterOrderMAN : promoterOrderMEN) {
                OpenOrderManInfo openOrderManInfo = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(promoterOrderMAN.getOrderManId()).stream().findFirst().orElse(null);
                String openOrderManLevel = openOrderManInfo.getOrderManLevel();
                int personalProfit = 0;
                if ("1".equals(openOrderManLevel)){
                    //个人收益(一级) 订单数*2 + 有效销售额*0.8% + 个人订单数大于等于10奖励100
                    personalProfit = (int) (promoterOrderMAN.getNumberOfOrders() * 200 + promoterOrderMAN.getPersonalSales() * 0.008 + (promoterOrderMAN.getNumberOfOrders() >= 10 ? 10000 : 0));
                }else if ("2".equals(openOrderManLevel)){
                    //个人收益(二级) 订单数*2.5 + 有效销售额*1% + 个人订单数大于等于10奖励100
                    personalProfit = (int) (promoterOrderMAN.getNumberOfOrders() * 250 + promoterOrderMAN.getPersonalSales() * 0.01 + (promoterOrderMAN.getNumberOfOrders() >= 10 ? 10000 : 0));
                }else if ("3".equals(openOrderManLevel)){
                    //个人收益(三级) 订单数*3 + 有效销售额*1.2% + 个人订单数大于等于10奖励100
                    personalProfit = (int) (promoterOrderMAN.getNumberOfOrders() * 300 + promoterOrderMAN.getPersonalSales() * 0.012 + (promoterOrderMAN.getNumberOfOrders() >= 10 ? 10000 : 0));
                }
                orderProfit+=personalProfit;
                numberOfOrders += promoterOrderMAN.getNumberOfOrders();
                totalSum += promoterOrderMAN.getTotalSum();
            }
        }
        PromoterOrderMan promoterOrder = new PromoterOrderMan();
        promoterOrder.setNumberOfOrders(numberOfOrders);
        promoterOrder.setTotalSum(totalSum);
        promoterOrder.setNumberOfOrderMan(promoterOrderMEN.size()-1);
        promoterOrder.setOrderProfit(orderProfit);
        return promoterOrder;
    }


    //邀请家庭服务师-无需审核版本（作废）
//    @Override
//    public void inviteOrderMan(AuthenticationApply apply, MultipartFile[] file) throws Exception {
//        Date nowTime = new Date();
//        //判断邀请人是否在有效期内
//        OpenOrderManInfo openOrderManInfo2 = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(apply.getUserId()).stream().findFirst().orElse(null);
//        if(openOrderManInfo2 == null || openOrderManInfo2.getEndTime().before(nowTime)){
//            throw new BusinessException(ErrorType.APPLY_ERROR);
//        }
//        //判断是否已经存在家庭服务师
//        OpenOrderManInfo openOrderManInfo1 = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(apply.getApplierId()).stream().findFirst().orElse(null);
//        if(openOrderManInfo1 != null && openOrderManInfo1.getEndTime().after(nowTime)){
//            throw new BusinessException(ErrorType.ORDER_MAN_ALREADY_EXIST);
//        }
//
//        //插入申请表
//        AuthenticationApply authenticationApply = new AuthenticationApply();
//        authenticationApply.setAddress(apply.getAddress());
//        authenticationApply.setRealName(apply.getRealName());
//        authenticationApply.setMobile(apply.getMobile());
//        authenticationApply.setUserId(apply.getUserId());
//        authenticationApply.setAreaCode(apply.getAreaCode());
//        authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
//        authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
//        authenticationApply.setCreateTime(new Date());
//        authenticationApply.setUpdateTime(new Date());
//        authenticationApply.setStatus("AUDIT_SUCCESS");
//        authenticationApply.setAuthenticationType("ORDER_MAN");
//        int rows = authenticationApplyMapper.insert(authenticationApply);
//
//        //插入家庭服务师相关表
//        OpenOrderManInfo openOrderManInfo = new OpenOrderManInfo();
//        Calendar c = Calendar.getInstance();  //得到当前日期和时间
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND,0);
//        Date startTime = c.getTime();
//        openOrderManInfo.setStartTime(startTime);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.YEAR,1);
//        cal.set(Calendar.HOUR_OF_DAY, 23);
//        cal.set(Calendar.SECOND, 59);
//        cal.set(Calendar.MINUTE, 59);
//        cal.set(Calendar.MILLISECOND,0);
//        Date endTime = cal.getTime();
//        openOrderManInfo.setEndTime(endTime);
//        openOrderManInfo.setCreateTime(new Date());
//        openOrderManInfo.setOrderManLevel("1");
//        if(openOrderManInfo2 != null){
//            openOrderManInfo.setPromoterId(openOrderManInfo2.getPromoterId());
//        }
//        openOrderManInfo.setOrderManId(apply.getApplierId());
//        openOrderManInfo.setSalesId(apply.getUserId());
//        openOrderManInfo.setIdentity("ORDER_MAN");
//        openOrderManInfoMapper.insert(openOrderManInfo);
//
////        User userInfo = userMapper.selectUserByuserId(apply.getApplierId());
//        User user = new User();
//        user.setUserId(apply.getApplierId());
//        user.setOpenOrderManInfoId(openOrderManInfo.getOpenOrderManInfoId());
//        user.setIsOrderMan("TRUE");
//        userMapper.updateUserByuserId(user);
//
//        //插入推广商下单员表（前期有用到此表，怕影响之前的接口，所以还是插入此表比较保险）
//        PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
//        promoterOrderMan.setOrderManId(apply.getApplierId());
//        if(openOrderManInfo2 != null){
//            promoterOrderMan.setPromoterId(openOrderManInfo2.getPromoterId());
//        }
//        promoterOrderMan.setIdentity("ORDER_MAN");
//        promoterOrderMan.setCreateTime(nowTime);
//        promoterOrderMan.setParentType("PROMOTER");
//        promoterOrderManMapper.insert(promoterOrderMan);
//    }

    //邀请家庭服务师-需要审核版本
    @Override
    public void inviteOrderMan(AuthenticationApply apply, MultipartFile[] file) throws Exception {
        Date nowTime = new Date();
        //判断邀请人是否在有效期内
        OpenOrderManInfo openOrderManInfo2 = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(apply.getUserId()).stream().findFirst().orElse(null);
        if(openOrderManInfo2 == null || openOrderManInfo2.getEndTime().before(nowTime)){
            throw new BusinessException(ErrorType.APPLY_ERROR);
        }
        //判断是否已经存在家庭服务师
        OpenOrderManInfo openOrderManInfo1 = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(apply.getApplierId()).stream().findFirst().orElse(null);
        if(openOrderManInfo1 != null && openOrderManInfo1.getEndTime().after(nowTime)){
            throw new BusinessException(ErrorType.ORDER_MAN_ALREADY_EXIST);
        }

        //插入申请表
        AuthenticationApply authenticationApply = new AuthenticationApply();
        authenticationApply.setAddress(apply.getAddress());
        authenticationApply.setRealName(apply.getRealName());
        authenticationApply.setMobile(apply.getMobile());
        authenticationApply.setUserId(apply.getApplierId());
        authenticationApply.setAreaCode(apply.getAreaCode());
        authenticationApply.setIdCardFront(ImageUtil.handleUpload(file[0]));
        authenticationApply.setIdCardBack(ImageUtil.handleUpload(file[1]));
        authenticationApply.setCreateTime(new Date());
        authenticationApply.setUpdateTime(new Date());
        authenticationApply.setStatus("WAIT_AUDIT");
        authenticationApply.setOrderManId(apply.getUserId());
        authenticationApply.setAuthenticationType("ORDER_MAN");
        int rows = authenticationApplyMapper.insert(authenticationApply);

    }

    @Override
    public void auditOrderMan(AuthenticationApply apply) {
       //校验申请信息是否为“待审核状态”
        if(!"WAIT_AUDIT".equals(apply.getStatus())){
            throw new BusinessException(ErrorType.AUDIT_ERROR);
        }
        Date nowTime = new Date();
        if("AUDIT_SUCCESS".equals(apply.getAuditStr())){
            OpenOrderManInfo openOrderManInfo2 = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(apply.getOrderManId()).stream().findFirst().orElse(null);
            OpenOrderManInfo openOrderManInfo = new OpenOrderManInfo();
            //开始时间和结束时间处理
            Calendar c = Calendar.getInstance();  //得到当前日期和时间
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND,0);
            Date startTime = c.getTime();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR,1);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.MILLISECOND,0);
            Date endTime = cal.getTime();
            //判断如果已经有家庭服务师信息，则修改信息，否则才新增家庭服务师信息
            OpenOrderManInfo paramOpenOrderManInfo = new OpenOrderManInfo();
            paramOpenOrderManInfo.setOrderManId(apply.getUserId());
            paramOpenOrderManInfo.setSalesId(apply.getOrderManId());
            OpenOrderManInfo currentOpenOrderManInfo = openOrderManInfoMapper.selectByOrderManIdAndSalesId(paramOpenOrderManInfo).stream().findFirst().orElse(null);
            if(currentOpenOrderManInfo == null){
                //插入家庭服务师相关表
                openOrderManInfo.setStartTime(startTime);
                openOrderManInfo.setEndTime(endTime);
                openOrderManInfo.setCreateTime(new Date());
                openOrderManInfo.setOrderManLevel("1");
                if(openOrderManInfo2 != null){
                    openOrderManInfo.setPromoterId(openOrderManInfo2.getPromoterId());
                }
                openOrderManInfo.setOrderManId(apply.getUserId());
                openOrderManInfo.setSalesId(apply.getOrderManId());
                openOrderManInfo.setIdentity("ORDER_MAN");
                openOrderManInfoMapper.insert(openOrderManInfo);
            }else{
                OpenOrderManInfo openOrderManInfo3 = new OpenOrderManInfo();
                openOrderManInfo3.setOpenOrderManInfoId(currentOpenOrderManInfo.getOpenOrderManInfoId());
                openOrderManInfo3.setOrderManLevel("1");
                openOrderManInfo3.setStartTime(startTime);
                openOrderManInfo3.setEndTime(endTime);
                openOrderManInfo3.setUpdateTime(nowTime);
                openOrderManInfoMapper.updateOpenOrderManInfo(openOrderManInfo3);
            }

            AuthenticationApply authenticationApply = new AuthenticationApply();
            authenticationApply.setApplyId(apply.getApplyId());
            authenticationApply.setStatus(apply.getAuditStr());
            authenticationApplyMapper.updateApplyOrederManInfoById(authenticationApply);

//        User userInfo = userMapper.selectUserByuserId(apply.getApplierId());
            User user = new User();
            user.setUserId(apply.getUserId());
            user.setOpenOrderManInfoId(openOrderManInfo.getOpenOrderManInfoId());
            user.setIsOrderMan("TRUE");
            userMapper.updateUserByuserId(user);

            //插入推广商下单员表（前期有用到此表，怕影响之前的接口，所以还是插入此表比较保险）
            PromoterOrderMan promoterOrderMan = new PromoterOrderMan();
            promoterOrderMan.setOrderManId(apply.getUserId());
            if(openOrderManInfo2 != null){
                promoterOrderMan.setPromoterId(openOrderManInfo2.getPromoterId());
            }
            promoterOrderMan.setIdentity("ORDER_MAN");
            promoterOrderMan.setCreateTime(nowTime);
            promoterOrderMan.setParentType("PROMOTER");
            promoterOrderManMapper.insert(promoterOrderMan);
        }
        if("AUDIT_FAIL".equals(apply.getAuditStr())){
            AuthenticationApply authenticationApply = new AuthenticationApply();
            authenticationApply.setApplyId(apply.getApplyId());
            authenticationApply.setStatus(apply.getAuditStr());
            authenticationApplyMapper.updateApplyOrederManInfoById(authenticationApply);
        }

    }


    //首页数据统计(本月包含收益)
    @Override
    public PromoterOrderMan getIndexData(PromoterOrderMan promoterOrderMan) {
        //个人月订单/金额/有效销售额
        PromoterOrderMan promoterOrder = openOrderManInfoMapper.getPersonalOrder(promoterOrderMan.getUserId());
        OpenOrderManInfo openOrderManInfo = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(promoterOrderMan.getUserId()).stream().findFirst().orElse(null);
        String openOrderManLevel = openOrderManInfo.getOrderManLevel();
        int personalProfit = 0;
        if ("1".equals(openOrderManLevel)){
            //个人收益(一级) 订单数*2 + 有效销售额*0.8% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrder.getNumberOfOrders() * 200 + promoterOrder.getPersonalSales() * 0.008 + (promoterOrder.getNumberOfOrders() >= 10 ? 10000 : 0));
        }else if ("2".equals(openOrderManLevel)){
            //个人收益(二级) 订单数*2.5 + 有效销售额*1% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrder.getNumberOfOrders() * 250 + promoterOrder.getPersonalSales() * 0.01 + (promoterOrder.getNumberOfOrders() >= 10 ? 10000 : 0));
        }else if ("3".equals(openOrderManLevel)){
            //个人收益(三级) 订单数*3 + 有效销售额*1.2% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrder.getNumberOfOrders() * 300 + promoterOrder.getPersonalSales() * 0.012 + (promoterOrder.getNumberOfOrders() >= 10 ? 10000 : 0));
        }
        promoterOrder.setPersonalProfit(personalProfit);
        Integer teamOfOrders = 0; //团队订单量
        Integer teamTotalSum = 0; //团队成交额
        Integer teamProfit = 0; //团队收益
        Integer teamSales = 0; //团队销售服务额
        //获取全部家庭服务师列表
        int count = 0;
        List<PromoterOrderMan> promoterOrderMEN = openOrderManInfoMapper.getOpenOrderManInfoList(promoterOrderMan);
        if (promoterOrderMEN.size() > 0) {
            promoterOrderMEN.remove(0);
            for (PromoterOrderMan promoterOrderMAN : promoterOrderMEN) {
                PromoterOrderMan teamPromoterOrder = openOrderManInfoMapper.getPersonalOrder(promoterOrderMAN.getOrderManId());
                if (teamPromoterOrder.getNumberOfOrders() > 10) {
                    count++;
                }
                teamOfOrders += teamPromoterOrder.getNumberOfOrders();
                teamTotalSum += teamPromoterOrder.getTotalSum();
                teamSales += teamPromoterOrder.getPersonalSales();
            }
        }
        if (teamOfOrders >= 50 && teamOfOrders < 100) {
            teamProfit = 10000;
        } else if (teamOfOrders >= 100 && teamOfOrders < 200) {
            teamProfit = 15000;
        } else if (teamOfOrders >= 200 && teamOfOrders < 500) {
            teamProfit = 32000;
        } else if (teamOfOrders >= 500 && teamOfOrders < 1000) {
            teamProfit = 85000;
        } else if (teamOfOrders >= 1000 && teamOfOrders < 2000) {
            teamProfit = 180000;
        } else if (teamOfOrders >= 2000 && teamOfOrders < 3000) {
            teamProfit = 380000;
        } else if (teamOfOrders >= 3000 && teamOfOrders < 4000) {
            teamProfit = 600000;
        } else if (teamOfOrders >= 4000 && teamOfOrders < 5000) {
            teamProfit = 880000;
        } else if (teamOfOrders >= 5000) {
            teamProfit = 1180000;
        }
        promoterOrder.setTeamOfOrders(teamOfOrders);
        promoterOrder.setTeamTotalSum(teamTotalSum);
        promoterOrder.setTeamProfit(teamProfit - (count * 10000)); //减去订单数大于10单以上的
        if(promoterOrder.getTeamProfit()<0){
            promoterOrder.setTeamProfit(0);
        }
        promoterOrder.setTeamSales(teamSales);
        promoterOrder.setOrderManId(promoterOrderMan.getUserId());
        //个人总收益
        promoterOrder.setPersonalTotalProfit(promoterOrder.getTeamProfit()+promoterOrder.getPersonalProfit());
        User user = userService.selectUserByuserId(promoterOrderMan.getUserId());
        if(user.getHeadImage() == null) {
            promoterOrder.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
        }else {
            String headImage = ImageUtil.dealToShow(user.getHeadImage());
            promoterOrder.setHeadImage(headImage);
        }
        return promoterOrder;
    }


    //代顾客下单数据统计
    @Override
    public PromoterOrderMan getPersonalOrderProfit(PromoterOrderMan promoterOrderMan) {
        PromoterOrderMan promoterOrder = openOrderManInfoMapper.getPersonalOrderProfit(promoterOrderMan);
        OpenOrderManInfo openOrderManInfo = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(promoterOrderMan.getUserId()).stream().findFirst().orElse(null);
        String openOrderManLevel = openOrderManInfo.getOrderManLevel();
        int personalProfit = 0;
        if ("1".equals(openOrderManLevel)){
            //个人收益(一级) 订单数*2 + 有效销售额*0.8% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrder.getNumberOfOrders() * 200 + promoterOrder.getPersonalSales() * 0.008 + (promoterOrder.getNumberOfOrders() >= 10 ? 10000 : 0));
        }else if ("2".equals(openOrderManLevel)){
            //个人收益(二级) 订单数*2.5 + 有效销售额*1% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrder.getNumberOfOrders() * 250 + promoterOrder.getPersonalSales() * 0.01 + (promoterOrder.getNumberOfOrders() >= 10 ? 10000 : 0));
        }else if ("3".equals(openOrderManLevel)){
            //个人收益(三级) 订单数*3 + 有效销售额*1.2% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrder.getNumberOfOrders() * 300 + promoterOrder.getPersonalSales() * 0.012 + (promoterOrder.getNumberOfOrders() >= 10 ? 10000 : 0));
        }
        promoterOrder.setOrderProfit(personalProfit);
        return promoterOrder;
    }

    @Override
    public Map getMemberAndOrderMan(Integer userId) {
        HashMap<String, Boolean> map = new HashMap<>();
        OpenMemberInfo openMemberInfo = openMemberInfoMapper.getOpenMemberInfoByEndTime(userId).stream().findFirst().orElse(null);
        if (openMemberInfo != null){
            map.put("member",true);
        }else{
            map.put("member",false);
        }
        OpenOrderManInfo openOrderManInfo = openOrderManInfoMapper.getOpenOrderManInfoByEndTime(userId).stream().findFirst().orElse(null);
        if(openOrderManInfo != null){
            map.put("orderMan",true);
        }else{
            map.put("orderMan",false);
        }
        return map;
    }


    //获取下级会员列表
    @Override
    public List<PromoterOrderMan> getMemberData(Integer userId) {
        List<PromoterOrderMan> promoterOrderMan = openOrderManInfoMapper.getMemberData(userId);
        PromoterOrderMan promoterOrderMan1 = new PromoterOrderMan();
        promoterOrderMan1.setOrderManId(userId);
        for (PromoterOrderMan orderMan : promoterOrderMan) {
            promoterOrderMan1.setUserId(orderMan.getUserId());
            PromoterOrderMan promoterOrder = openOrderManInfoMapper.getOrderManData(promoterOrderMan1);
            if(orderMan.getHeadImage() == null) {
                orderMan.setHeadImage("http://www.laykj.cn/wherebuy/images/2019/02/14/15/d40c2c26-20bc-4a4d-a012-e62c7ede7d80.png");
            }else {
                String headImage = ImageUtil.dealToShow(orderMan.getHeadImage());
                orderMan.setHeadImage(headImage);
            }
            orderMan.setNumberOfOrders(promoterOrder.getNumberOfOrders());
            orderMan.setTotalSum(promoterOrder.getTotalSum());
        }
        return promoterOrderMan;
    }


    //根据家庭服务师id和date获取订单数据统计
    @Override
    public PromoterOrderMan getOrderManData(PromoterOrderMan PromoterOrderMan) {
        PromoterOrderMan promoterOrderMan = openOrderManInfoMapper.getOrderManData(PromoterOrderMan);
        OpenOrderManInfo openOrderManInfo = openOrderManInfoMapper.getOpenOrderManInfoByOrderManId(PromoterOrderMan.getOrderManId()).stream().findFirst().orElse(null);
        String openOrderManLevel = openOrderManInfo.getOrderManLevel();
        int personalProfit = 0;
        if ("1".equals(openOrderManLevel)){
            //个人收益(一级) 订单数*2 + 有效销售额*0.8% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrderMan.getNumberOfOrders() * 200 + promoterOrderMan.getPersonalSales() * 0.008 + (promoterOrderMan.getNumberOfOrders() >= 10 ? 10000 : 0));
        }else if ("2".equals(openOrderManLevel)){
            //个人收益(二级) 订单数*2.5 + 有效销售额*1% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrderMan.getNumberOfOrders() * 250 + promoterOrderMan.getPersonalSales() * 0.01 + (promoterOrderMan.getNumberOfOrders() >= 10 ? 10000 : 0));
        }else if ("3".equals(openOrderManLevel)){
            //个人收益(三级) 订单数*3 + 有效销售额*1.2% + 个人订单数大于等于10奖励100
            personalProfit = (int) (promoterOrderMan.getNumberOfOrders() * 300 + promoterOrderMan.getPersonalSales() * 0.012 + (promoterOrderMan.getNumberOfOrders() >= 10 ? 10000 : 0));
        }
        promoterOrderMan.setOrderProfit(personalProfit);
        return promoterOrderMan;
    }


}
