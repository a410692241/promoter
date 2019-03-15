package com.linayi.dao.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linayi.entity.goods.Category;

public interface CategoryMapper {
	int insert(Category record);

	int insertSelective(Category record);

	List<Category> getCategorysByCate(@Param("level") Integer level, @Param("name") String name);

	Category getCategoryById(Integer categoryId);

	/**
	 * 根据level查询Category对象集合
	 * @param level 级别
	 * @return Category对象集合
	 */
	List<Category> getCategorysByLevel(Short level);

	/**
	 * 根据ParentId 查询Category对象集合
	 * @param parentId 父级Id
	 * @return Category对象集合0
	 */
	List<Category> getCategorysByParentId(String parentId);

	/**
	 * 根据categoryId删除信息
	 * @param categoryId 主键Id
	 * @return 影响的行数
	 */
	Integer deleteBycategoryId(Integer categoryId);

	/**
	 * 根据主键categoryId修改信息
	 * @param categoryId 主键Id
	 * @return 影响的行数
	 */
	Integer updateBycategoryId(Category category);
	
	/**
	 * 根据ParentId查询categoryId集合
	 * @param parentId 父级Id
	 * @return  categoryId集合
	 */
	List<Integer> getCategoryIdsByParentId(String parentId);
    
    /**
     * 根据父类id获取商品分类信息
     * @param categoryId
     * @return
     */
    List<Category> findAllCategory(Integer parentId);

	/**
	 * 获取最高层级
	 * @return
	 */
	Integer findMaxLevenl();
    
    /**
     * 根据品牌id获取分类
     * @param brandId
     * @return
     */
    List<Category>getCategoryByBrandId(Integer brandId);
}