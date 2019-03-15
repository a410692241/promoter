package com.linayi.dao.account;

import com.linayi.entity.account.AccountMenu;
import com.linayi.entity.account.Menu;
import com.linayi.entity.account.MenuTree;
import com.linayi.entity.account.Module;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMenuMapper {
    int insert(AccountMenu record);

    int insertSelective(AccountMenu record);
    /**
	 * 获取所有的模块
	 * @param accountId
	 * @return
	 */
	List<MenuTree> getAllModule(Module module);
	/**
	 * 通过模块ID获取对应的菜单信息，是否展示禁用的菜单
	 * @param moduleId
	 * @return
	 */
	List<MenuTree> getMenuByModuleId(Integer moduleId);
	/**
	 * 根据账号ID添加菜单
	 * @param menu
	 * @return
	 */
	Integer insertMenu(Menu menu);
	/**
	 * 根据账号ID和菜单ID编辑菜单
	 * @param menu
	 * @return
	 */
	Integer changeMenu(Menu menu);
	/**
	 * 根据账号ID和菜单ID删除菜单
	 * @param accountId
	 * @param menuId
	 * @return
	 */
	Integer deleteMenu(@Param("accountId")Integer accountId,@Param("menuId")Integer menuId);
	/**
	 * 根据账号ID在账号菜单拿到菜单Id集合
	 */
	List<Integer> getMenuIdListByAccountId(Integer accountId);
	/**
	 * 根据账号ID在角色菜单拿到菜单Id集合
	 */
	List<Integer> gainMenuIdListByAccountId(Integer accountId);
	/**
	 * 通过模块ID和菜单ID集合获取对应的菜单信息，是否展示禁用的菜单
	 * @param moduleId
	 * @return
	 */
	List<MenuTree> getMenuByModuleIdAndMenuIdList(@Param("moduleId")Integer moduleId,
			@Param("menuIdList")List<Integer> menuIdList);
}