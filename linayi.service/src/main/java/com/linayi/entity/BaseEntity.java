package com.linayi.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.linayi.exception.ErrorType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分页基础类")
public class BaseEntity {
	
	//页大小
	@ApiModelProperty(hidden = true)
	private Integer pageSize;

	//当前页
	@ApiModelProperty(hidden = true)
	private Integer currentPage;

	//总记录数
	@ApiModelProperty(hidden = true)
	private Integer total;

	// S：成功     F：失败
	@ApiModelProperty(hidden = true)
	private String respCode;

	/**
	 * 只有respCode=F时，errorType才会有值
	 */
	@ApiModelProperty(hidden = true)
	private String errorType;


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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}


	/**
	 * 设置返回错误信息
	 * @param errorMsg
	 * @return
	 */
	public BaseEntity setErrorMsg(ErrorType errorMsg){
		this.errorType = errorMsg.getErrorMsg();
		this.respCode = "F";
		return this;
	}

	/**
	 * 设置返回错误信息
	 * @param errorMsg
	 * @param respCode    S：成功     F：失败
	 * @return
	 */
	public BaseEntity setErrorMsg(ErrorType errorMsg, String respCode){
		this.errorType = errorMsg.getErrorMsg();
		this.respCode = respCode;
		return this;
	}
}
