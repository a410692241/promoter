package com.linayi.dao.user;

import com.linayi.entity.user.ShoppingCar;

import java.util.List;

public interface ShoppingCarMapper {
    int insert(ShoppingCar record);

    int insertSelective(ShoppingCar record);

    ShoppingCar getShopCarById(Integer shoppingCarId);

    /**
     * 根据接受地址id和选中状态查询所有的购物车 用户Id
     *
     * @param shoppingCar
     * @return
     */
    List<ShoppingCar> getAllCarByReceiveAddressId(ShoppingCar shoppingCar);

    int updateShopCar(ShoppingCar shoppingCar);

    int deleteCarById(Integer shoppingCarId);

    /**
     * 查询购物车
     *
     * @param shoppingCar
     * @return
     */
    ShoppingCar getShopCar(ShoppingCar shoppingCar);

    int updateByPrimaryKeySelective(ShoppingCar shoppingCar);
}