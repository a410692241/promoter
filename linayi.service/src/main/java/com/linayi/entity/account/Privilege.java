package com.linayi.entity.account;

import com.linayi.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;


public class Privilege extends BaseEntity {
	private Integer id;
	private String text;
	private String url;
	private List<Privilege> children = new ArrayList<Privilege>();
	
	public Privilege() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Privilege(Integer id, String text, String url, List<Privilege> children) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.children = children;
	}

	@Override
	public String toString() {
		return "Privilege [id=" + id + ", text=" + text + ", url=" + url + ", children=" + children + "]";
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

	public List<Privilege> getChildren() {
		return children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}
	
	
}
