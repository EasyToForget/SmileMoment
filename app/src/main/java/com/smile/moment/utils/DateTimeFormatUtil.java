package com.smile.moment.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatUtil {

    /**
     * 时间字符串转换成时间戳
     *
     * @param date 时间字符串，如2015-01-01 12:00:00
     */
    public static long string2longSecondByFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date dt = sdf.parse(date);
            return dt.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    /**
     * 时间戳转换成String
     *
     * @param time 时间戳
     */
    public static String long2stringByFormatForZh(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        Date dt = new Date(time);
        return sdf.format(dt);
    }

}
