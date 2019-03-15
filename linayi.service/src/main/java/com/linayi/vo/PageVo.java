package com.linayi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
//分页参数
public class PageVo {
    @ApiModelProperty(value = "页的大小",required = true,example = "10")
    public Integer pageSize;
    @ApiModelProperty(value = "当前页",required = true,example = "1")
    public Integer currentPage;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
    
    
}
