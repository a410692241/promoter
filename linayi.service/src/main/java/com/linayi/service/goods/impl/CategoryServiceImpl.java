package com.linayi.service.goods.impl;

import com.linayi.dao.goods.CategoryMapper;
import com.linayi.entity.goods.Category;
import com.linayi.exception.ErrorType;
import com.linayi.service.goods.CategoryService;
import com.linayi.util.OSSManageUtil;
import com.linayi.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<Category> getCategorysByLevel(int level) {
		return categoryMapper.getCategorysByCate(level, null);
	}

	@Override
	public Integer getCategoryByName(String name) {
		List<Category> categorysByCate = categoryMapper.getCategorysByCate(null, name);
		if (categorysByCate != null && categorysByCate.size() > 0){
			return  categorysByCate.get(0).getCategoryId();
		}
		return null;
	}

	@Override
	public Integer addCategory(Category category1) {
		categoryMapper.insertSelective(category1);
		return category1.getCategoryId();
	}

	@Override
	public Category getCategoryById(Integer categoryId) {
		Category category = new Category();
		if (categoryId != null && categoryId == -1) {
			category.setName("ROOT");
			category.setLevel(Short.valueOf("0"));
			category.setCategoryId(-1);
		}else{
			category = categoryMapper.getCategoryById(categoryId);
		}
		if(category != null){
			category.setLogo(category.getLogo());
		}
		return category;
	}

	@Override
	public List<Category> getCatergoysList(Short level) {
		List<Category> category1 = categoryMapper.getCategorysByLevel(level);
		for (Category c1 : category1) {
			Integer categoryId1 = c1.getCategoryId();
			String parentId1 = categoryId1.toString();
			List<Category> category2 = categoryMapper.getCategorysByParentId(parentId1);
			c1.setChildren(category2);
			for (Category c2 : category2) {
				Integer categoryId2 = c2.getCategoryId();
				String parentId2 = categoryId2.toString();
				List<Category> category3 = categoryMapper.getCategorysByParentId(parentId2);
				c2.setChildren(category3);
				for (Category c3 : category3) {
					Integer categoryId3 = c3.getCategoryId();
					String parentId3 = categoryId3.toString();
					List<Category> category4 = categoryMapper.getCategorysByParentId(parentId3);
					c3.setChildren(category4);
					for (Category c4 : category4) {
						Integer categoryId4 = c4.getCategoryId();
						String parentId4 = categoryId4.toString();
						List<Category> category5 = categoryMapper.getCategorysByParentId(parentId4);
						c4.setChildren(category5);
					}
				}
			}
		}
		return category1;
	}

	@Override
	public String insertCategory(Category category,MultipartFile file) {
		try {
			Integer parentId = category.getCategoryId();
			String name = category.getName();
			Short level = (short) (category.getLevel()+1);
			Date createTime = new Date();
			Category category1 = new Category();
			category1.setLevel(level);
			if (!file.isEmpty()) {
				category1.setLogo(OSSManageUtil.uploadFile(file));
			}
			category1.setCreateTime(createTime);
			category1.setName(name);
			category1.setParentId(parentId);
			category1.setStatus("NORMAL");
			categoryMapper.insertSelective(category1);
		} catch (Exception e) {
			return new ResponseData(ErrorType.SYSTEM_ERROR).toString();
		}
		return new ResponseData("操作成功！").toString();
	}

	@Override
	public String updateCategory(Category category,MultipartFile file) {
		try {
			String name = category.getName();
			Integer categoryId = category.getCategoryId();
			Date updateTime = new Date();
			Category category1 = new Category();
			if (!file.isEmpty()) {
				category1.setLogo(OSSManageUtil.uploadFile(file));
			}
			category1.setCategoryId(categoryId);
			category1.setName(name);
			category1.setUpdateTime(updateTime);
			categoryMapper.updateBycategoryId(category1);
		} catch (Exception e) {
			return new ResponseData("F",ErrorType.SYSTEM_ERROR.getErrorMsg()).toString();
		}
		return new ResponseData("操作成功！").toString();
	}

	@Override
	public void deleteCategory(String[] categoryId) {
		if(categoryId.length > 0 && !categoryId[0].trim().equals("")){
			for (String s : categoryId) {
				Integer categoryId1 = Integer.valueOf(Integer.parseInt(s));
				Category category = new Category();
				category.setCategoryId(categoryId1);
				category.setStatus("REMOVED");
				categoryMapper.updateBycategoryId(category);
			}
		}
	}

	@Override
	public List<Category> findAllCategory(Integer parentId) {
		List<Category> categoryList = categoryMapper.findAllCategory(parentId);
		for (Category c : categoryList){
			c.setLogo(c.getLogo());
		}
		return categoryList;
	}



	public List<Category> allCategory(){
		List<Category> categoryList = findAllCategory(893);

		for(Category cg:categoryList) {
			List<Category> childrenCategory = findAllCategory(cg.getCategoryId());
			cg.setChildren(childrenCategory);
			for (Category c : childrenCategory) {
				List<Category> children = findAllCategory(c.getCategoryId());
				c.setChildren(children);
			}
		}

		return categoryList;
	}

	@Override
	public List<Category> getCategoryByBrandId(Integer brandId) {

		List<Category>categoryList = categoryMapper.getCategoryByBrandId(brandId);
		if(categoryList.size()>0) {
			for(Category i:categoryList) {
				i.setLogo(i.getLogo());
			}
		}

		return categoryList;
	}


}
