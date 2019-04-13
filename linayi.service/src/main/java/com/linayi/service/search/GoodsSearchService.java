package com.linayi.service.search;

import com.linayi.entity.goods.GoodsSku;

import java.util.List;

public interface GoodsSearchService {

     List<GoodsSku> search(Object text, boolean isHighlight, Integer pageNum, Integer pageSize, String sortField, boolean order, String... fieldNames) throws Exception;

     List<GoodsSku> search(Object text,String... fieldNames) throws Exception;

     List<GoodsSku> search(Object text,Integer pageNum, Integer pageSize,String... fieldNames) throws Exception;

     List<GoodsSku> search(Object text,Integer pageNum, Integer pageSize,String sortField,boolean order,String... fieldNames) throws Exception;
}
