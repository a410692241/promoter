package com.linayi.entity.api;

import java.io.Serializable;
import java.util.Date;

/**
 * api_logs
 * @author 
 */
public class ApiLogs implements Serializable {
    private Integer logId;

    /**
     * [请求url]
     */
    private String requestUrl;

    /**
     * [请求时间]
     */
    private Date requestTime;

    /**
     * [请求头]
     */
    private String requestHead;

    /**
     * [请求体]
     */
    private String requestBody;

    /**
     * [错误信息]
     */
    private String errorMsg;

    /**
     * [响应体]
     */
    private String responseBody;

    private static final long serialVersionUID = 1L;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestHead() {
        return requestHead;
    }

    public void setRequestHead(String requestHead) {
        this.requestHead = requestHead;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApiLogs other = (ApiLogs) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
            && (this.getRequestUrl() == null ? other.getRequestUrl() == null : this.getRequestUrl().equals(other.getRequestUrl()))
            && (this.getRequestTime() == null ? other.getRequestTime() == null : this.getRequestTime().equals(other.getRequestTime()))
            && (this.getRequestHead() == null ? other.getRequestHead() == null : this.getRequestHead().equals(other.getRequestHead()))
            && (this.getRequestBody() == null ? other.getRequestBody() == null : this.getRequestBody().equals(other.getRequestBody()))
            && (this.getErrorMsg() == null ? other.getErrorMsg() == null : this.getErrorMsg().equals(other.getErrorMsg()))
            && (this.getResponseBody() == null ? other.getResponseBody() == null : this.getResponseBody().equals(other.getResponseBody()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getRequestUrl() == null) ? 0 : getRequestUrl().hashCode());
        result = prime * result + ((getRequestTime() == null) ? 0 : getRequestTime().hashCode());
        result = prime * result + ((getRequestHead() == null) ? 0 : getRequestHead().hashCode());
        result = prime * result + ((getRequestBody() == null) ? 0 : getRequestBody().hashCode());
        result = prime * result + ((getErrorMsg() == null) ? 0 : getErrorMsg().hashCode());
        result = prime * result + ((getResponseBody() == null) ? 0 : getResponseBody().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", requestUrl=").append(requestUrl);
        sb.append(", requestTime=").append(requestTime);
        sb.append(", requestHead=").append(requestHead);
        sb.append(", requestBody=").append(requestBody);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", responseBody=").append(responseBody);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}