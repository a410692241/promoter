package com.linayi.entity.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttributeValue {
    private Integer valueId;

    private String value;

    private Integer attributeId;

    private String status;
    //属性名称
    private String attributeName;

    /***
	 * 属性ID集合
	 */
	private List<Integer> attributeIdList = new ArrayList<>();
	private List<Integer> attributeIdValueList = new ArrayList<>();

    private Date updateTime;

    private Date createTime;


    public List<Integer> getAttributeIdValueList() {
        return attributeIdValueList;
    }

    public void setAttributeIdValueList(List<Integer> attributeIdValueList) {
        this.attributeIdValueList = attributeIdValueList;
    }

    public List<Integer> getAttributeIdList() {
		return attributeIdList;
	}

	public void setAttributeIdList(List<Integer> attributeIdList) {
		this.attributeIdList = attributeIdList;
	}

	public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
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
		return "AttributeValue [valueId=" + valueId + ", value=" + value + ", attributeId=" + attributeId + ", status="
				+ status + ", updateTime=" + updateTime + ", createTime=" + createTime + "]";
	}

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}