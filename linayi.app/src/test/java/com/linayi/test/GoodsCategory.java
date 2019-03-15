package com.linayi.test;

import java.util.List;

import com.linayi.dao.goods.CategoryMapper;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.linayi.entity.goods.Category;
import com.linayi.service.goods.CategoryService;

public class GoodsCategory {
	@Test
	public void category() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("config/mybatis-config.xml","config/spring-context.xml");
		CategoryMapper categoryService = ac.getBean("categoryMapper", CategoryMapper.class);
		List<Category> list = categoryService.findAllCategory(-1);
		System.out.println("list+++"+list);

//		for(Category gc:list) {
//			System.out.println(gc.getName()+"11111="+gc.getCategoryId());
//			List<Category> list1 = categoryService.findAllCategory(gc.getCategoryId());
//			for(Category gc1:list1){
//				System.out.println(gc1.getName()+"22222="+gc1.getCategoryId());
//				List<Category> list2 = categoryService.findAllCategory(gc1.getCategoryId());
//				for(Category gc2:list2){
//					System.out.println(gc2.getName()+"33333="+gc2.getCategoryId());
//				}
//			}
//		}
		ac.close();
	}
}
