package com.linayi.service.search.impl;

import com.alibaba.fastjson.JSON;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.service.search.GoodsSearchService;
import org.apache.commons.lang3.StringUtils;
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
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GoodsSearchServiceImpl implements GoodsSearchService {

    @Autowired
    private RestHighLevelClient client;

    /**
     *
     * @param text  查询条件
     * @param isHighlight 是否高亮
     * @param pageNum 页码   从1开始
     * @param pageSize 页的大小
     * @param sortField 排序属性
     * @param order 排序顺序  false正序 true逆序
     * @param fieldNames 查询的属性
     * @return
     * @throws Exception
     */
    @Override
    public List<GoodsSku>  search(Object text,boolean isHighlight,Integer pageNum,Integer pageSize,String sortField,boolean order, String... fieldNames) throws Exception {
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";

        //1 构建匹配条件
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(text, fieldNames);
        //2组合匹配条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(queryBuilder);
        //3创建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //只展示需要的属性
        //sourceBuilder.fetchSource(new String[]{"id","price","title","brand"},null);
        sourceBuilder.query(boolQueryBuilder);
        //默认相关度倒叙排序
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        if (StringUtils.isEmpty(sortField)) {
            SortOrder sortOrder = SortOrder.ASC;
            if (!order) {
                sortOrder = SortOrder.DESC;
            }
            sourceBuilder.sort(new FieldSortBuilder(sortField).order(sortOrder));
        }
        //是否高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        if (isHighlight) {
            if (fieldNames != null && fieldNames.length > 0) {
                for (String fieldName : fieldNames) {
                    highlightBuilder.field(fieldName);
                }
                highlightBuilder.preTags(preTag).postTags(postTag);
            }
        }
        if (pageNum != null && pageSize != null) {
            sourceBuilder.from((pageNum - 1) * pageSize).size(pageNum * pageSize);
            sourceBuilder.highlighter(highlightBuilder);
        }
        //4创建搜索Request
        SearchRequest request = new SearchRequest("goods_index");
        request.types("goods");
        request.source(sourceBuilder);
        //5解析反馈结果
        SearchResponse response = client.search(request);
        SearchHits hits = response.getHits();
        List<GoodsSku> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map tempSource = hit.getSourceAsMap();
            GoodsSku goodsSku = JSON.parseObject(JSON.toJSONString(tempSource), GoodsSku.class);
            //获取对应的高亮域
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (isHighlight) {
                Class<? extends GoodsSku> aClass = goodsSku.getClass();
                if (fieldNames != null && fieldNames.length > 0) {
                    for (String fieldName : fieldNames) {
                        String field = highlightFields.get(fieldName).getFragments()[0].toString();
                        String setName = parSetName(fieldName);
                        Method method = aClass.getMethod(setName, String.class);
                        method.invoke(goodsSku, field);
                    }
                }
            }
            list.add(goodsSku);
        }
        return list;
    }

    /**
     *
     * @param text 查询条件
     * @param fieldNames  查询的属性
     * @return
     * @throws Exception
     */
    @Override
    public List<GoodsSku> search(Object text,String... fieldNames) throws Exception {
        return search(text,false,null,null,null,false,fieldNames);
    }
    @Override
    public List<GoodsSku> search(Object text,Integer pageNum, Integer pageSize,String... fieldNames) throws Exception {
        return search(text,false,pageNum,pageSize,null,false,fieldNames);
    }

    @Override
    public List<GoodsSku> search(Object text,Integer pageNum, Integer pageSize,String sortField,boolean order,String... fieldNames) throws Exception {
        return search(text,false,pageNum,pageSize,sortField,order,fieldNames);
    }


    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return String
     */
    private String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }
}
