package com.linayi.entity.correct;

import com.linayi.entity.BaseEntity;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.goods.GoodsSku;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Correct extends BaseEntity {
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    private String realName;
    private Long userName;

    public Long getUserName() {
        return userName;
    }

    public void setUserName(Long userName) {
        this.userName = userName;
    }

    /**
     * [主键]
     */
    private Long correctId;

    /**
     * [超市ID]
     */
    private Integer supermarketId;

    private String supermarkerName;


    public String getSupermarkerName() {
        return supermarkerName;
    }

    public void setSupermarkerName(String supermarkerName) {
        this.supermarkerName = supermarkerName;
    }

    private Supermarket supermarket;

    /**
     * [超市名]
     */
    private String name;

    /**
     * [商品ID]
     */
    private Long goodsSkuId;

    /**
     * [商品名]
     */
    private String fullName;

    /**
     * [商品图片]
     */
    private String goodsImage;

    private GoodsSku goodsSku;

    /**
     * [价格]单位：分
     */
    private Integer price;

    /**
     * [价格生效时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * [价格过期时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private String strStartTime;

    private String strEndTime;

    /*创建开始时间*/
    private String createTimeStart;
    /*创建结束时间*/
    private String createTimeEnd;

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    /**
     * [用户ID]
     */
    private Integer userId;

    /**
     * [用户类型]
     */
    private String userType;

    /**
     * [纠错父ID]
     */
    private Long parentId;

    /**
     * [图片路径]
     */
    private String image;

    /**
     * [上级纠错或者分享的图片]
     */
    private String parentImage;

    /**
     * [价格类型] 正常价：NORMAL  促销价：PROMOTION  处理价：DEAL  会员价：MEMBER
     */
    private String priceType;


    private String priceTypeForCha;

    /**
     * [状态] 待审核：WAIT_AUDIT  审核通过：AUDIT_SUCCESS  终止生效：TERMINATED  审核不通过：AUDIT_FAIL  已生效：AFFECTED   已过期：EXPIRED
     */
    private String status;

    /**
     * [价格实际生效时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date actualStartTime;

    /**
     * [价格实际过期时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date actualEndTime;

    public String getGoodsSkuName() {
        return goodsSkuName;
    }

    public void setGoodsSkuName(String goodsSkuName) {
        this.goodsSkuName = goodsSkuName;
    }

    private String goodsSkuName;

    /**
     * [类型] 分享：SHARE  纠错：CORRECT
     */
    private String type;

    /**
     * [更新时间]
     */
    private Date updateTime;

    /**
     * [创建时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private AdminAccount adminAccount;

    /**
     * 纠错历史列表按钮类型
     */
    private String historyButtonType;

    private User user;

    private String nickName;

    private String mobile;

    private String isRecall;

    private static final long serialVersionUID = 1L;

    private List<String> statusList;

    private String correctType;//按钮类型

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public GoodsSku getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(GoodsSku goodsSku) {
        this.goodsSku = goodsSku;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Supermarket getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(Supermarket supermarket) {
        this.supermarket = supermarket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCorrectId() {
        return correctId;
    }

    public void setCorrectId(Long correctId) {
        this.correctId = correctId;
    }

    public Integer getSupermarketId() {
        return supermarketId;
    }

    public void setSupermarketId(Integer supermarketId) {
        this.supermarketId = supermarketId;
    }

    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }


    public String getPriceTypeForCha() {
        return priceTypeForCha;
    }

    public void setPriceTypeForCha(String priceTypeForCha) {
        this.priceTypeForCha = priceTypeForCha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getHistoryButtonType() {
        return historyButtonType;
    }

    public void setHistoryButtonType(String historyButtonType) {
        this.historyButtonType = historyButtonType;
    }

    public String getParentImage() {
        return parentImage;
    }

    public void setParentImage(String parentImage) {
        this.parentImage = parentImage;
    }

    public String getIsRecall() {
        return isRecall;
    }

    public void setIsRecall(String isRecall) {
        this.isRecall = isRecall;
    }


    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public String getCorrectType() {
        return correctType;
    }

    public void setCorrectType(String correctType) {
        this.correctType = correctType;
    }

    @Override
    public String toString() {
        return "Correct{" +
                "correctId=" + correctId +
                ", supermarketId=" + supermarketId +
                ", supermarket=" + supermarket +
                ", goodsSkuId=" + goodsSkuId +
                ", goodsSku=" + goodsSku +
                ", price=" + price +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", image='" + image + '\'' +
                ", parentImage='" + parentImage + '\'' +
                ", priceType='" + priceType + '\'' +
                ", status='" + status + '\'' +
                ", actualStartTime=" + actualStartTime +
                ", actualEndTime=" + actualEndTime +
                ", type='" + type + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", historyButtonType='" + historyButtonType + '\'' +
                ", user=" + user +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public Correct() {
        super();
    }

    public AdminAccount getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(AdminAccount adminAccount) {
        this.adminAccount = adminAccount;
    }
}