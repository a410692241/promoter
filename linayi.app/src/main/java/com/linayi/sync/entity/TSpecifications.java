package com.linayi.sync.entity;

public class TSpecifications {
    private Integer id;

    private String packing;

    private String color;

    private String capacity;

    private String weight;

    private String taste;

    private String material;

    private String pumpingNum;

    private String alcoholicStrength;

    private String salesAttributes;

    private String productAttributes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing == null ? null : packing.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity == null ? null : capacity.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste == null ? null : taste.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public String getPumpingNum() {
        return pumpingNum;
    }

    public void setPumpingNum(String pumpingNum) {
        this.pumpingNum = pumpingNum == null ? null : pumpingNum.trim();
    }

    public String getAlcoholicStrength() {
        return alcoholicStrength;
    }

    public void setAlcoholicStrength(String alcoholicStrength) {
        this.alcoholicStrength = alcoholicStrength == null ? null : alcoholicStrength.trim();
    }

    public String getSalesAttributes() {
        return salesAttributes;
    }

    public void setSalesAttributes(String salesAttributes) {
        this.salesAttributes = salesAttributes == null ? null : salesAttributes.trim();
    }

    public String getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(String productAttributes) {
        this.productAttributes = productAttributes == null ? null : productAttributes.trim();
    }
}