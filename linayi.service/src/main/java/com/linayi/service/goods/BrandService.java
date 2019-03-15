package com.linayi.service.goods;

import com.linayi.entity.goods.Brand;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService {

    /*查询所有的品牌*/
    List<Brand> getBrandsByName();

    /*根据品牌的名字获得Id*/
    Integer getBrandIdByBrandName(String name);

    /*新增品牌*/
    Integer addBrand(Brand brand);

    /**
     * 根据id查找品牌
     *
     * @param brandId
     * @return
     */
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

    Object insetExcelBrand(MultipartFile file);

    void insertBrand(Brand brand);

    Brand selectBrandByName(String name);
}
