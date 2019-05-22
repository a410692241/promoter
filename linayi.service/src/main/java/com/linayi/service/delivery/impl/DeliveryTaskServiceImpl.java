package com.linayi.service.delivery.impl;


import com.linayi.dao.area.AreaMapper;
import com.linayi.dao.area.SmallCommunityMapper;
import com.linayi.dao.delivery.DeliveryBoxGoodsMapper;
import com.linayi.dao.delivery.DeliveryBoxMapper;
import com.linayi.dao.delivery.DeliveryTaskMapper;
import com.linayi.dao.goods.GoodsSkuMapper;
import com.linayi.dao.order.OrderBoxMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.dao.user.UserMapper;
import com.linayi.entity.area.Area;
import com.linayi.entity.area.SmallCommunity;
import com.linayi.entity.delivery.DeliveryBoxGoods;
import com.linayi.entity.delivery.DeliveryTask;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.order.OrderBox;
import com.linayi.entity.order.Orders;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.entity.user.ShoppingCar;
import com.linayi.entity.user.User;
import com.linayi.enums.DeliveryStatus;
import com.linayi.enums.OrderStatus;
import com.linayi.service.delivery.DeliveryTaskService;
import com.linayi.util.ConstantUtil;
import com.linayi.util.ImageUtil;
import com.linayi.util.OSSManageUtil;
import com.linayi.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeliveryTaskServiceImpl implements DeliveryTaskService {
    @Resource
    private DeliveryTaskMapper deliveryTaskMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private DeliveryBoxMapper deliveryBoxMapper;
    @Resource
    private DeliveryBoxGoodsMapper deliveryBoxGoodsMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private GoodsSkuMapper goodsSkuMapper;
    @Autowired
    private SupermarketMapper supermarketMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ProcurementTaskMapper procurementTaskMapper;
    @Resource
    private OrderBoxMapper orderBoxMapper;
    @Resource
    private SmallCommunityMapper smallCommunityMapper;

    @Override
    public List<Orders> getListDeliveryTask(Orders orders) {
        //获取所有装箱的订单
        orders.setCommunityStatus("PACKED");
        List<Orders> listOrders = ordersMapper.getOrderListDelivery(orders);
        List<Orders> newListOrders = new ArrayList<>();
        //通过订单Id获得订单的商品数量和配送箱信息
        for (int i = 0; i < listOrders.size(); i++) {
            Long ordersId = listOrders.get(i).getOrdersId();
            //获取配送箱信息

            List<OrderBox> OrderBoxList = orderBoxMapper.getOrderBoxList(ordersId);
            for (int k = 0; k < OrderBoxList.size(); k++) {
                orders = new Orders();
                //通过订单Id获得订单的商品数量
                Integer sum = 0;
                ProcurementTask procurementTask = new ProcurementTask();
                procurementTask.setOrdersId(ordersId);
                procurementTask.setBoxNo(OrderBoxList.get(k).getBoxNo());
                List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementList(procurementTask);
                for (int j = 0; j < procurementTaskList.size(); j++) {
                    Integer actualQuantity = procurementTaskList.get(j).getActualQuantity();
                    sum += actualQuantity;
                }
                orders.setBoxTime(OrderBoxList.get(k).getBoxTime());
                orders.setBoxNo(OrderBoxList.get(k).getBoxNo());
                orders.setOrdersId(listOrders.get(i).getOrdersId());
                orders.setReceiverName(listOrders.get(i).getReceiverName());
                orders.setQuantity(sum);
                newListOrders.add(orders);
            }
        }
        return newListOrders;
//		List<DeliveryTask> listDeliveryTask= deliveryTaskMapper.getListDeliveryTask(deliveryTask);
//
//		for(int i=0;i<listDeliveryTask.size();i++){
//			//通过订单id获取顾客名称
//			Long ordersId = listDeliveryTask.get(i).getOrdersId();
//			String customerName = ordersMapper.getOrderById(ordersId).getReceiverName();
//			listDeliveryTask.get(i).setCustomerName(customerName);
//			//通过配送箱ID获取装箱时间和数量以及箱号
//			Long deliveryBoxId = listDeliveryTask.get(i).getDeliveryBoxId();
//			DeliveryBox deliveryBox = deliveryBoxMapper.getDeliveryBox(deliveryBoxId);
//			listDeliveryTask.get(i).setCreateTime(deliveryBox.getCreateTime());
//			listDeliveryTask.get(i).setBoxNo(deliveryBox.getBoxNo());
//			List<DeliveryBoxGoods> listDeliveryBoxGoods = deliveryBoxGoodsMapper.getDeliveryBoxGoods(deliveryBoxId);
//			//配送箱中商品数量总和
//			Integer sum = 0;
//			for(int j=0;j<listDeliveryBoxGoods.size();j++){
//				Integer quantity = listDeliveryBoxGoods.get(j).getQuantity();
//				sum += quantity;
//			}
//			listDeliveryTask.get(i).setQuantity(sum);
//		}
//		return listDeliveryTask;
    }

    @Override
    public Orders toViewDeliveryTask(String boxNo, Long ordersId) {
        Orders newOrders = new Orders();
        //通过订单ID获取顾客信息
        Orders orders = ordersMapper.getOrderById(ordersId);
        String address = getAddress(orders);
        newOrders.setCreateTime(orders.getCreateTime());
        newOrders.setReceiverName(orders.getReceiverName());
        newOrders.setMobile(orders.getMobile());
        newOrders.setAddress(address);
        //通过订单ID获取配送箱信息
//		OrderBox OrderBox =orderBoxMapper.getOrderBox(ordersId);
//		newOrders.setBoxNo(OrderBox.getBoxNo());

        //通过订单id获取商品信息
        List<ProcurementTask> listProcurementTask = procurementTaskMapper.getListByOrdersId(ordersId, boxNo);
        List<GoodsSku> listGoodsSku = new ArrayList<>();
        Integer sum = 0;
        for (int i = 0; i < listProcurementTask.size(); i++) {
            GoodsSku newGoodsSku = new GoodsSku();
            Integer goodsSkuId = listProcurementTask.get(i).getGoodsSkuId();
            Integer actualQuantity = listProcurementTask.get(i).getActualQuantity();
            GoodsSku goodsSku = goodsSkuMapper.getGoodsById(goodsSkuId);
            newGoodsSku.setImage(ImageUtil.dealToShow(goodsSku.getImage()));
            newGoodsSku.setFullName(goodsSku.getFullName());
            newGoodsSku.setQuantity(actualQuantity);
            listGoodsSku.add(newGoodsSku);
            sum += actualQuantity;
        }
        newOrders.setGoodsSkuList(listGoodsSku);
        newOrders.setQuantity(sum);
        newOrders.setOrdersId(ordersId);
        newOrders.setBoxNo(boxNo);
        return newOrders;
    }

    @Override
    @Transactional
    public Integer sealBox(String boxNo, Long ordersId, MultipartFile file) {
        String path = null;
        ordersMapper.updateStatusByOrdersId(ordersId);
        try {
            path = ImageUtil.handleUpload(file);
            OrderBox orderBox = new OrderBox();
            orderBox.setBoxNo(boxNo);
            orderBox.setOrdersId(ordersId);
            orderBox.setImage(path);
            orderBoxMapper.updateImage(orderBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
//		deliveryTaskMapper.updateStatusByDeliveryBoxId(deliveryBoxId);
//		ordersMapper.updateStatusByOrdersId(ordersId);
//		String path = null;
//		try {
//			path = ImageUtil.handleUpload(file);
//			DeliveryBox deliveryBox = new DeliveryBox();
//			deliveryBox.setImage(path);
//			deliveryBox.setDeliveryBoxId(deliveryBoxId);
//			deliveryBoxMapper.updateImageById(deliveryBox );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        return null;
    }

    /**
     * 获取收货地址
     */
    private String getAddress(Orders orders) {
        String areaCode = orders.getAddressOne();
        String addressTwo = orders.getAddressTwo();
        String addressThree = orders.getAddressThree();
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
        String address = areaName + addressTwo + addressThree;
        return address;
    }

    @Override
    public List<Orders> getOrdersBydelivererIdAndStatus(Orders orders) {
        if (orders.getDelivererId() != null) {
            List<Integer> smallCommunityList = new ArrayList<>();
            List<SmallCommunity> delivererList = smallCommunityMapper.getDeliverer(orders.getDelivererId());
            for (SmallCommunity smallCommunity : delivererList) {
                smallCommunityList.add(smallCommunity.getSmallCommunityId());
            }
            orders.setSmallCommunityIdList(smallCommunityList);
        }
        if (DeliveryStatus.DELIVERING.toString().equals(orders.getCommunityStatus())) {
            List<Orders> ordersList = ordersMapper.getOrdersBySmallCommunityIdAndStatus(orders);
            return this.getOrderTotalPriceByOrdersList(ordersList);
        } else {
            List<Orders> ordersList = ordersMapper.getOrdersByUserIdAndFinishStatus(orders);
            return this.getOrderTotalPriceByOrdersList(ordersList);
        }
    }

    //通过订单列表获取订单总价格
    public List<Orders> getOrderTotalPriceByOrdersList(List<Orders> ordersList) {
        for (Orders order : ordersList) {
            List<OrderBox> orderBoxList = orderBoxMapper.getOrderBoxList(order.getOrdersId());
            for (OrderBox orderBox : orderBoxList) {
                ProcurementTask task = new ProcurementTask();
                task.setBoxNo(orderBox.getBoxNo());
                task.setOrdersId(orderBox.getOrdersId());
                List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(task);
                Integer totalPrice = 0;
                for (ProcurementTask procurementTask : procurementTaskList) {
                    totalPrice = procurementTask.getPrice() * procurementTask.getActualQuantity() + totalPrice;
                }
                if (order.getDelivererId() != null) {
                    order.setDelivererName(userMapper.selectUserByuserId(order.getDelivererId()).getRealName());
                }
                order.setAmount(totalPrice + order.getServiceFee());
            }
        }
        return ordersList;
    }

    @Override
    public Integer updateFinishedStatus(Orders orders) {
        Orders orderById = ordersMapper.getOrderById(orders.getOrdersId());
        if (orderById.getDeliveryFinishTime() == null) {
            orders.setDeliveryFinishTime(new Date());
        }
        orders.setCommunityStatus(OrderStatus.DELIVER_FINISHED.toString());
        return ordersMapper.updateOrdersCommunityStatusByOrdersId(orders);
    }

    @Override
    public List<DeliveryTask> selectAll(DeliveryTask deliveryTask) {
        List<DeliveryTask> listDeliveryTask = deliveryTaskMapper.selectAll(deliveryTask);
        for (int i = 0; i < listDeliveryTask.size(); i++) {
            //获取配送的订单金额
            Long deliveryBoxId = listDeliveryTask.get(i).getDeliveryBoxId();
            List<DeliveryBoxGoods> listDeliveryBoxGoods = deliveryBoxGoodsMapper.getDeliveryBoxGoods(deliveryBoxId);
            Integer money = 0;
            for (int j = 0; j < listDeliveryBoxGoods.size(); j++) {
                money += listDeliveryBoxGoods.get(j).getPrice() * listDeliveryBoxGoods.get(j).getQuantity();
            }
            listDeliveryTask.get(i).setAmount(money);
        }
        return listDeliveryTask;
    }

    //通过订单id查询配送订单详情
    @Override
    public Orders getDeliveryTaskDetails(Orders orders) {
        orders = ordersMapper.getOrderById(orders.getOrdersId());
        List<OrderBox> orderBox = orderBoxMapper.getOrderBoxList(orders.getOrdersId());
        for (OrderBox box : orderBox) {
            ProcurementTask task = new ProcurementTask();
            task.setBoxNo(box.getBoxNo());
            task.setOrdersId(box.getOrdersId());
            orders.setBoxTime(box.getBoxTime());
            List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(task);
            List<ShoppingCar> listShoppingCar = new ArrayList<>();
            Integer goodsTotalPrice = 0;
            for (ProcurementTask procurementTask : procurementTaskList) {
                ShoppingCar shoppingCar = new ShoppingCar();
                String name = supermarketMapper.selectSupermarketBysupermarketId(procurementTask.getSupermarketId()).getName();
                shoppingCar.setMinSupermarketName(name);
                GoodsSku goodsSku = goodsSkuMapper.getGoodsById(procurementTask.getGoodsSkuId());
                shoppingCar.setGoodsName(goodsSku.getFullName());
                shoppingCar.setGoodsSkuImage(ImageUtil.dealToShow(goodsSku.getImage()));
                shoppingCar.setMinPrice(procurementTask.getPrice() + "");
                shoppingCar.setQuantity(procurementTask.getActualQuantity());
                shoppingCar.setHeJiPrice(procurementTask.getPrice() * procurementTask.getActualQuantity() + "");
                goodsTotalPrice = Integer.valueOf(shoppingCar.getHeJiPrice()) + goodsTotalPrice;
                listShoppingCar.add(shoppingCar);
            }
            orders.setShoppingCarList(listShoppingCar);
            orders.setGoodsTotalPrice(goodsTotalPrice + "");
            orders.setServiceFee(orders.getServiceFee());
            orders.setTotalPrice(goodsTotalPrice + orders.getServiceFee() + "");
            orders.setPayPrice(goodsTotalPrice + orders.getServiceFee() + "");
        }
        //网点电话
        orders.setServiceMobile(PropertiesUtil.getValueByKey(ConstantUtil.SERVICE_MOBILE));
        //联系收货人
        // TODO: 2019/2/27 0027
        //设置收货地址
        String address = this.getAddress(orders);
        orders.setAddress(address);
        return orders;
    }

    @Override
    public DeliveryTask getDeliveryTaskView(Long ordersId, Long deliveryTaskId) {
        //通过订单ID获取顾客信息
        Orders orders = ordersMapper.getOrderById(ordersId);
        String address = getAddress(orders);
        DeliveryTask deliveryTask = new DeliveryTask();
        deliveryTask = deliveryTaskMapper.getDeliveryTaskById(deliveryTaskId);
        deliveryTask.setAddress(address);
        deliveryTask.setMobile(orders.getMobile());
        deliveryTask.setCustomerName(orders.getReceiverName());

        //通过UserId获取配送员信息
        Integer userId = deliveryTask.getUserId();
        User user = userMapper.selectUserByuserId(userId);
        deliveryTask.setReceiverName(user.getRealName());
        deliveryTask.setDeliveryMobile(user.getMobile());
        //获取配送的订单金额
        Long deliveryBoxId = deliveryTask.getDeliveryBoxId();
        List<DeliveryBoxGoods> listDeliveryBoxGoods = deliveryBoxGoodsMapper.getDeliveryBoxGoods(deliveryBoxId);
        Integer money = 0;
        for (int j = 0; j < listDeliveryBoxGoods.size(); j++) {
            money += listDeliveryBoxGoods.get(j).getPrice() * listDeliveryBoxGoods.get(j).getQuantity();
        }
        deliveryTask.setAmount(money);
        return deliveryTask;
    }
}
