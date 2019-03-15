package com.linayi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd";

    public final static String Y_M_D_H_M_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 把Date类型按指定格式转字符串默认yyyy-MM-dd
     * @param date
     * @param pattern
     * @return
     */
    public static String date2String(Date date, String pattern){
        String format = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CheckUtil.isNotNullEmpty(pattern) ? pattern : DEFAULT_PATTERN);
            if (date == null){
                return null;
            }
            format = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 把String类型按指定格式转Date默认yyyy-MM-dd
     * @param format
     * @param pattern
     * @return
     */
    public static Date string2Date(String format, String pattern){
        Date parse = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CheckUtil.isNotNullEmpty(pattern) ? pattern : DEFAULT_PATTERN);
            if (format != null && !"".equals(format.trim())){
                parse = sdf.parse(format);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }



}
