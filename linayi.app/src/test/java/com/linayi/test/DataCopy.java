package com.linayi.test;


import com.linayi.entity.goods.GoodsSku;
import com.linayi.service.search.GoodsSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class DataCopy {

    @Autowired
    GoodsSearchService goodsSearchService;


    @Test
    public void test(){

        try {
            List<GoodsSku> search = goodsSearchService.search("墨菲", true, 1, 10, "price", false, "desc");
            for (GoodsSku goodsSku : search) {
                System.out.println("goodsSku = " + goodsSku.getDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
