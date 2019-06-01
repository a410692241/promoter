package com.linayi.entity.area;

import com.linayi.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class SmallCommunityFullName extends SmallCommunity{
    /**
     * 省市区街道小区全名
     */
    private String fullName;

}