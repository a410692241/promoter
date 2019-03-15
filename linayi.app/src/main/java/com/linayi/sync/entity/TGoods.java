package com.linayi.sync.entity;

import java.util.Date;

public class TGoods {
    private Integer id;

    private String name;

    private String marque;

    private String goodsFunction;

    private String placeOfOrigin;

    private String dateOfManufacture;

    private String termOfValidity;

    private String producer;

    private String otherAttributes;

    private String image;

    private String barCode;

    private String createTime;

    private Date updateTime;

    private Integer specificationsId;

    private Integer brandId;

    private String createName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque == null ? null : marque.trim();
    }

    public String getGoodsFunction() {
        return goodsFunction;
    }

    public void setGoodsFunction(String goodsFunction) {
        this.goodsFunction = goodsFunction == null ? null : goodsFunction.trim();
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin == null ? null : placeOfOrigin.trim();
    }

    public String getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(String dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture == null ? null : dateOfManufacture.trim();
    }

    public String getTermOfValidity() {
        return termOfValidity;
    }

    public void setTermOfValidity(String termOfValidity) {
        this.termOfValidity = termOfValidity == null ? null : termOfValidity.trim();
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer == null ? null : producer.trim();
    }

    public String getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(String otherAttributes) {
        this.otherAttributes = otherAttributes == null ? null : otherAttributes.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSpecificationsId() {
        return specificationsId;
    }

    public void setSpecificationsId(Integer specificationsId) {
        this.specificationsId = specificationsId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    @Override
    public String toString() {
        return "TGoods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marque='" + marque + '\'' +
                ", goodsFunction='" + goodsFunction + '\'' +
                ", placeOfOrigin='" + placeOfOrigin + '\'' +
                ", dateOfManufacture='" + dateOfManufacture + '\'' +
                ", termOfValidity='" + termOfValidity + '\'' +
                ", producer='" + producer + '\'' +
                ", otherAttributes='" + otherAttributes + '\'' +
                ", image='" + image + '\'' +
                ", barCode='" + barCode + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", specificationsId=" + specificationsId +
                ", brandId=" + brandId +
                ", createName='" + createName + '\'' +
                '}';
    }
}