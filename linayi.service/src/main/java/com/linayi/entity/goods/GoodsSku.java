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

    private Double spreadRate;        //差价率

    private String minSupermarketName;

    private String minPriceString; //最低价String

    private List<SupermarketGoods> supermarketGoodsList;//所有有该商品的超市

    private String spreadRateString; // 差价率0.12%
    
    private Integer userId; //用户id
    
    private Integer communityId; //社区id
    
    private String  valueName; //规格名
    private String  realName; //新增商品用户名


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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}