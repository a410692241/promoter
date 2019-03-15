package com.linayi.entity.account;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBO {

	private Integer id;
	
	private Integer parentId;
	
	private String text;
	
	private String state;
	
	private String checked;
	
	/**菜单状态*/
	private String status;
	
	/**菜单url*/
	private String url;
	
	/**是否在首页显示*/
	private String common;
	
	/**首页图标*/
	private String icon;
	
	private List<TreeNodeBO> children = new ArrayList<TreeNodeBO>();

	/**权限编码*/
	private String privilegeCode;
	
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public List<TreeNodeBO> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeBO> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}
	
	public String getIcon() {
		return icon;
	}

	
	public void setIcon( String icon ) {
		this.icon = icon;
	}
	
	public String getPrivilegeCode() {
		return privilegeCode;
	}

	
	public void setPrivilegeCode( String privilegeCode ) {
		this.privilegeCode = privilegeCode;
	}
}
