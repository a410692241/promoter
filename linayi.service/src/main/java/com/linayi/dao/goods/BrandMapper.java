package com.linayi.dao.goods;

import com.linayi.entity.goods.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    int insert(Brand record);

    int insertSelective(Brand record);

    List<Brand> getBrandsByName(@Param("offset") Integer offset, @Param("count") Integer count, @Param("name") String name);

    Brand getBrandById(Integer brandId);

    /**
     * 根据品牌名模糊查找品牌
     *
     * @param brandName
     * @return
     */
    List<Brand> getBrandByName(Brand brand);

    void deleteByBrandId(int brandId);

    List<Brand> getBrandAll(Brand brand);

    void insertBrandExcel(String name);

    void insertBrand(Brand brand);

    Brand selectBrandByName(String name);
}