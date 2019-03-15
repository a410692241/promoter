package com.linayi.entity.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.linayi.util.ImageUtil;
import org.springframework.format.annotation.DateTimeFormat;

import com.linayi.entity.BaseEntity;

public class Category extends BaseEntity {
    private Integer categoryId;

    private String name;

    private Integer parentId;

    private String logo;

    private Integer orderNo;

    private Short level;

    private String status;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    
    private String parentName;
    
    private List<Category> child = new ArrayList<Category>();
    
    private List<Integer> categoryIdList = new ArrayList<>();
    
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public List<Category> getChildren() {
		return child;
	}

	public void setChildren(List<Category> child) {
		this.child = child;
	}

	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}


	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}