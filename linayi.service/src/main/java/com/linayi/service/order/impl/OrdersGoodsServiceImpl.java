package com.linayi.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.linayi.dao.order.OrdersGoodsMapper;
import com.linayi.dao.order.OrdersMapper;
import com.linayi.dao.procurement.ProcurementTaskMapper;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.procurement.ProcurementTask;
import com.linayi.service.order.OrdersGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrdersGoodsServiceImpl implements OrdersGoodsService {
    @Autowired
    private OrdersGoodsMapper ordersGoodsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ProcurementTaskMapper procurementTaskMapper;

    @Transactional
    @Override
    public void updateOrdersGoodsStatus(OrdersGoods ordersGoods) {
        boolean flag = false;
        //boolean isBuy =false;
        OrdersGoods ordersGoods1 = ordersGoodsMapper.getOrdersGoodsById(ordersGoods.getOrdersGoodsId());
        List<Map> list = JSON.parseArray(ordersGoods1.getSupermarketList(), Map.class);
        Integer supermarketId = ordersGoods1.getSupermarketId();
        Map s = list.stream().filter(item -> item.get("supermarket_id") == supermarketId).collect(Collectors.toList()).stream().findFirst().orElse(null);
        s.put("status",ordersGoods.getStatus());
        int i = list.indexOf(s);
        Map<String,Object> map = new HashMap<>();
        if ((i + 1) < list.size()){
            map = list.get(i+1);
            if (map != null && (ordersGoods.getStatus().equals("PRICE_HIGH") || ordersGoods.getStatus().equals("NO_GOODS"))){
                ordersGoods.setStatus("WAIT_DEAL");
            }
        }

        ordersGoods.setSupermarketList(JSON.toJSONString(list));
        ordersGoodsMapper.updateOrdersGoodsById(ordersGoods);

        ProcurementTask procurementTask = new ProcurementTask();
        procurementTask.setOrdersId(ordersGoods1.getOrdersId());
        procurementTask.setOrdersGoodsId(ordersGoods1.getOrdersGoodsId());
        List<ProcurementTask> procurementTaskList = procurementTaskMapper.getProcurementTaskList(procurementTask);
        if (procurementTaskList != null && procurementTaskList.size() > 0){
            procurementTask = procurementTaskList.get(0);
        }
        procurementTask.setProcureStatus(ordersGoods.getStatus());
        procurementTaskMapper.updateProcurementTaskById(procurementTask);

        List<OrdersGoods> ordersGoodsList = ordersGoodsMapper.getOrdersGoodsByOrdersId(ordersGoods1.getOrdersId());
        for (OrdersGoods orderGoods : ordersGoodsList) {
            String status = orderGoods.getStatus();
            if ("WAIT_PROCURE".equals(status) || "WAIT_DEAL".equals(status)){
                flag = true;
            }

        }


        Orders orders = new Orders();
        orders.setOrdersId(ordersGoods1.getOrdersId());


        if (flag){
            //采买中
            orders.setCommunityStatus("PROCURING");
        }else {
            //采完成
            orders.setCommunityStatus("PROCUREMENT_FINISHED");
        }
        ordersMapper.updateOrderById(orders);
    }


    @Override
    public List<OrdersGoods> query(OrdersGoods ordersGoods) {
        return ordersGoodsMapper.getOrdersGoodsByOrdersGoods(ordersGoods);
    }

    @Override
    public List<OrdersGoods> getNoProcumentTaskOrders() {
        return ordersGoodsMapper.getNoProcumentTaskOrders();
    }
}
