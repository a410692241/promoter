package com.linayi.service.account.impl;

import com.linayi.dao.account.AccountMenuMapper;
import com.linayi.dao.account.MenuMapper;
import com.linayi.dao.account.ModuleMapper;
import com.linayi.entity.account.Menu;
import com.linayi.entity.account.MenuTree;
import com.linayi.entity.account.Module;
import com.linayi.entity.account.TreeNodeBO;
import com.linayi.service.account.AccountMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AccountMenuServiceImpl implements AccountMenuService {
	@Resource
	private AccountMenuMapper accountMenuMapper;
	@Resource
	private ModuleMapper moduleMapper;
	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public List<MenuTree> getAllMenuList() {
		Module module = new Module();
		List<MenuTree> list= accountMenuMapper.getAllModule(module);
		
		for(int i=0;i<list.size();i++){
			list.get(i).setCategory("module");
			Integer moduleId = list.get(i).getId();
			List<MenuTree> list1= accountMenuMapper.getMenuByModuleId(moduleId);
			for(int j=0;j<list1.size();j++){
				list1.get(j).setCategory("menu");
			}
			list.get(i).setChildren(list1);
		}
		return list;
	}
	
	@Override
	@Transactional
	public Integer addMenu(Menu menu) {
		if(menu.getModuleId() != -1){
			//添加菜单
			Date date = new Date();
			menu.setCreateTime(date);
			accountMenuMapper.insertMenu(menu);
		}else{
			//添加模块
			Module module = new Module();
			module.setModuleName(menu.getMenuName());
			module.setStatus(menu.getStatus());
			
			Date date = new Date();
			module.setCreateTime(date);
			moduleMapper.insert(module);
			
		}
		return null;
	}
	
	@Override
	public Integer changeMenu( Menu menu,String category) {
		if("menu".equals(category)){
			//编辑菜单
			Date updateTime = new Date();
			menu.setUpdateTime(updateTime );
			accountMenuMapper.changeMenu(menu);
			
		}else{
			//编辑模块
			Date updateTime = new Date();
			Module module = new Module();
			module.setModuleId(menu.getMenuId());
			module.setModuleName(menu.getMenuName());
			module.setStatus(menu.getStatus());
			module.setUpdateTime(updateTime);
			moduleMapper.changeModule(module );
			
		}
		return null;
	}

	@Override
	public Collection<TreeNodeBO> getModelMenusByAccId(Integer roleId) {
		List<Module> modules1 = moduleMapper.getList();
		List<Menu> menus1 = menuMapper.getMenuByRoleId(roleId);
		List<Menu> menus2 =  menuMapper.getMenuList();
		//模块处理
		Map<Integer,TreeNodeBO> map = new HashMap<>();
		for (Module module : modules1) {
			TreeNodeBO treeNodeBO = new TreeNodeBO();
			treeNodeBO.setText(module.getModuleName());
			treeNodeBO.setId(module.getModuleId());
			map.put(treeNodeBO.getId(),treeNodeBO);
		}
		//根据角色拥有的菜单处理
		List<TreeNodeBO> treeNodeBOs = new ArrayList<>();
		for (int i = 0; i < menus2.size(); i++) {
			TreeNodeBO treeNodeBO = new TreeNodeBO();
			treeNodeBO.setText(menus2.get(i).getMenuName());
			treeNodeBO.setId(menus2.get(i).getMenuId());
			treeNodeBO.setParentId(menus2.get(i).getModuleId());
			if(menus1 != null && menus1.size() >0){
				for (Menu menu1 : menus1) {
					if (menus2.get(i).getMenuId() == menu1.getMenuId()){
						treeNodeBO.setChecked("true");
					}
				}
			}
			treeNodeBOs.add(treeNodeBO);
		}

		Set<Integer> integers = map.keySet();
		for (Integer integer : integers) {
			List<TreeNodeBO> list = new ArrayList<>();
			if (treeNodeBOs != null && treeNodeBOs.size() > 0){
				for (TreeNodeBO treeNodeBO : treeNodeBOs) {
					if (integer == treeNodeBO.getParentId()){
						list.add(treeNodeBO);
					}
				}
				map.get(integer).setChildren(list);
			}
		}
		Collection<TreeNodeBO> values = map.values();

		return values;
	}

	@Override
	public List<MenuTree> getMenuListByAccountId(Integer accountId) {
		Module module = new Module();
		module.setStatus("ENABLED");
		List<MenuTree> list = accountMenuMapper.getAllModule(module);
		List<Integer> menuIdList = accountMenuMapper.getMenuIdListByAccountId(accountId);
		List<Integer> mIdList = accountMenuMapper.gainMenuIdListByAccountId(accountId);
	
		for(int i=0;i<mIdList.size();i++){
			if(!menuIdList.contains(mIdList.get(i)) ){
				menuIdList.add(mIdList.get(i));
			}
		}
		for(int i =0;i<list.size();i++){
			list.get(i).setCategory("module");
			Integer moduleId = list.get(i).getId();
			if(menuIdList.size()>0){
				List<MenuTree> list1= accountMenuMapper.getMenuByModuleIdAndMenuIdList(moduleId, menuIdList);
				for(int j=0;j<list1.size();j++){
					list1.get(j).setCategory("menu");
				}
				if(list1.size() == 0){
					list.remove(i);
					i--;
					continue;
				}
				list.get(i).setChildren(list1);
			}else{
				list = new ArrayList<>();
			}
		}
		return list;
	}


}
