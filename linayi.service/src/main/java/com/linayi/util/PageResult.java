package com.linayi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.linayi.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value = "分页实体类", description = "这是分页结果对象")
public class PageResult<T> extends  BaseEntity{
	@ApiModelProperty(name = "total",value = "总数量", required = false, example = "10")
	private Integer total;
	@ApiModelProperty(hidden = true)
	private List<T> rows;	//后台封装数据属性 list of this page
	@ApiModelProperty(value = "封装集合的对象")
	private List<T> data;	// 用户封装数据属性list of this page
	@ApiModelProperty(name = "totalPage",value = "总页数", required = false, example = "10")
	private Integer totalPage; // 总页数

	public PageResult() {
	}

	public PageResult(List<T> rows, int total) {
		this.rows = rows;
		this.total = total;
	}


	public PageResult(List<T> data, BaseEntity baseEntity) {
		this.data = data;
		Integer total = baseEntity.getTotal();
		Integer pageSize = baseEntity.getPageSize();
		if ((total != null && pageSize != null) && (total > 0 && pageSize > 0)){
			totalPage = (total + 1) / pageSize == 0 ? 1 : ((total + 1) / pageSize + 1);
		}
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
	}
}
