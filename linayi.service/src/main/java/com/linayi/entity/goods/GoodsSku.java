package com.linayi.entity.goods;

import com.linayi.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class GoodsSku extends BaseEntity {
    private Long goodsSkuId;
    /*商品名*/
    private String name;

    private String fullName;

    /*商品条形码*/
    private String barcode;

    private Integer categoryId;

    private Integer brandId;

    private String status;

    private String image;

    private Date updateTime;

    private Date createTime;

    private String model;
    //创建者账号
    private String userName;

    private String function;

    private String produceAddress;

    /**商品数量*/
    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date produceDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validDate;

    private String manufacturer;

    private String otherAttribute;
    /*创建开始时间*/
    private String createTimeStart;
    /*创建结束时间*/
    private String createTimeEnd;
    /* 品牌名*/
    private String brandName;
    /*类名*/
    private String categoryName;
    /*所有规格的值*/
    private String attrValues;

    private String minSupermarket;

    private Integer minPrice;

    private String maxSupermarket;

    private Integer maxPrice;

    private Integer maxSupermarketId; //最高价超市id

    private Integer minSupermarketId; //最低价超市id

    private Integer minSupermarketIdNormal; //普通会员最低价超市id

    private Integer minPriceNormal; //普通会员最低价

    private Integer maxSupermarketIdNormal; //普通会员最高价超市id

    private Integer maxPriceNormal; //普通会员最高价

    private Integer minSupermarketIdSenior; //高级会员最低价超市id

    private Integer minPriceSenior; //高级会员最低价

    private Integer maxSupermarketIdSenior; //高级会员最高价超市id

    private Integer maxPriceSenior; //高级会员最高价

    private Integer minSupermarketIdSuper; //超级vip最低价超市id

    private Integer minPriceSuper; //超级vip最低价

    private Integer maxSupermarketIdSuper; //超级vip最高价超市id

    private Integer maxPriceSuper; //超级vip最高价

    private Double spreadRate;        //差价率

    private String minSupermarketName;

    private String minPriceString; //最低价String

    private List<SupermarketGoods> supermarketGoodsList;//所有有该商品的超市

    private String spreadRateString; // 差价率0.12%

    private Integer userId; //用户id

    private Integer communityId; //社区id

    private String  valueName; //规格名

    private String  createName; //新增商品用户名

    private String memberLevel;

    private Integer soldNum; //销售数量

    private  String isRecommend; //是否是推荐商品

    private String orderBy; //按什么排序

    private String correctType; //纠错类型

    private Integer supermarketId; //超市id

    private String goodsPrice;//商品价格

    private Long correctId; //纠错id

    private Integer clickNum;

    /**
     * [开始时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * [结束时间]
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
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

    public String getCorrectType() {
        return correctType;
    }

    public void setCorrectType(String correctType) {
        this.correctType = correctType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    private  List<Long> goodsSkuIdList; //用于存放goodsSkuId集合

    public List<Long> getGoodsSkuIdList() {
        return goodsSkuIdList;
    }

    public void setGoodsSkuIdList(List<Long> goodsSkuIdList) {
        this.goodsSkuIdList = goodsSkuIdList;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getEstablishName() {
        return establishName;
    }

    public void setEstablishName(String establishName) {
        this.establishName = establishName;
    }

    private String establishName;

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getSpreadRate() {
		return spreadRate;
	}

	public void setSpreadRate(Double spreadRate) {
		this.spreadRate = spreadRate;
	}

	public Integer getMaxSupermarketId() {
		return maxSupermarketId;
	}

	public void setMaxSupermarketId(Integer maxSupermarketId) {
		this.maxSupermarketId = maxSupermarketId;
	}

	public Integer getMinSupermarketId() {
		return minSupermarketId;
	}

	public void setMinSupermarketId(Integer minSupermarketId) {
		this.minSupermarketId = minSupermarketId;
	}

	public String getMaxSupermarket() {
		return maxSupermarket;
	}

	public void setMaxSupermarket(String maxSupermarket) {
		this.maxSupermarket = maxSupermarket;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinSupermarket() {
		return minSupermarket;
	}

	public void setMinSupermarket(String minSupermarket) {
		this.minSupermarket = minSupermarket;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
    }

    public String getProduceAddress() {
        return produceAddress;
    }

    public void setProduceAddress(String produceAddress) {
        this.produceAddress = produceAddress == null ? null : produceAddress.trim();
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getOtherAttribute() {
        return otherAttribute;
    }

    public void setOtherAttribute(String otherAttribute) {
        this.otherAttribute = otherAttribute == null ? null : otherAttribute.trim();
    }

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }

    @Override
    public String toString() {
        return "GoodsSku{" +
                "goodsSkuId=" + goodsSkuId +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", barcode='" + barcode + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", model='" + model + '\'' +
                ", function='" + function + '\'' +
                ", produceAddress='" + produceAddress + '\'' +
                ", quantity=" + quantity +
                ", produceDate=" + produceDate +
                ", validDate=" + validDate +
                ", manufacturer='" + manufacturer + '\'' +
                ", otherAttribute='" + otherAttribute + '\'' +
                ", createTimeStart='" + createTimeStart + '\'' +
                ", createTimeEnd='" + createTimeEnd + '\'' +
                ", brandName='" + brandName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", attrValues='" + attrValues + '\'' +
                ", minSupermarket='" + minSupermarket + '\'' +
                ", minPrice=" + minPrice +
                ", maxSupermarket='" + maxSupermarket + '\'' +
                ", maxPrice=" + maxPrice +
                ", maxSupermarketId=" + maxSupermarketId +
                ", minSupermarketId=" + minSupermarketId +
                ", spreadRate=" + spreadRate +
                ", minSupermarketName='" + minSupermarketName + '\'' +
                ", minPriceString='" + minPriceString + '\'' +
                ", supermarketGoodsList=" + supermarketGoodsList +
                ", spreadRateString='" + spreadRateString + '\'' +
                ", userId=" + userId +
                ", communityId=" + communityId +
                ", valueName='" + valueName + '\'' +
                ", establishName='" + establishName + '\'' +
                '}';
    }

    public String getMinSupermarketName() {
        return minSupermarketName;
    }

    public void setMinSupermarketName(String minSupermarketName) {
        this.minSupermarketName = minSupermarketName;
    }

    public String getMinPriceString() {
        return minPriceString;
    }

    public void setMinPriceString(String minPriceString) {
        this.minPriceString = minPriceString;
    }

    public String getSpreadRateString() {
        return spreadRateString;
    }

    public void setSpreadRateString(String spreadRateString) {
        this.spreadRateString = spreadRateString;
    }

    public List<SupermarketGoods> getSupermarketGoodsList() {
        return supermarketGoodsList;
    }

    public void setSupermarketGoodsList(List<SupermarketGoods> supermarketGoodsList) {
        this.supermarketGoodsList = supermarketGoodsList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMinSupermarketIdNormal() {
        return minSupermarketIdNormal;
    }

    public void setMinSupermarketIdNormal(Integer minSupermarketIdNormal) {
        this.minSupermarketIdNormal = minSupermarketIdNormal;
    }

    public Integer getMinPriceNormal() {
        return minPriceNormal;
    }

    public void setMinPriceNormal(Integer minPriceNormal) {
        this.minPriceNormal = minPriceNormal;
    }

    public Integer getMaxSupermarketIdNormal() {
        return maxSupermarketIdNormal;
    }

    public void setMaxSupermarketIdNormal(Integer maxSupermarketIdNormal) {
        this.maxSupermarketIdNormal = maxSupermarketIdNormal;
    }

    public Integer getMaxPriceNormal() {
        return maxPriceNormal;
    }

    public void setMaxPriceNormal(Integer maxPriceNormal) {
        this.maxPriceNormal = maxPriceNormal;
    }

    public Integer getMinSupermarketIdSenior() {
        return minSupermarketIdSenior;
    }

    public void setMinSupermarketIdSenior(Integer minSupermarketIdSenior) {
        this.minSupermarketIdSenior = minSupermarketIdSenior;
    }

    public Integer getMinPriceSenior() {
        return minPriceSenior;
    }

    public void setMinPriceSenior(Integer minPriceSenior) {
        this.minPriceSenior = minPriceSenior;
    }

    public Integer getMaxSupermarketIdSenior() {
        return maxSupermarketIdSenior;
    }

    public void setMaxSupermarketIdSenior(Integer maxSupermarketIdSenior) {
        this.maxSupermarketIdSenior = maxSupermarketIdSenior;
    }

    public Integer getMaxPriceSenior() {
        return maxPriceSenior;
    }

    public void setMaxPriceSenior(Integer maxPriceSenior) {
        this.maxPriceSenior = maxPriceSenior;
    }

    public Integer getMinSupermarketIdSuper() {
        return minSupermarketIdSuper;
    }

    public void setMinSupermarketIdSuper(Integer minSupermarketIdSuper) {
        this.minSupermarketIdSuper = minSupermarketIdSuper;
    }

    public Integer getMinPriceSuper() {
        return minPriceSuper;
    }

    public void setMinPriceSuper(Integer minPriceSuper) {
        this.minPriceSuper = minPriceSuper;
    }

    public Integer getMaxSupermarketIdSuper() {
        return maxSupermarketIdSuper;
    }

    public void setMaxSupermarketIdSuper(Integer maxSupermarketIdSuper) {
        this.maxSupermarketIdSuper = maxSupermarketIdSuper;
    }

    public Integer getMaxPriceSuper() {
        return maxPriceSuper;
    }

    public void setMaxPriceSuper(Integer maxPriceSuper) {
        this.maxPriceSuper = maxPriceSuper;
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

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }
}
