package com.linayi.service.goods;

import com.linayi.entity.goods.Category;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    /*根据级别查找类别*/
    List<Category> getCategorysByLevel(int level);
    /*根据级别名字查找类别的Id*/
    Integer getCategoryByName(String name);
    /*新增分类信息*/
    Integer addCategory(Category category1);
    /*根据Id查找类别*/
    Category getCategoryById(Integer categoryId);
    /*根据level查询Category对象集合*/
	List<Category> getCatergoysList(Short level);
	/*新增信息*/
	String insertCategory(Category category,MultipartFile file);
	/*修改信息*/
	String updateCategory(Category category,MultipartFile file);
	/*修改status状态*/
	void deleteCategory(String[] categoryId);
	   /**
     * 根据父类id获取商品分类信息
     * @param categoryId
     * @return
     */
    List<Category> findAllCategory(Integer parentId);
    
    
    
    /**
     * 根据品牌id获取分类
     * @param brandId
     * @return
     */
    List<Category>getCategoryByBrandId(Integer brandId);

    /**
     * 查询所有的分类
     * @return
     */
    List<Category> allCategory();
}
