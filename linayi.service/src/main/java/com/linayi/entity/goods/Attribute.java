package com.linayi.entity.goods;

import com.linayi.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Attribute extends BaseEntity {
    private Integer attributeId;

    private String name;

    private String status;
    
    /**[修改时间]*/
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date updateTime;
    
    /**[创建时间]*/
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date createTime;
    
	/**属性值*/
	private List<AttributeValue> attributeValueList = new ArrayList<>();
	
	/**
	 * 属性id集合
	 */
	private List<Integer> attributeIdList = new ArrayList<>();
	/***
	 * 管理员
	 */
	private String adminName;

	private String attributeValue;
	
	public List<Integer> getAttributeIdList() {
		return attributeIdList;
	}

	public void setAttributeIdList(List<Integer> attributeIdList) {
		this.attributeIdList = attributeIdList;
	}

	public List<AttributeValue> getAttributeValueList() {
		return attributeValueList;
	}

	public void setAttributeValueList(List<AttributeValue> attributeValueList) {
		this.attributeValueList = attributeValueList;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

	@Override
	public String toString() {
		return "Attribute [attributeId=" + attributeId + ", name=" + name + ", status=" + status + ", updateTime="
				+ updateTime + ", createTime=" + createTime + "]";
	}

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}