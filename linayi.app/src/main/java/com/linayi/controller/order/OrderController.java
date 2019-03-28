package com.linayi.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linayi.controller.BaseController;
import com.linayi.entity.order.Orders;
import com.linayi.entity.order.OrdersGoods;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.exception.ErrorType;
import com.linayi.service.order.OrderService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.util.ParamValidUtil;
import com.linayi.util.ResponseData;

@RestController
@RequestMapping("/goods/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    SupermarketService supermarketService;

    /**
     * 生成订单
     * @return
     */
    @RequestMapping("/addOrder.do")
    public Object addOrder(@RequestBody Map<String, Object> param){
        ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
        try {
            pvu.Exist("amount","serviceFee","extraFee","saveAmount");
            param.put("userId",getUserId());
            orderService.addOrder(param);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }
    @RequestMapping("/getOrderList.do")
    public Object getOrderList(@RequestBody Map<String, Object> param, HttpServletRequest request){
        ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
        try {
            Orders orders = pvu.transObj(Orders.class);
            orders.setUserId(getUserId());
            Object orderList = orderService.getOrderList(orders);

            return new ResponseData(orderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 查看订单详情
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("/getOrderDetails.do")
    public Object getOrderDetails(@RequestBody Map<String, Object> param, HttpServletRequest request){
        ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
        pvu.Exist("ordersId");
        try {
            Orders orders = pvu.transObj(Orders.class);
            Orders data = orderService.getOrderDetails(orders, request);
            return new ResponseData(data);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 根据采买员对应超市的Id和查询对应订单商品最低价超市Id的商品详情
     * @param param
     * @return
     */
    @RequestMapping("/orderGoodsDetailsById.do")
    public Object getOrderDetailsBySupermarket(@RequestBody Map<String, Object> param){
        ParamValidUtil<OrdersGoods> pvu = new ParamValidUtil<>(param);
        try {
            pvu.Exist("ordersGoodsId");
            Integer userId = getUserId();
            OrdersGoods data = orderService.getOrderGoodsDetailsById(userId, param);
            return new ResponseData(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    //public Object

    @RequestMapping("/updateOrderStatus.do")
    public Object updateOrderStatus(@RequestBody Map<String, Object> param){
        ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
        pvu.Exist("ordersId","userStatus");
        try {
            Orders orders = pvu.transObj(Orders.class);
            orderService.updateOrderStatus(orders);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 查询退款售后的订单
     * @param param
     * @return
     */
    @RequestMapping("/afterSale.do")
    public Object getAfterSaleOrders(@RequestBody Map<String, Object> param){
        ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
        try {
            //pvu.Exist("accessToken");
            List<Orders> ordersList =orderService.getAfterSaleOrders(getUserId());
            return new ResponseData(ordersList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 再来一单
     * @param param
     * @return
     */
    @RequestMapping("/againOrders.do")
    public Object againOrders(@RequestBody Map<String, Object> param){
        ParamValidUtil<Orders> pvu = new ParamValidUtil<>(param);
        try {
            //pvu.Exist("accessToken","ordersId");
            Orders orders = pvu.transObj(Orders.class);
            orders.setUserId(getUserId());
            orderService.againOrders(orders);
            return new ResponseData("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
     * 采买任务订单商品列表
     * @return
     */
    @RequestMapping("/getBuyOrderGoodsList.do")
    public Object getBuyOrderList(@RequestBody Map<String, Object> param){
        ParamValidUtil<OrdersGoods> pvu = new ParamValidUtil<>(param);
        Integer userId = getUserId();
        try{
            pvu.Exist("status");
            Supermarket supermarket = supermarketService.getSupermarketByProcurerId(userId);
            if (supermarket ==null){
                return new ResponseData("F",ErrorType.NOT_PROCURER.getErrorMsg());
            }
            OrdersGoods ordersGoods = pvu.transObj(OrdersGoods.class);
            ordersGoods.setSupermarketId(supermarket.getSupermarketId());
            Object ordersList = orderService.getBuyOrderList(ordersGoods);
            return new ResponseData(ordersList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg());
    }

    /**
	 * 查询装箱列表
	 */
	@RequestMapping("/boxingOrder.do")
	public Object boxingOrder(@RequestBody Orders order) {
		System.out.println(order.getTotal());
		try {
			if (order.getPageSize() == null) {
				order.setPageSize(15);
			}
			order.setCommunityId(getCommunityId());
			List<Orders> orderList = orderService.getBoxingList(order);

			Integer totalPage = (int) Math.ceil(Double.valueOf(order.getTotal()) / Double.valueOf(order.getPageSize()));
			if (totalPage <= 0) {
				totalPage++;
			}

			Map<String, Object> map = new HashMap<>();
			map.put("data", orderList);
			map.put("totalPage", totalPage);
			map.put("currentPage", order.getCurrentPage());
			return new ResponseData(map);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
	}

}
