package com.linayi.test;


import com.linayi.entity.goods.GoodsSku;
import com.linayi.search.GoodsSearchService;
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
            List<GoodsSku> search = goodsSearchService.search("奶", true, 1, 10, "goods_sku_id", false, "full_name","name");
            for (GoodsSku goodsSku : search) {
                System.out.println(goodsSku.getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
