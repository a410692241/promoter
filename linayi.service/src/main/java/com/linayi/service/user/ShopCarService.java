package com.linayi.service.user;

import com.linayi.entity.user.ShoppingCar;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ShopCarService {
    /**
     * 新增购物车
     * @param shoppingCar
     */
    void addShopCar(ShoppingCar shoppingCar);

    /**
     * 购物车列表合计
     * @param shoppingCar
     * @param request
     */
    Map<String, Object> shopCarlistAdd(ShoppingCar shoppingCar, HttpServletRequest request);

    /**
     * 修改购物车数量和选中状态
     * @param shoppingCar
     */
    void updateGoodsNum(ShoppingCar shoppingCar);

    /**
     * 根据收货地址id购物车列表全选
     * @param shoppingCar
     * @return
     */
    void updateAllCarstatus(ShoppingCar shoppingCar);

    /**
     * 根据id删除购物车
     * @param shoppingCarId
     */
    void deleteCarById(Object shoppingCarId);

    /**
     * 根据接受地址id查询出所有选中购物车并返回商品列表，比价优惠，服务费，附加费用，预计送达时间，共多少件，合计
     * @param shoppingCar
     * @return
     */
    Map<String, Object> toOrder(ShoppingCar shoppingCar,HttpServletRequest request);
}
