package com.linayi.controller.goods;


import java.util.List;

import com.linayi.exception.ErrorType;
import com.linayi.util.CheckUtil;
import com.linayi.util.PageResult;
import com.linayi.util.ResponseData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.linayi.entity.goods.Brand;
import com.linayi.service.goods.BrandService;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/goods/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;


    /*//根据品牌名模糊查找品牌
    @RequestMapping("/searchBrand.do")
    @ResponseBody
    public List<Brand> showBrandByBrandName(@RequestParam("brandName")String brandName) {

        List<Brand> brandList = brandService.getBrandByName(brandName);

        return brandList;
    }*/
    @RequestMapping("/list.do")
    @ResponseBody
    public Object selectBrandList(Brand brand) {
        List<Brand> list = brandService.getBrandAll(brand);
        PageResult<Brand> result = new PageResult<>(list, brand.getTotal());
        return result;
    }

    @Transactional(rollbackFor = Throwable.class)
    @RequestMapping("/delete.do")
    @ResponseBody
    public Object deleteBrand(int brandId) {
        brandService.deleteByBrandId(brandId);
        return new ResponseData("成功").toString();
    }

    @Transactional(rollbackFor = Throwable.class)
    @RequestMapping("/parse.do")
    @ResponseBody
    public Object parseExcel(MultipartFile file) {
        Object o = brandService.insetExcelBrand(file);
        return new ResponseData("操作成功");
    }

    @RequestMapping("/edit")
    public String edit() {
        return "/jsp/goods/AddBrand";
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object addBrand(String brandName) {
        if(CheckUtil.isNotNullEmpty(brandName)&&brandName.equals(null)) {
            Brand brand = new Brand();
            brand.setName(brandName);
            brand.setStatus("NORMAL");
            Brand bra = brandService.selectBrandByName(brandName);
            if (!CheckUtil.isNullEmpty(bra)) {
                return new ResponseData(ErrorType.BRAND_ERROR).toString();
            }
            brandService.insertBrand(brand);
            return new ResponseData("success").toString();
        }else{
            return new ResponseData(ErrorType.SYSTEM_ERROR);
        }
    }
}
