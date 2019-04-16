package com.linayi.test;


import com.linayi.entity.goods.GoodsSku;
import com.linayi.search.GoodsSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class DataCopy {

    @Autowired
    GoodsSearchService goodsSearchService;

    @Autowired
    private RestHighLevelClient client;


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
    @Test
    public void test1() throws IOException {

//1 构建匹配条件
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

        //2组合匹配条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(queryBuilder);
        //3创建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //只展示需要的属性
        //sourceBuilder.fetchSource(new String[]{"id","price","title","brand"},null);
        sourceBuilder.query(boolQueryBuilder);
        SearchRequest request = new SearchRequest("cars");
        request.types("transactions");
        request.source(sourceBuilder);
        //5解析反馈结果
        SearchResponse response = client.search(request);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsMap());
        }

    }
}
