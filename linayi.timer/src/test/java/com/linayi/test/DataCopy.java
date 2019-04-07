package com.linayi.test;

import com.alibaba.fastjson.JSON;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.sync.dao.TOrdersGoodsMapper;
import com.linayi.sync.dao.TOrdersMapper;
import com.linayi.sync.dao.TSupermarketMapper;
import com.linayi.sync.entity.TOrders;
import com.linayi.sync.entity.TOrdersGoods;
import com.linayi.sync.entity.TSupermarket;
import com.linayi.sync.factory.MySqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class DataCopy {

    @Autowired
    private OrdersGoodsMapper ordersGoodsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ProcurementTaskMapper procurementTaskMapper;

    @Test
    public void copyDate(){
        Map<Long,TOrders> ordersMap = new HashMap<>();
        SqlSession session = MySqlSessionFactory.getSqlSession();
        TOrdersGoodsMapper tOrdersGoodsMapper = session.getMapper(TOrdersGoodsMapper.class);
        TSupermarketMapper tSupermarketMapper = session.getMapper(TSupermarketMapper.class);
        TOrdersMapper tOrdersMapper = session.getMapper(TOrdersMapper.class);
        TOrdersGoods tordersGoods = new TOrdersGoods();

        List<TOrdersGoods> tOrdersGoodsList = tOrdersGoodsMapper.query(tordersGoods);

        for (TOrdersGoods tOrdersGoods : tOrdersGoodsList) {
            if(tOrdersGoods.getOrdersId() == 138){
                System.out.println("..........................");
            }
            TOrders tOrders;
            if (ordersMap.containsKey(tOrdersGoods.getOrdersId())){
                tOrders =  ordersMap.get(tOrdersGoods.getOrdersId());
            }else {
                tOrders = tOrdersMapper.getOrderById(tOrdersGoods.getOrdersId());
                ordersMap.put(tOrders.getOrdersId(),tOrders);
                String userStatus = tOrders.getUserStatus();
                String communityStatus = tOrders.getCommunityStatus();
                //保存订单
                Orders orders = new Orders();
                orders.setOrdersId(tOrders.getOrdersId());
                orders.setUserId(tOrders.getUserId());
                orders.setReceiverName(tOrders.getReceiverName());
                orders.setMobile(tOrders.getMobile());
                orders.setAddressOne(tOrders.getAddressOne());
                orders.setAddressTwo(tOrders.getAddressTwo());
                orders.setAddressThree(tOrders.getAddressThree());
                orders.setPayWay(tOrders.getPayWay());
                orders.setSaveAmount(tOrders.getSaveAmount());
                orders.setServiceFee(tOrders.getServiceFee());
                orders.setExtraFee(tOrders.getExtraFee());
                orders.setActualArriveTime(tOrders.getArriveTime());
                orders.setActualArriveTime(tOrders.getActualArriveTime());
                orders.setRemark(tOrders.getRemark());
                orders.setQuantity(tOrders.getQuantity());
                orders.setAmount(tOrders.getAmount());
//user_status          varchar(20) comment '[用户端订单状态] 进行中：IN_PROGRESS  已取消：CANCELED  已完成：FINISHED   已拒收：REFUSED
// 已退款：REFUNDED',
//community_status     varchar(20) comment '[社区端订单状态] 采买中：PROCURING   采买完成：PROCURE_FINISHED   全部已收货（社区端）：RECEIVED
// 已装箱：PACKED  配送中：DELIVERING   配送完成：DELIVER_FINISHED',

//  user_status   [订单状态] 待收货：IN_PROGRESS  已取消：CANCELED  确认收货：CONFIRM_RECEIVE  已完成：FINISHED
//community_status [订单状态] 正在发布采买任务：PUBLISHING_PROCUREMENT  商品采买中：PROCURING  采买完成：PROCUREMENT_FINISHED  配送中：DELIVERING
                if (userStatus.equals("CONFIRM_RECEIVE")){
                    orders.setUserStatus("FINISHED");
                }else {
                    orders.setUserStatus(userStatus);
                }

                if (communityStatus.equals("PUBLISHING_PROCUREMENT")){
                    orders.setCommunityStatus("PROCURING");
                }else if (communityStatus.equals("PROCUREMENT_FINISHED")){
                    orders.setCommunityStatus("PROCURE_FINISHED");
                }else if (communityStatus.equals("CANCELED")){
                    orders.setCommunityStatus("PROCURE_FINISHED");
                }else {
                    orders.setCommunityStatus(communityStatus);
                }
                orders.setCommunityId(tOrders.getCommunityId());
//                orders.setBoxNo();
//                orders.setImage();
//                orders.setBoxTime();
//                orders.setDelivererId();
//                orders.setDeliveryFinishTime();
                orders.setUpdateTime(tOrders.getUpdateTime());
                orders.setCreateTime(tOrders.getCreateTime());
                ordersMapper.insert(orders);
            }



            //保存订单商品
            OrdersGoods ordersGoods = new OrdersGoods();
//            orders_goods_id      bigint not null comment '[主键]',
//            orders_id            bigint comment '[订单ID]',
//            supermarket_id       int comment '[超市ID]',
//            goods_sku_id         int comment '[商品ID]',
//            price                int comment '[用户创建订单时价格]单位：分，最低价',
//            quantity             int comment '[数量]',
//            actual_quantity      int comment '[实际采买数量]',
//            procure_status       varchar(50) comment '[采买状态]  采买中：PROCURING   采买完成：FINISHED',
//            supermarket_list     varchar(300) comment '[所有比价超市]  [{"supermarket_id":11,"price":10},{"supermarket_id":12,"price":11}]',
//            update_time          datetime comment '[更新时间]',
//            create_time          datetime comment '[创建时间]',
            String status1 = tOrdersGoods.getStatus();
            ordersGoods.setOrdersGoodsId(tOrdersGoods.getOrdersGoodsId());
            ordersGoods.setOrdersGoodsId(tOrdersGoods.getOrdersGoodsId());
            ordersGoods.setOrdersId(tOrdersGoods.getOrdersId());
            ordersGoods.setSupermarketId(tOrdersGoods.getSupermarketId());
            ordersGoods.setGoodsSkuId(tOrdersGoods.getGoodsSkuId());
            ordersGoods.setPrice(tOrdersGoods.getPrice());
            ordersGoods.setQuantity(tOrdersGoods.getQuantity());
            if (status1.equals("WAIT_PROCURE") || status1.equals("WAIT_DEAL")){
                //采买中
                ordersGoods.setActualQuantity(0);
                ordersGoods.setProcureStatus("PROCURING");
            }else{
                ordersGoods.setActualQuantity(tOrdersGoods.getQuantity());
                ordersGoods.setProcureStatus(status1);
                ordersGoods.setProcureStatus("FINISHED");
            }


            List<Map> list1 = JSON.parseArray(tOrdersGoods.getSupermarketList(), Map.class);
            for (Map map : list1) {
                map.remove("status");
            }
            ordersGoods.setSupermarketList(JSON.toJSONString(list1));
            ordersGoods.setUpdateTime(tOrdersGoods.getUpdateTime());
            ordersGoods.setCreateTime(tOrdersGoods.getCreateTime());
            ordersGoodsMapper.insert(ordersGoods);
            TSupermarket tSupermarket = tSupermarketMapper.selectSupermarketBysupermarketId(tOrdersGoods.getSupermarketId());
            ProcurementTask procurementTask = new ProcurementTask();
            //保存采买任务
            if (!"BOUGHT".equals(status1)){
                //采买中
                procurementTask.setProcureQuantity(0);
                procurementTask.setActualQuantity(0);
            }else if ("BOUGHT".equals(status1)){
                //采买完成
                procurementTask.setProcureQuantity(tOrdersGoods.getQuantity());
                procurementTask.setActualQuantity(tOrdersGoods.getQuantity());
            }
            //procurementTask.setProcurementTaskId();
            procurementTask.setUserId(tSupermarket.getProcurerId());
            procurementTask.setQuantity(tOrdersGoods.getQuantity());
            procurementTask.setOrdersGoodsId(tOrdersGoods.getOrdersGoodsId());
            procurementTask.setOrdersId(tOrdersGoods.getOrdersId());
            procurementTask.setSupermarketId(tOrdersGoods.getSupermarketId());
            procurementTask.setGoodsSkuId(tOrdersGoods.getGoodsSkuId());
            procurementTask.setPrice(tOrdersGoods.getPrice());
            procurementTask.setReceiveQuantity(0);
            List<Map> list = JSON.parseArray(tOrdersGoods.getSupermarketList(), Map.class);
            List<Map> mapList = list.stream().filter(item -> item.get("status") != null).collect(Collectors.toList());
            Collections.reverse(mapList);
            String status = (String) mapList.get(0).get("status");
//            procurementTask.setBoxQuantity();
            //[采买状态]  采买中：PROCURING   缺货：LACK  价高未买：PRICE_HIGH  已买：BOUGHT

//          [采买状态] ：待采买：WAIT_PROCURE  缺货：NO_GOODS  价高未买：PRICE_HIGH  已买：BOUGHT
            if (status.equals("WAIT_PROCURE")){
                status = "PROCURING";
            }else if(status.equals("NO_GOODS")){
                status = "LACK";
            }
            procurementTask.setProcureStatus(status);
//            procurementTask.setReceiveStatus();
//            procurementTask.setBoxQuantity();
            procurementTask.setCommunityId(tOrders.getCommunityId());
//            procurementTask.setDeliveryBoxId();
//            procurementTask.setBoxNo();
            procurementTask.setUpdateTime(new Date());
            procurementTask.setCreateTime(new Date());

            procurementTaskMapper.insert(procurementTask);

        }

    }

}
