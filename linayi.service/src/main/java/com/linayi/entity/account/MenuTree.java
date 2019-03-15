package com.linayi.entity.account;

import java.util.ArrayList;
import java.util.List;

import com.linayi.entity.BaseEntity;

public class MenuTree extends BaseEntity{
	private Integer id;
	private String text;
	private String url;
	private String category;
	private String status;
	private List<MenuTree> children = new ArrayList<MenuTree>();

	public MenuTree() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuTree(Integer id, String text, String url, String category,String status, List<MenuTree> children) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.category = category;
		this.children = children;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
