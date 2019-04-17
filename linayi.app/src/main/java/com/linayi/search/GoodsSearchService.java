package com.linayi.search;

import com.linayi.entity.goods.GoodsSku;
import com.linayi.util.PageResult;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface GoodsSearchService {

     PageResult<GoodsSku> search(Object text, boolean isHighlight, Integer pageNum, Integer pageSize, String sortField, boolean order, String... fieldNames) throws Exception;

     PageResult<GoodsSku> search(Object text,String... fieldNames) throws Exception;

     PageResult<GoodsSku> search(Object text, Integer pageNum, Integer pageSize,String... fieldNames) throws Exception;

     PageResult<GoodsSku> search(Object text, Integer pageNum, Integer pageSize, String sortField, boolean order,String... fieldNames) throws Exception;
}
