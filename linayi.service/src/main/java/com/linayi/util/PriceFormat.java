package com.linayi.util;

public class PriceFormat {

    /**
     * 分转元为单位
     *
     * @param Price
     * @return
     */
    public static String getpriceString(Integer Price) {
        StringBuffer sb = new StringBuffer(Price + "");
        if (sb.length() >= 3) {
            sb.insert(sb.toString().length() - 2, ".");
        } else if (sb.length() == 2) {
            return "0." + sb.toString();
        } else if (sb.length() == 1) {
            return "0.0" + sb.toString();
        }
        return sb.toString();
    }
}
