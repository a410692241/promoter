package com.linayi.controller.order;

import com.linayi.controller.BaseController;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.exception.ErrorType;
import com.linayi.service.order.OrdersGoodsService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/goods/ordersGoods")
public class OrdersGoodsController extends BaseController {

    @Autowired
    private OrdersGoodsService ordersGoodsService;
    /**
     *
     * 修改订单商品状态
     * @param param
     * @return
     */
    @RequestMapping("/updateOrderGoodsStatus.do")
    public Object updateOrderGoodsStatus(@RequestBody Map<String, Object> param){
        ParamValidUtil<OrdersGoods> pvu = new ParamValidUtil<>(param);
        try {
            pvu.Exist("status","ordersGoodsId");
            OrdersGoods ordersGoods = pvu.transObj(OrdersGoods.class);
            ordersGoodsService.updateOrdersGoodsStatus(ordersGoods);
            return new ResponseData("success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("F", ErrorType.SYSTEM_ERROR.getErrorMsg());
    }




}
