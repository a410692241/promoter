package com.linayi.controller.account;

import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.account.Menu;
import com.linayi.entity.account.MenuTree;
import com.linayi.entity.account.TreeNodeBO;
import com.linayi.exception.ErrorType;
import com.linayi.service.account.AccountMenuService;
import com.linayi.util.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;



@Controller
@RequestMapping("/accountMenu")
public class AccountMenuController {
	@Resource
	private AccountMenuService accountMenuService;

	/**
	 * 获取菜单信息，在前端界面渲染
	 * @param
	 * @return
	 */
	@RequestMapping("/list.do")
	public ModelAndView list(HttpSession session) {
		ModelAndView view = new ModelAndView("forward:/index.jsp");
		AdminAccount adminAccount = (AdminAccount) session.getAttribute("loginAccount");
		List<MenuTree> list = accountMenuService.getMenuListByAccountId(adminAccount.getAccountId());
		view.addObject("privilegeTree", list);
		return view;
	}

	/**
	 * 菜单权限管理
	 * @return
	 */
	@RequestMapping("/getPrivilegeTree.do")
	@ResponseBody
	public Object getPrivilegeTree() {
		ResponseData rr = null;
		try {
			List<MenuTree> list = accountMenuService.getAllMenuList();
			MenuTree node = new MenuTree();
			node.setText("root");
			//设置二级菜单图片显示
			node.setChildren(list);
			return Arrays.asList(node);
		} catch (Exception e) {
			rr = new ResponseData(ErrorType.SYSTEM_ERROR.toString());
		}
		return rr;
	}

	/**
	 * 添加菜单
	 * @return
	 */
	@RequestMapping(value="/addMenu.do")
	@ResponseBody
	public ResponseData addMenu(Menu menu,@RequestParam("category")String category,HttpSession session){
		ResponseData rr = null;
		try {
			accountMenuService.addMenu(menu);
			rr = new ResponseData(menu);
		} catch (Exception e) {
			rr = new ResponseData( ErrorType.SYSTEM_ERROR);
		}

		return rr;

	}

	/**
	 * 编辑菜单
	 */
	@RequestMapping(value="/updateMenu.do")
	@ResponseBody
	public ResponseData updateMenu(Menu menu,@RequestParam("category")String category){
		ResponseData rr = null;
		
		try {
			accountMenuService.changeMenu( menu, category);
			rr = new ResponseData(menu);
		} catch (Exception e) {
			rr = new ResponseData( ErrorType.SYSTEM_ERROR);
		}

		return rr;

	}
	/**
	 * 删除菜单
	 */
	@RequestMapping(value="/deleteMenu.do")
	@ResponseBody
	public ResponseData deleteMenu(Integer id,String category,HttpSession session){
		Integer accountId = (Integer) session.getAttribute("");
		ResponseData rr = null;
		if(accountId == null || accountId !=null){
			accountId = 1;
		}

		rr = new ResponseData("修改成功");
		return rr;
	}


	@RequestMapping("/getModelMenus.do")
	@ResponseBody
	public Collection<TreeNodeBO> getModelMenusByAccId(Integer roleId) {
		Collection<TreeNodeBO> treeNodeBOs = accountMenuService.getModelMenusByAccId(roleId);
		return treeNodeBOs;
	}
}
