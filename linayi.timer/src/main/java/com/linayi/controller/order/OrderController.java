package com.linayi.controller.order;

import com.linayi.entity.order.Orders;
import com.linayi.service.order.OrderService;
import com.linayi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/orderList.do")
    @ResponseBody
    public Object list(Orders orders){
        List<Orders> list = orderService.getOrderAll(orders);
        for(Orders o:list){
            String addressOne = o.getAddressOne();
            String addressTwo = o.getAddressTwo();
            String addressThree = o.getAddressThree();
            o.setAddress(addressOne+":"+addressTwo+":"+addressThree);
        }
        PageResult<Orders> pageResult = new PageResult<>(list,orders.getTotal());
        return pageResult;
    }


}
