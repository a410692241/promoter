package com.linayi.test;


import com.linayi.entity.goods.SupermarketGoods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class DataCopy {


    @Test
    public void test1(){
//[{"price":250,"supermarket_id":7},{"price":280,"supermarket_id":2},
//        {"price":280,"supermarket_id":4},{"price":289,"supermarket_id":11}]
        List<SupermarketGoods> supermarketGoodsList = new ArrayList<>();
        SupermarketGoods supermarketGoods1 = new SupermarketGoods();
        supermarketGoods1.setPrice(250);
        supermarketGoods1.setSupermarketId(7);
        supermarketGoods1.setRank(2);
        supermarketGoodsList.add(supermarketGoods1);
        SupermarketGoods supermarketGoods2 = new SupermarketGoods();
        supermarketGoods2.setPrice(250);
        supermarketGoods2.setSupermarketId(2);
        supermarketGoods2.setRank(1);
        supermarketGoodsList.add(supermarketGoods2);
        SupermarketGoods supermarketGoods3 = new SupermarketGoods();
        supermarketGoods3.setPrice(260);
        supermarketGoods3.setSupermarketId(8);
        supermarketGoods3.setRank(5);
        supermarketGoodsList.add(supermarketGoods3);
        SupermarketGoods supermarketGoods4 = new SupermarketGoods();
        supermarketGoods4.setPrice(250);
        supermarketGoods4.setSupermarketId(9);
        supermarketGoods4.setRank(4);
        supermarketGoodsList.add(supermarketGoods4);
        SupermarketGoods supermarketGoods5 = new SupermarketGoods();
        supermarketGoods5.setPrice(299);
        supermarketGoods5.setSupermarketId(10);
        supermarketGoods5.setRank(3);
        supermarketGoodsList.add(supermarketGoods5);

        List<SupermarketGoods> collects = supermarketGoodsList.stream().
                sorted((o1,o2) ->{
                    if(o1.getPrice() == null){
                        return 1;
                    }
                    if(o2.getPrice() == null){
                        return -1;
                    }
                    if((o2.getPrice()+"").equals(o1.getPrice() + "")){
                        return o2.getRank() - o1.getRank();
                    }
                    return o2.getPrice() - o1.getPrice();
                }).collect(Collectors.toList());
        for (SupermarketGoods collect : collects) {
            System.out.println("collect = " + collect);
        }
    }
}
