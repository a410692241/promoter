package com.linayi.util;

import java.text.DecimalFormat;

public class NumberUtil {

    /**
     *将Double四舍五入保留两位小数
     * @param num
     * @return
     */
    public static String formatDouble(Double num){
        DecimalFormat df   = new DecimalFormat("######0.00");
        return df.format(num);
    }
}
