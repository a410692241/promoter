package com.linayi.service.account;

import com.linayi.entity.account.Menu;
import com.linayi.entity.account.MenuTree;
import com.linayi.entity.account.TreeNodeBO;

import java.util.Collection;
import java.util.List;

public interface AccountMenuService {

	/**
	 * 获取所有菜单信息
	 * @param moduleId
	 * @return
	 */
	List<MenuTree> getAllMenuList();
	/**
	 * 通过账号ID获取菜单信息
	 * @param accountId
	 * @return
	 */
	List<MenuTree> getMenuListByAccountId(Integer accountId);
	/**
	 * 添加菜单
	 * @param menu
	 * @return
	 */
	Integer addMenu(Menu menu);
	
	/**
	 * 根据账号ID和菜单ID编辑菜单
	 * @param menu
	 * @return
	 */
	Integer changeMenu(Menu menu,String category);

	/**
	 * 根据角色id查询出模块和菜单并转json
	 * @param roleId
	 * @return
	 */
	Collection<TreeNodeBO> getModelMenusByAccId(Integer roleId);
}
