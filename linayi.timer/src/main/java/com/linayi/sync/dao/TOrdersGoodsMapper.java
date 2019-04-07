package com.linayi.sync.dao;


import com.linayi.sync.entity.TOrdersGoods;

import java.util.List;

public interface TOrdersGoodsMapper {
    int insert(TOrdersGoods record);

    int insertSelective(TOrdersGoods record);

   /* *//**
     * @param ordersId
     * @return
     *//*
    List<TOrdersGoods> getTOrdersGoodsByOrdersId(Long ordersId);

    void updateTOrdersGoodsById(TOrdersGoods TOrdersGoods);

    TOrdersGoods getTOrdersGoodsById(Long ordersId);

    List<TOrdersGoods> getTOrdersGoodsByTOrdersGoods(TOrdersGoods TOrdersGoods);*/

    List<TOrdersGoods> query(TOrdersGoods TOrdersGoods);

    List<TOrdersGoods> getNoProcumentTaskOrders();

    TOrdersGoods getTOrdersGoods(Integer goodsSkuId, long ordersId);
}