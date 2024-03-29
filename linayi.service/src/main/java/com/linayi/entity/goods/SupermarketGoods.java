package com.linayi.entity.goods;

import com.linayi.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class SupermarketGoods extends BaseEntity implements Serializable{
    /**
	 *
	 */
	private static final long serialVersionUID = -4988704763900697034L;

	private Long supermarketGoodsId;

    private Integer supermarketId;

    private Long goodsSkuId;

    private Integer price;

    private Long correctId;

    private String supermarketName; //超市名

    private String correctType;//按钮类型

    private String name;

	private String fullName;

    private String status;

    private String priceStr;
	//距离排序
    private Integer rank;

	private String barcode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private List<Integer> supermarketIdList;

	public List<Integer> getSupermarketIdList() {
		return supermarketIdList;
	}

	public void setSupermarketIdList(List<Integer> supermarketIdList) {
		this.supermarketIdList = supermarketIdList;
	}

	public String getSupermarketName() {
		return supermarketName;
	}

	public String getCorrectType() {
		return correctType;
	}

	public void setCorrectType(String correctType) {
		this.correctType = correctType;
	}

	public void setSupermarketName(String supermarketName) {
		this.supermarketName = supermarketName;
	}

	public Long getSupermarketGoodsId() {
        return supermarketGoodsId;
    }

    public void setSupermarketGoodsId(Long supermarketGoodsId) {
        this.supermarketGoodsId = supermarketGoodsId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getCorrectId() {
        return correctId;
    }

    public void setCorrectId(Long correctId) {
        this.correctId = correctId;
    }

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
