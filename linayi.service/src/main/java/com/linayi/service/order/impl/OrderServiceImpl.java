package com.linayi.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.linayi.dao.address.ReceiveAddressMapper;
import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.community.CommunityMapper;
import com.linayi.dao.goods.AttributeMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.dao.promoter.OpenMemberInfoMapper;
import com.linayi.dao.promoter.OpenOrderManInfoMapper;
import com.linayi.dao.promoter.PromoterOrderManMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.ShoppingCarMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.community.Community;
import com.linayi.entity.goods.*;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.order.OrdersSku;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.entity.promoter.OpenMemberInfo;
import com.linayi.entity.promoter.OpenOrderManInfo;
import com.linayi.entity.promoter.PromoterOrderMan;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.ReceiveAddress;
import com.linayi.entity.user.ShoppingCar;
import com.linayi.entity.user.User;
import com.linayi.enums.MemberLevel;
import com.linayi.enums.OrderStatus;
import com.linayi.exception.ErrorType;
import com.linayi.service.goods.BrandService;
import com.linayi.service.goods.CommunityGoodsService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.order.OrderService;
import com.linayi.service.promoter.OpenMemberInfoService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.util.*;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCarMapper shoppingCarMapper;
    @Autowired
    private ReceiveAddressMapper receiveAddressMapper;
    @Autowired
    SmallCommunityMapper smallCommunityMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrdersGoodsMapper ordersGoodsMapper;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;
    @Autowired
    private BrandService brandService;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;
    @Autowired
    private AttributeMapper attributeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    SupermarketMapper supermarketMapper;
    @Autowired
    private ProcurementTaskMapper procurementTaskMapper;
    @Autowired
    private PromoterOrderManMapper promoterOrderManMapper;
    @Autowired
    private OpenMemberInfoMapper openMemberInfoMapper;
    @Autowired
    private OpenMemberInfoService openMemberInfoService;
    @Autowired
    private OpenOrderManInfoMapper openOrderManInfoMapper;
    @Autowired
    private SupermarketService supermarketService;
    @Autowired
    private CommunityGoodsService communityGoodsService;

    @Transactional
    @Override
    public ResponseData addOrder(Map<String, Object> param){
        Integer userId = (Integer) param.get("userId");
        User user = userMapper.selectUserByuserId(userId);
        Integer receiveAddressId = user.getDefaultReceiveAddressId();
        String payWay = (String) param.get("pay_way");
        String remark = (String) param.get("remark");
        String amount1 = ((String) param.get("amount")).replace(".", "");
        Integer amount = Integer.parseInt(amount1);
        Integer saveAmount = Integer.parseInt(((String) param.get("saveAmount")).replace(".", ""));
        Integer extraFee = Integer.parseInt(((String) param.get("extraFee")).replace(".", ""));
        Integer serviceFee = Integer.parseInt(((String) param.get("serviceFee")).replace(".", ""));
        //判断是否为下单员
        PromoterOrderMan promoterOrderMan = promoterOrderManMapper.getPromoterOrderManByOrderManId(userId);
        OpenMemberInfo openMemberInfo = new OpenMemberInfo();
        String addressType = "MINE";
        if (promoterOrderMan != null){
            //下单员
            ReceiveAddress receiveAddress = new ReceiveAddress();
            receiveAddress.setReceiveAddressId(receiveAddressId);
            ReceiveAddress receiveAddress1 = receiveAddressMapper.selectAddbyacGdAdId(receiveAddress);
            if (receiveAddress1.getAddressType() != null &&"CUSTOMER".equals(receiveAddress1.getAddressType())){
                //给顾客下单
                addressType = "CUSTOMER";
            }
        }
        boolean isVIP = false;
        if("MINE".equals(addressType)){
            openMemberInfo.setUserId(userId);
            openMemberInfo.setEndTime(new Date());
            List<OpenMemberInfo> openMemberInfos = openMemberInfoMapper.getMemberInfo(openMemberInfo);
            if (openMemberInfos != null && openMemberInfos.size() > 0){
                openMemberInfo = openMemberInfos.get(0);
                isVIP = true;
                    //是会员
                    Integer freeTimes = openMemberInfo.getFreeTimes();
                    if (freeTimes != null && freeTimes > 0){
                        freeTimes --;
                        serviceFee = 0;
                        openMemberInfo.setFreeTimes(freeTimes);
                        openMemberInfoMapper.updateById(openMemberInfo);
                }
            }else {
                return new ResponseData(ErrorType.NOT_MEMBER);
            }
        }

        //获取所有的购物车
        List<ShoppingCar> shoppingCars = getShoppingCars(userId, receiveAddressId);
        if (shoppingCars == null){
            shoppingCars = new ArrayList<>();
            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setQuantity(Integer.valueOf(param.get("quantity") + ""));
            shoppingCar.setGoodsSkuId(Integer.valueOf(param.get("goodsSkuId") + ""));
            shoppingCars.add(shoppingCar);
        }

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
        Integer num = 0;
        if (shoppingCars != null && shoppingCars.size() > 0) {
            for (ShoppingCar car : shoppingCars) {
                num += car.getQuantity();
            }
        }
        Orders order = generateOrders(userId, payWay, remark, amount, saveAmount, extraFee, serviceFee, num, receiveAddress, smallCommunity,receiveAddressId,addressType);
        if (isVIP){
            Integer openOrderManInfoId = openMemberInfo.getOpenOrderManInfoId();
            OpenOrderManInfo openOrderManInfo = openOrderManInfoMapper.getOpenOrderManInfoById(openOrderManInfoId);
            order.setPromoterId(openOrderManInfo.getPromoterId());
            order.setOrderManId(openOrderManInfo.getOrderManId());
        }
        // 插入订单
        ordersMapper.insert(order);
        MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(userId);
        //新增订单商品
        for (ShoppingCar car : shoppingCars) {
            List<SupermarketGoods> supermarketGoodsList = supermarketGoodsService.getSupermarketGoodsList(car.getGoodsSkuId(), smallCommunity.getCommunityId());
            MemberPriceUtil.supermarketPriceByLevel(currentMemberLevel,supermarketGoodsList);

            OrdersGoods ordersGoods = generateOrdersGoods(order,MemberPriceUtil.supermarketGoods,MemberPriceUtil.allSpermarketGoodsList, car.getQuantity(), car.getGoodsSkuId());
            ordersGoodsMapper.insert(ordersGoods);
            SupermarketGoods supermarketGoods = MemberPriceUtil.allSpermarketGoodsList.get(MemberPriceUtil.allSpermarketGoodsList.size() - 1);
            Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(supermarketGoods.getSupermarketId());
            supermarket.setPrice(supermarketGoods.getPrice());
            supermarket.setSupermarketId(supermarket.getSupermarketId());
            //待采买任务
            ProcurementTask procurementTask = generateProcurementTask(smallCommunity, ordersGoods, supermarket);
            procurementTaskMapper.insert(procurementTask);
            GoodsSku goodsSku = goodsSkuMapper.getGoodsById(car.getGoodsSkuId());
            goodsSku.setSoldNum(goodsSku.getSoldNum() == null ? 0 : goodsSku.getSoldNum() + car.getQuantity());
            goodsSkuMapper.update(goodsSku);
            if(car.getShoppingCarId() != null){
                shoppingCarMapper.deleteCarById(Integer.parseInt(car.getShoppingCarId() + ""));
            }
        }
        return new ResponseData("success");
    }

    @Override
    public  OrdersGoods generateOrdersGoods(Orders order, List<SupermarketGoods> supermarketGoodsList,List<SupermarketGoods> supermarketGoodsList1, Integer quantity, Integer goodsSkuId) {
        OrdersGoods ordersGoods = new OrdersGoods();
        ordersGoods.setOrdersId(order.getOrdersId());
        String jsonStr = dealSupermarket(supermarketGoodsList1);
        ordersGoods.setSupermarketList(jsonStr);
        ordersGoods.setMaxPrice(supermarketGoodsList.get(0).getPrice());
        ordersGoods.setPrice(supermarketGoodsList.get(supermarketGoodsList.size() - 1).getPrice());
        ordersGoods.setQuantity(quantity);
        ordersGoods.setGoodsSkuId(goodsSkuId);
        ordersGoods.setSupermarketId(supermarketGoodsList.get(supermarketGoodsList.size() - 1).getSupermarketId());
        ordersGoods.setMaxSupermarketId(supermarketGoodsList.get(0).getSupermarketId());
        ordersGoods.setCreateTime(new Date());
        //待采买状态
        ordersGoods.setProcureStatus("PROCURING");
        return ordersGoods;
    }

    public static ProcurementTask generateProcurementTask(SmallCommunity smallCommunity, OrdersGoods ordersGoods, Supermarket supermarket) {
        ProcurementTask procurementTask = new ProcurementTask();
        procurementTask.setOrdersGoodsId(ordersGoods.getOrdersGoodsId());
        procurementTask.setSupermarketId(supermarket.getSupermarketId());
        procurementTask.setOrdersId(ordersGoods.getOrdersId());
        procurementTask.setGoodsSkuId(ordersGoods.getGoodsSkuId());
        procurementTask.setPrice(supermarket.getPrice());
        procurementTask.setQuantity(ordersGoods.getQuantity());
        procurementTask.setActualQuantity(0);
        procurementTask.setProcureQuantity(0);
        procurementTask.setProcureStatus("PROCURING");
        procurementTask.setReceiveStatus("WAIT_OUT");
        procurementTask.setCommunityId(smallCommunity.getCommunityId());
        procurementTask.setUserId(supermarket.getProcurerId());
        procurementTask.setCreateTime(new Date());
        procurementTask.setActualQuantity(0);
        return procurementTask;
    }

    public static Orders generateOrders(Integer userId, String payWay, String remark, Integer amount, Integer saveAmount, Integer extraFee, Integer serviceFee, Integer num, ReceiveAddress receiveAddress, SmallCommunity smallCommunity,Integer receiveAddressId,String addressType) {
        //生成订单
        Orders order = new Orders();
        order.setUserId(userId);
        order.setAddressOne(smallCommunity.getAreaCode());
        order.setAddressTwo(smallCommunity.getName());
        //新增小区ID
        order.setSex(receiveAddress.getSex());
        order.setSmallCommunityId(smallCommunity.getSmallCommunityId());
        order.setAddressThree(receiveAddress.getAddressTwo());
        order.setReceiverName(receiveAddress.getReceiverName());
        order.setMobile(receiveAddress.getMobile());
        order.setPayWay(payWay);
        order.setRemark(remark);
        order.setAmount(amount);
        order.setSaveAmount(saveAmount);
        order.setReceiveAddressId(receiveAddressId);
        order.setExtraFee(extraFee);
        order.setAddressType(addressType);
        //会员免费次数抵服务费
        order.setServiceFee(serviceFee);
        order.setUserStatus("IN_PROGRESS");
        order.setCommunityStatus("PROCURING");
        order.setCommunityId(smallCommunity.getCommunityId());
        order.setCreateTime(new Date());

        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.add(Calendar.HOUR_OF_DAY, 12);
        //预计送达时间
        Date deliveryTime = cl.getTime();
        order.setArriveTime(deliveryTime);
        //实际送达时间
//        order.setActualArriveTime(deliveryTime);
        //订单商品数量
        order.setQuantity(num);
        return order;
    }


    //获取所有的购物车
    public List<ShoppingCar> getShoppingCars(Integer userId, Integer receiveAddressId) {
        ShoppingCar shoppingCar = new ShoppingCar();
        shoppingCar.setSelectStatus("selected");
        shoppingCar.setUserId(userId);
        shoppingCar.setReceiveAddressId(receiveAddressId);
        List<ShoppingCar> shoppingCars = shoppingCarMapper.getAllCarByReceiveAddressId(shoppingCar);
        //没有选择购物车
        if (shoppingCars == null || shoppingCars.size() < 1) {
            return null;
        }
        return shoppingCars;
    }

    private static String dealSupermarket(List<SupermarketGoods> supermarketGoodsList) {
        if (supermarketGoodsList != null && supermarketGoodsList.size() > 0) {
            List<Map> list = new ArrayList<>();
            for (int i = supermarketGoodsList.size() - 1 ; i >= 0; i--) {
                Map<String, Object> map = new HashMap<>();
                map.put("supermarket_id", supermarketGoodsList.get(i).getSupermarketId());
                map.put("price", supermarketGoodsList.get(i).getPrice());
                list.add(map);
            }
            String json = JSON.toJSONString(list);
            return json;
        }
        return null;
    }


    @Override
    public List<ProcurementTask> selectProcuringByOrdersId(ProcurementTask procurementTask){
        List<ProcurementTask> procurementTask1 = procurementTaskMapper.selectProcuringByOrdersId(procurementTask);
        for (ProcurementTask p : procurementTask1){
            if(p.getProcureStatus()=="LACK"||p.getProcureStatus()=="PRICE_HIGH"){
                p.setProcureStatus("PROCURING");
            }
            Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(p.getSupermarketId());
            p.setSupermarketName(supermarket.getName());
            p.setImage(goodsSkuMapper.selectNamebyId(p.getGoodsSkuId()).getImage());
            p.setGoodsSkuName(goodsSkuMapper.selectNamebyId(p.getGoodsSkuId()).getName());
        }
        return procurementTask1;
    }

    @Override
    public Object getOrderList(Orders orders) {
        String communityStatus = orders.getCommunityStatus();
        if (communityStatus != null) {
            orders.setUserId(null);
            if ("ALL".equals(communityStatus)){
                orders.setCommunityStatus(null);
            }

        }

        List<Orders> ordersList = ordersMapper.getOrderList(orders);
        List<Orders> orders3 = getOrdersList(ordersList, "orderList");
        PageResult<Orders> ordersPageResult = new PageResult<>(orders3,orders);
        return ordersPageResult;
    }

    @Override
    public PageResult<Orders> getOrdersList(Orders orders) {
        String range = orders.getRange();
        if ("MONTH".equals(range)){
            Calendar cl = Calendar.getInstance();
            cl.setTime(new Date());
            cl.set(Calendar.DAY_OF_MONTH,1);
            cl.set(Calendar.HOUR_OF_DAY,0);
            cl.set(Calendar.MINUTE,0);
            cl.set(Calendar.SECOND,0);
            orders.setCreateTimeStart(DateUtil.date2String(cl.getTime(),"yyyy-MM-dd HH:mm:ss"));
            cl.add(Calendar.MONTH,1);
            orders.setCreateTimeEnd(DateUtil.date2String(cl.getTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        List<Orders> ordersList = ordersMapper.getALLOrderByOrderMan(orders);
        List<Orders> orders3 = getOrdersList(ordersList, "orderList");
        PageResult<Orders> ordersPageResult = new PageResult<>(orders3,orders);
        return ordersPageResult;
    }


    @Override
    public PageResult<Orders> getProcureOrderList(Orders orders) {
        String communityName = orders.getCommunityName();
        String communityStatus = orders.getCommunityStatus();
        Integer userId = orders.getUserId();
        if (communityStatus != null) {
            orders.setUserId(null);
            if ("ALL".equals(communityStatus)){
                orders.setCommunityStatus(null);
            }

        }
        List<Orders> ordersList;
        if(communityName != null && "community".equals(communityName)){
            orders.setCommunityId(userId);
            ordersList = ordersMapper.getProcureOrderList(orders);
            if(ordersList != null && ordersList.size() > 0){
                for (Orders orders1 : ordersList) {
                    Integer serviceFee = orders1.getServiceFee();
                    if(serviceFee != null && serviceFee >0){
                        orders1.setAmount(orders1.getAmount() - serviceFee);
                    }
                }
            }
        }else {
            ordersList = ordersMapper.getOrderList(orders);
        }
        PageResult<Orders> ordersPageResult = new PageResult<>(ordersList,orders);
        return ordersPageResult;
    }

    public List<Orders> getOrdersList(Collection<Orders> ordersList, String type) {

        List<Orders> orders3 = new ArrayList<>();
        for (Orders orders1 : ordersList) {
            List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersId(orders1.getOrdersId());

            Orders orders2 = new Orders();
            orders2.setQuantity(orders1.getQuantity());
            // 商品总价
            Integer goodsTotalPrice = 0;
            // 商品实际总价
            Integer goodsPayPrice = 0;
            // 总价
            Integer totalPrice = 0;
            // 实付价格
            Integer payPrice = 0;
            //附加价格
            Integer addPrice = 0;

            //服务费
            Integer serviceFee = orders1.getServiceFee();
            // 附加费用
            Integer extraFee = orders1.getExtraFee();

            if (type.equals("orderList")) {
                addPrice += serviceFee + extraFee;
                orders2.setReceiverName(orders1.getReceiverName());
            }
            if (type.equals("orderDetails")) {
                orders2.setSex(orders1.getSex());
                //服务费
                orders2.setServiceFeeString(getpriceString(serviceFee));
                // 附加费用
                orders2.setExtraFeeString(getpriceString(extraFee));

                addPrice = serviceFee + extraFee;
                //备注
                orders2.setRemark(orders1.getRemark());
                // 送货时间
                orders2.setDeliveryTime(orders1.getDeliveryTime());
                //配送完成时间
//                orders2.setDeliveryFinishTime(orders1.getDeliveryFinishTime());
                //收货时间
                orders2.setReceiptTime(DateUtil.date2String(orders1.getActualArriveTime() == null ? orders1.getDeliveryFinishTime() : orders1.getActualArriveTime(), DateUtil.Y_M_D_H_M_PATTERN));
            }

            //网点名字和联系方式
            Integer communityId = orders1.getCommunityId();
            Community community = new Community();
            community.setCommunityId(communityId);
            Community community1 = communityMapper.getCommunity(community);
            orders2.setCommunityId(communityId);
            orders2.setCommunityPhone(community1.getMobile());
            orders2.setCommunityName(community1.getName());
            MemberLevel currentMemberLevel = openMemberInfoService.getCurrentMemberLevel(orders1.getUserId());
            orders2.setCreateDateStr(DateUtil.date2String(orders1.getCreateTime(), DateUtil.Y_M_D_H_M_PATTERN));
            List<ShoppingCar> cars = new ArrayList<>();
            try {
                for (OrdersGoods ordersGoods : ordersGoodsList) {
                    ProcurementTask procurement = new ProcurementTask();
                    procurement.setOrdersGoodsId(ordersGoods.getOrdersGoodsId());
                    List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(procurement);
                    if("community".equals(type)){
                        if(procurementTaskList.get(0).getProcureQuantity() == null || procurementTaskList.get(0).getProcureQuantity() < 1){
                            continue;
                        }
                    }
                    ShoppingCar shoppingCar = new ShoppingCar();
                    GoodsSku goodsSku = goodsSkuMapper.getGoodsById(ordersGoods.getGoodsSkuId());
                    String goodsName = getGoodsName(goodsSku);
                    if("NO_NORMAL".equals(goodsName)){
                        goodsName = goodsSku.getFullName();
                    }
                    shoppingCar.setGoodsName(goodsName);
                    shoppingCar.setGoodsSkuId(Integer.parseInt(goodsSku.getGoodsSkuId() + ""));
                    shoppingCar.setGoodsSkuImage(ImageUtil.dealToShow(goodsSku.getImage()));
                    if("PROCURING".equals(ordersGoods.getStatus())){
                        shoppingCar.setStatus(ordersGoods.getStatus());
                    }else {
                        shoppingCar.setStatus(procurementTaskList.get(0).getProcureStatus());
                    }
                    Integer minPrice;
                    Integer maxPrice;
                    String minPriceSupermarketName;
                    String maxPriceSupermarketName;
                    if (ordersGoods.getMaxPrice() == null || ordersGoods.getMaxSupermarketId() == null){
                        String supermarketList = ordersGoods.getSupermarketList();
                        List<Map> list = JSON.parseArray(supermarketList, Map.class);
                        List<SupermarketGoods> supermarketGoods = new ArrayList<>();
                        for (Map map : list) {
                            SupermarketGoods supermarketGoods1 = new SupermarketGoods();
                            supermarketGoods1.setPrice(Integer.parseInt(map.get("price") + ""));
                            supermarketGoods1.setSupermarketId(Integer.parseInt(map.get("supermarket_id") + ""));
                            supermarketGoods1.setRank(1);
                            supermarketGoods.add(supermarketGoods1);
                        }

                        Integer[] idAndPriceByLevel = MemberPriceUtil.supermarketPriceByLevel(currentMemberLevel, supermarketGoods);
                        minPrice = idAndPriceByLevel[0];
                        maxPrice = idAndPriceByLevel[2];

                        minPriceSupermarketName = supermarketService.getSupermarketById(idAndPriceByLevel[1]).getName();
                        maxPriceSupermarketName = supermarketService.getSupermarketById(idAndPriceByLevel[3]).getName();

                    }else {
                        minPrice = ordersGoods.getPrice();
                        maxPrice = ordersGoods.getMaxPrice();

                        minPriceSupermarketName = supermarketService.getSupermarketById(ordersGoods.getSupermarketId()).getName();
                        maxPriceSupermarketName = supermarketService.getSupermarketById(ordersGoods.getMaxSupermarketId()).getName();
                    }
                    //社区端订单详情查看
                    if("community".equals(type)){
                        Integer sum = procurementTaskList.stream().mapToInt(ProcurementTask::getActualQuantity).sum();
                        shoppingCar.setQuantity(sum);
                    }else {
                        shoppingCar.setQuantity(ordersGoods.getQuantity());
                    }
                    shoppingCar.setMinPrice(getpriceString(minPrice));
                    shoppingCar.setMaxPrice(getpriceString(maxPrice));
                    shoppingCar.setMaxSupermarketName(maxPriceSupermarketName);
                    shoppingCar.setMinSupermarketName(minPriceSupermarketName);
                    shoppingCar.setSpreadRate(NumberUtil.formatDouble((maxPrice - minPrice) * 100 / Double.parseDouble("" + minPrice)) + "%");
                    shoppingCar.setHeJiPrice(getpriceString(shoppingCar.getQuantity() * minPrice));
                    cars.add(shoppingCar);

                    goodsPayPrice += ordersGoods.getQuantity() * minPrice;
                    goodsTotalPrice += ordersGoods.getQuantity() * minPrice;

                }
                payPrice += goodsPayPrice + addPrice;
                totalPrice += goodsTotalPrice + addPrice;
                orders2.setOrdersId(orders1.getOrdersId());
                orders2.setCreateDate(DateUtil.date2String(orders1.getCreateTime(), "yyyy/MM/dd"));
                orders2.setGoodsTotalPrice(getpriceString(goodsTotalPrice));
                orders2.setPayPrice(getpriceString(payPrice));
                orders2.setTotalPrice(getpriceString(totalPrice));
                orders2.setShoppingCarList(cars);
                String userStatus = orders1.getUserStatus();
                orders2.setUserStatus(orders1.getUserStatus());
                String communityStatus = orders1.getCommunityStatus();
                orders2.setCommunityStatus(communityStatus);

                //已取消：CANCELED
                if("CANCELED".equals(userStatus)){
                    orders2.setStatus("CANCELED");
                }else if("FINISHED".equals(userStatus)){
                    orders2.setStatus("FINISHED");
                }else {
                    if("RECEIVED".equals(communityStatus) || "PACKED".equals(communityStatus)){
                        communityStatus = "DELIVERING";
                    }
                    orders2.setStatus(communityStatus);
                }
                orders3.add(orders2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return orders3;
    }

    @Override
    public Orders getOrderDetails(Orders orders, HttpServletRequest request) {
        String communityName = orders.getCommunityName();
        orders.setCommunityName(null);
        List<Orders> orderList = ordersMapper.getOrderList(orders);
        List<Orders> ordersList;
        if (orderList != null && orderList.size() > 0) {
            if(communityName != null && "community".equals(communityName)){
                ordersList = getOrdersList(orderList, "community");
            }else {
                ordersList = getOrdersList(orderList, "orderDetails");
            }

            Orders orders1 = orderList.get(0);
            Orders orders2 = ordersList.get(0);

            orders2.setMobile(orders1.getMobile());
            orders2.setReceiverName(orders1.getReceiverName());
            //设置支付编号
            orders2.setPayNumber("123456789");
            //客服电话
            orders2.setServiceMobile(PropertiesUtil.getValueByKey(ConstantUtil.SERVICE_MOBILE));
            //设置收货地址
            String areaCode = orders1.getAddressOne();
            String addressTwo = orders1.getAddressTwo();
            String addressThree = orders1.getAddressThree();
            Area area = new Area();
            String areaName = "";
            while (true) {
                area.setCode(areaCode);
                Area areaInfo = areaMapper.getAreaInfo(area);
                areaName = areaInfo.getName() + areaName;
                if (areaInfo.getParent().equals("1000")) {
                    break;
                }
                areaCode = areaInfo.getParent();
            }
            orders2.setAddress(areaName + addressTwo + addressThree);
//            orders2.setStatus(orders1.getCommunityStatus());
            return orders2;
        }
        return null;
    }

    //商品名字处理
    public String getGoodsName(GoodsSku goodsSku) {
        Brand brand = brandService.getBrandById(goodsSku.getBrandId());

        List<Attribute> attributesList = attributeMapper.getAttributesList(goodsSku.getGoodsSkuId());
        StringBuffer goodsName = new StringBuffer();
        if (brand != null) {
            goodsName.append(brand.getName());
        }
        Map<String, String> attributeMap = new HashMap<>();
        if (attributesList != null && attributesList.size() > 0) {
            for (Attribute attribute : attributesList) {
                attributeMap.put(attribute.getName(), attribute.getAttributeValue());
            }
            String taste = attributeMap.get(AttributeOrder.attrOrdes.get(0));
            if (taste != null && !"".equals(taste)) {
                goodsName.append(" ").append(taste);
                attributeMap.remove(AttributeOrder.attrOrdes.get(0));
            }
            goodsName.append(" ").append(goodsSku.getName());
            for (String attrOrde : AttributeOrder.attrOrdes) {
                if (!attrOrde.equals(AttributeOrder.attrOrdes.get(0)) && attributeMap.containsKey(attrOrde)) {
                    goodsName.append(" ").append(attributeMap.get(attrOrde));
                    attributeMap.remove(attrOrde);
                }
            }
            if (attributeMap != null && attributeMap.size() > 0) {
                Set<String> strings = attributeMap.keySet();
                for (String attrName : strings) {
                    goodsName.append(" ").append(attributeMap.get(attrName));
                }
            }
        }else{
            return "NO_NORMAL";
        }
        return goodsName.toString();
    }

    @Override
    public void updateOrderStatus(Orders orders) {
        ordersMapper.updateOrderById(orders);
    }

    @Override
    public List<Orders> getAfterSaleOrders(Integer userId) {
        List<Orders> ordersList = ordersMapper.getAfterOrderList(userId, "REFUSE_RECEIVE", "REFUNDED");
        List<Orders> orderList = getOrdersList(ordersList, "orderList");
        return orderList;
    }

    @Transactional
    @Override
    public String againOrders(Orders orders) {
        List<OrdersGoods> ordersGoods = ordersGoodsMapper.getOrdersGoodsByOrdersId(orders.getOrdersId());
        User user = userMapper.selectUserByuserId(orders.getUserId());
        Integer receiveAddressId = user.getDefaultReceiveAddressId();

        ReceiveAddress receiveAddress = receiveAddressMapper.getReceiveAddressByReceiveAddressId(receiveAddressId);
        Integer smallComunityId = receiveAddress.getAddressOne();
        SmallCommunity smallCommunity = new SmallCommunity();
        smallCommunity.setSmallCommunityId(smallComunityId);
        smallCommunity = smallCommunityMapper.getSmallCommunity(smallCommunity);
        Integer communityId = smallCommunity.getCommunityId();
        if (ordersGoods != null && ordersGoods.size() > 0) {
            for (OrdersGoods ordersGood : ordersGoods) {
                CommunityGoods communityGoods = new CommunityGoods();
                communityGoods.setCommunityId(communityId);
                communityGoods.setGoodsSkuId(ordersGood.getGoodsSkuId());
                communityGoods = communityGoodsService.getCommunityGoods(communityGoods);

                if(communityGoods == null){
                    return "no_price";
                }

                ShoppingCar shoppingCar = new ShoppingCar();
                shoppingCar.setUserId(orders.getUserId());
                shoppingCar.setGoodsSkuId(ordersGood.getGoodsSkuId());
                shoppingCar.setReceiveAddressId(receiveAddressId);
                ShoppingCar shopCar = shoppingCarMapper.getShopCar(shoppingCar);
                if(shopCar == null){
                    shoppingCar.setGoodsSkuId(ordersGood.getGoodsSkuId());
                    shoppingCar.setQuantity(ordersGood.getQuantity());
                    shoppingCar.setSelectStatus("SELECTED");
                    shoppingCarMapper.insert(shoppingCar);
                }else {
                    shopCar.setQuantity(shopCar.getQuantity() + ordersGood.getQuantity());
                    shoppingCarMapper.updateShopCar(shopCar);
                }

            }
        }
        return "success";
    }

    @Override
    public List<Orders> getOrderAll(Orders orders) {
        if ("CANCELED".equals(orders.getCommunityStatus())){
            orders.setUserStatus("CANCELED");
            orders.setCommunityStatus(null);
        }
        List<Orders> orderList = ordersMapper.getALLOrder(orders);
        for (Orders o : orderList) {
            Community community = new Community();
            community.setCommunityId(o.getCommunityId());
            community = communityMapper.getCommunity(community);
            o.setCommunityName(community.getName());
            o.setAddressOne(getAreaNameByAreaCode(o.getAddressOne()));
            List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersId(o.getOrdersId());
            if (ordersGoodsList != null && ordersGoodsList.size() > 0){
                Integer total = 0;
                for (OrdersGoods ordersGoods : ordersGoodsList) {
                    ProcurementTask procurementTask = new ProcurementTask();
                    procurementTask.setOrdersGoodsId(ordersGoods.getOrdersGoodsId());
                    List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskListAsc(procurementTask);
                    for (int i = 0; i < procurementTaskList.size(); i++) {
                        if(!"PROCURING".equals(procurementTaskList.get(i).getProcureStatus())){
                            int qunatity = procurementTaskList.get(i).getActualQuantity();
//                            if(i == procurementTaskList.size() - 1){
//                                qunatity = procurementTaskList.get(i).getQuantity();
//                            }
                            total += qunatity * procurementTaskList.get(i).getPrice();
                        }
                    }

                }
                o.setOrderGoodsTotalPrice(total + o.getServiceFee() + o.getExtraFee());
            }
//            OrdersSku ordersSku = ordersMapper.selectSkuIdByordersId(o.getOrdersId());
//            o.setGoodsSkuId(ordersSku.getGoodsSkuId());
        }
        return orderList;
    }
 
    /**
     * 通过areaCode获取完整的省市区街道名
     *
     * @param areaCode
     * @return
     */
    @Override
    public String getAreaNameByAreaCode(String areaCode) {
        //获取街道名
        String streetName = areaMapper.getNameByCode(areaCode);
        //获取省市区区名
        String regionName = areaMapper.getNameByCode(areaCode.substring(0, 8));
        String cityName = areaMapper.getNameByCode(areaCode.substring(0, 6));
        String provinceName = areaMapper.getNameByCode(areaCode.substring(0, 4));
        if (areaCode.length() > 8) {
            //拼接
            String areaName = provinceName + cityName + regionName + streetName;
            return areaName;
        } else {
            //拼接
            String areaName = provinceName + cityName + regionName;
            return areaName;
        }
    }

    /**
     * 分转元为单位
     *
     * @param Price
     * @return
     */
    public String getpriceString(Integer Price) {
        StringBuffer sb = new StringBuffer(Price + "");
        if (sb.length() >= 3) {
            sb.insert(sb.toString().length() - 2, ".");
        } else if (sb.length() == 2) {
            return "0." + sb.toString();
        } else if (sb.length() == 1) {
            return "0.0" + sb.toString();
        }
        return sb.toString();
    }

    @Override
    public List<OrdersSku> getGoodsOrderSku(OrdersSku ordersSku) {
        ordersSku.setCurrentPage(null);
        ordersSku.setPageSize(null);
        List<OrdersSku> ordersSkuList = ordersMapper.getGoodsOrderSku(ordersSku);
        for (OrdersSku o : ordersSkuList) {
            o.setImage(ImageUtil.dealToShow(o.getImage()));
            ProcurementTask procurementTask = new ProcurementTask();
            procurementTask.setOrdersGoodsId(o.getOrdersGoodsId());
            List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskListAsc(procurementTask);
            Integer sum = procurementTaskList.stream().mapToInt(ProcurementTask::getActualQuantity).sum();
            o.setProcurementTaskId(procurementTaskList.get(0).getProcurementTaskId());
            o.setProcureQuantity(sum);
            String procureStatus = o.getProcureStatus();
            if ("BOUGHT".equals(procureStatus)){
                o.setProcureStatus("FINISHED");
            }
        }
        return ordersSkuList;
    }

    @Override
    public List<OrdersSku> getAllGoodsOrderSku(OrdersSku ordersSku) {
        return ordersMapper.getAllGoodsOrderSku(ordersSku);
    }

    @Override
    public List<ProcurementTask> selectAllProcurementTask(ProcurementTask procurementTask){
        List<ProcurementTask> procurementTaskList = procurementTaskMapper.selectAllProcurementTask(procurementTask);
        for (ProcurementTask ptl : procurementTaskList){
            Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(ptl.getSupermarketId());
            ptl.setSupermarketName(supermarket.getName());
            GoodsSku goodsSku = goodsSkuMapper.selectNamebyId(ptl.getGoodsSkuId());
            ptl.setGoodsSkuName(goodsSku.getName());
        }
        return procurementTaskList;
    }

    @Override
    @Transactional
    public Integer updateOrderById(Orders orders) {
//        Orders order = ordersMapper.getOrderById(orders.getOrdersId());
//        if (order.getCommunityStatus() != null && order.getCommunityStatus().equals("CANCELED")){
//            return 0;
//        }
//
//        if ("CANCELED".equals(orders.getCommunityStatus())){
//            orders.setUserStatus("CANCELED");
//        }
        OrdersGoods ordersGoods = new OrdersGoods();
        ordersGoods.setOrdersId(orders.getOrdersId());
        ordersGoods.setProcureStatus("PROCURING");
        ordersGoodsMapper.updateOrdersGoodsCanceled(ordersGoods);

        ProcurementTask procurementTask = new ProcurementTask();
        procurementTask.setOrdersId(orders.getOrdersId());
        procurementTask.setProcureStatus("PROCURING");
        procurementTaskMapper.updateProcurementTaskCanceled(procurementTask);

        return ordersMapper.updateOrderById(orders);
    }

    @Override
    public Object getBuyOrderList(OrdersGoods orderGoods) {

        List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersGoods(orderGoods);
        PageResult<OrdersGoods> pageResult = new PageResult<>(ordersGoodsList,orderGoods);
        if (ordersGoodsList != null && ordersGoodsList.size() > 0) {
            for (OrdersGoods ordersGoods : ordersGoodsList) {
                int totalPrice = ordersGoods.getPrice() * ordersGoods.getQuantity();
                String getpriceString = getpriceString(totalPrice);
                ordersGoods.setTotalPrice(getpriceString);
                //设置截止时间
                //ordersGoods.setEndTime();
            }
        }
        return pageResult;
    }

    @Override
    public OrdersGoods getOrderGoodsDetailsById(Integer userId, Map<String, Object> param) {
        Long ordersGoodsId = Long.parseLong(param.get("ordersGoodsId") + "");
        OrdersGoods ordersGoods = ordersGoodsMapper.getOrdersGoodsById(ordersGoodsId);
        Orders orders = ordersMapper.getOrderById(ordersGoods.getOrdersId());
        //网点名字和联系方式
        Integer communityId = orders.getCommunityId();
        Community community = new Community();
        community.setCommunityId(communityId);
        Community community1 = communityMapper.getCommunity(community);

        Integer goodsSkuId = ordersGoods.getGoodsSkuId();
        GoodsSku goodsSku = goodsSkuMapper.getGoodsById(goodsSkuId);
        ordersGoods.setImage(goodsSku.getImage());
        ordersGoods.setGoodsSkuName(goodsSku.getFullName());
        int totalPrice = ordersGoods.getPrice() * ordersGoods.getQuantity();
        String getpriceString = getpriceString(totalPrice);
        ordersGoods.setTotalPrice(getpriceString);
        ordersGoods.setCommunityName(community1.getName());
        return ordersGoods;
    }


    @Override
    public OrdersSku getOrderSupermarketList(OrdersSku ordersSku) {
        return ordersMapper.getOrderSupermarketList(ordersSku);
    }

    @Override
    public List<SupermarketGoods> showAllPurchase(OrdersSku ordersSku) {
        List<SupermarketGoods> sgList = new ArrayList<>();
        if (ordersSku.getSupermarketList() == null || ordersSku.getSupermarketList().equals("")) {
            return null;
        } else {
            String sL = ordersSku.getSupermarketList();
            String replace = sL.replace("_i", "I");
            List<Map> list = JSON.parseArray(replace, Map.class);
            list.stream().forEach(item -> {
                ParamValidUtil<SupermarketGoods> pvu = new ParamValidUtil<>(item);
                SupermarketGoods goods = pvu.transObj(SupermarketGoods.class);
                Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(goods.getSupermarketId());
                goods.setSupermarketName(supermarket.getName());
                if (ordersSku.getGoodsSkuId() != null) {
                    goods.setGoodsSkuId(Long.parseLong(ordersSku.getGoodsSkuId() + ""));
                }

                sgList.add(goods);
            });


        }
        return sgList;
    }

    @Transactional
    @Override
    public void buySecondHeigh(ProcurementTask procurementTask) {
        Integer supermarketId = procurementTask.getSupermarketId();
        OrdersGoods ordersGoods = new OrdersGoods();
        procurementTask = procurementTaskMapper.getProcurementById(procurementTask.getProcurementTaskId());
        ordersGoods.setOrdersId(Long.parseLong(procurementTask.getOrdersId() + ""));
        ordersGoods.setGoodsSkuId(procurementTask.getGoodsSkuId());
        List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.query(ordersGoods);
        List<Map> list = JSON.parseArray(ordersGoodsList.get(0).getSupermarketList(), Map.class);


        Map s = list.stream().filter(item -> item.get("supermarket_id") == supermarketId).collect(Collectors.toList()).stream().findFirst().orElse(null);
        procurementTask.setQuantity(procurementTask.getQuantity() - procurementTask.getProcureQuantity());
        procurementTask.setProcurementTaskId(null);
        procurementTask.setProcureStatus("PROCURING");
        procurementTask.setPrice(Integer.parseInt(s.get("price") + ""));
        procurementTask.setActualQuantity(0);
        procurementTask.setProcureQuantity(0);
        procurementTask.setReceiveStatus("WAIT_OUT");
        procurementTask.setUpdateTime(null);
        procurementTask.setSupermarketId(supermarketId);
        procurementTaskMapper.insert(procurementTask);

    }


    @Override
    public OrdersGoods getOrderGoods(OrdersSku ordersSku) {
        OrdersGoods ordersGoods = new OrdersGoods();
        ordersGoods.setGoodsSkuId(ordersSku.getGoodsSkuId());
        ordersGoods.setOrdersId(Long.parseLong(ordersSku.getOrdersId() + ""));
        List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.query(ordersGoods);
        return ordersGoodsList.get(0);
    }

    /**
     * 根据订单商品id查询采买任务
     * @param procurementTask
     * @return
     */
    @Override
    public List<ProcurementTask> selectSIdPriceByOgId(ProcurementTask procurementTask){
        List<ProcurementTask> ptList = procurementTaskMapper.selectSIdPriceByOgId(procurementTask);
        return ptList;
    }

    @Override
    public List<ProcurementTask> procurementTaskList(OrdersSku ordersSku){
        Long ordersGoodsId = ordersSku.getOrdersGoodsId();
        ProcurementTask pt = new ProcurementTask();
        pt.setOrdersGoodsId(ordersGoodsId);
        List<ProcurementTask> procurementTasksList = selectSIdPriceByOgId(pt);
        for(ProcurementTask pList : procurementTasksList){
            Supermarket supermarket = supermarketMapper.selectSupermarketBysupermarketId(pList.getSupermarketId());
            pList.setSupermarketName(supermarket.getName());
        }
        return procurementTasksList;
    }

    @Override
    public List<Orders> getBoxingList(Orders order) {
        order.setCommunityStatus(OrderStatus.RECEIVED.toString());
        List<Orders> orderList = ordersMapper.getOrderListByCommunityIdAndRECEIVED(order);
        return orderList;
    }

	@Override
	public void updateOrderReceivedStatus(Long ordersId) {
		//通过订单ordersId查询订单信息
		Orders order =ordersMapper.getOrderById(ordersId);
		int cou = 0;
		if("PROCURE_FINISHED".equals(order.getCommunityStatus())){
		//根据orderId获取数量大于0的采买任务状态集合
		List<String> seceiveStatusList = procurementTaskMapper.getReceiveStatusByOrderId(ordersId);

		if(seceiveStatusList.size()>0) {

            for (String s : seceiveStatusList) {
                if (s.contains("RECEIVED")){
                    cou++;
                }
            }

			if(seceiveStatusList.size()==cou){ //全部为已收货

				Orders orders = new Orders();
				orders.setCommunityStatus("RECEIVED");
				orders.setOrdersId(ordersId);
				//修改订单社区状态为全部已收货
				 ordersMapper.updateOrderById(orders);
				}
			}
		}

	}
}
