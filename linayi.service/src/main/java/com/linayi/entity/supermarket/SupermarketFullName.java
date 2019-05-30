package com.linayi.entity.supermarket;

import com.linayi.entity.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Data
public class SupermarketFullName extends Supermarket {

    /**
     * 省市区街道超市全名
     */
    private String fullName;
}
