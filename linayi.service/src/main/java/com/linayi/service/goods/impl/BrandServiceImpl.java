package com.linayi.service.goods.impl;

import com.linayi.dao.goods.BrandMapper;
import com.linayi.entity.goods.Brand;
import com.linayi.service.goods.BrandService;
import com.linayi.util.CheckUtil;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Override
    public List<Brand> getBrandsByName() {
        return brandMapper.getBrandsByName(null, 0, null);
    }

    @Override
    public Integer getBrandIdByBrandName(String name) {
        List<Brand> brandList = brandMapper.getBrandsByName(null, 0, name);
        if(brandList != null && brandList.size() > 0){
            return brandList.get(0).getBrandId();
        }
        return null;
    }

    @Override
    public Integer addBrand(Brand brand) {
        brandMapper.insertSelective(brand);
        return brand.getBrandId();
    }

    @Override
    public Brand getBrandById(Integer brandId) {
        return brandMapper.getBrandById(brandId);
    }


    //根据品牌名模糊查找品牌
	@Override
	public List<Brand> getBrandByName(Brand brand) {
		List<Brand> brandList = brandMapper.getBrandByName(brand);
		for(int i=0;i<brandList.size();i++){
			String image = brandList.get(i).getLogo();
			String logo = image;
			brandList.get(i).setLogo(logo);
		}
		return brandList;
	}

    @Override
    public void deleteByBrandId(int brandId) {
        brandMapper.deleteByBrandId(brandId);
    }

    @Override
    public List<Brand> getBrandAll(Brand brand) {
        return brandMapper.getBrandAll(brand);
    }

    @Override
    public void insertBrandExcel(String name) {
        brandMapper.insertBrandExcel(name);
    }

    public Object insetExcelBrand(MultipartFile file){
        Brand brand = new Brand();
        InputStream inputStream=null;
        String contentType = file.getContentType();
        String[] split = contentType.split("/");
        String type = split[1];
        if(!type.equals("vnd.ms-excel")){
            return null;
        }
        try{
            inputStream = file.getInputStream();
            Workbook wb = Workbook.getWorkbook(inputStream);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            System.out.println();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        if(cellinfo.isEmpty()){
                            continue;
                        }
                        innerList.add(cellinfo);
                        if(j==0) {
                            /* brand.setName(cellinfo);*/ //如果后期要导入多列的话考虑这个
                            System.out.println(cellinfo+"-----------");
                                Brand br = brandMapper.selectBrandByName(cellinfo);
                                if(CheckUtil.isNullEmpty(br)){
                                    brandMapper.insertBrandExcel(cellinfo);
                                }else if(!CheckUtil.isNullEmpty(br)){
                                    continue;
                                }
                        }

                    }
                    outerList.add(i, innerList);
                    System.out.println();
                }
                return outerList;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertBrand(Brand brand) {
        brandMapper.insertBrand(brand);
    }

    @Override
    public Brand selectBrandByName(String name) {
        Brand brand = brandMapper.selectBrandByName(name);
        return brand;
    }
}
