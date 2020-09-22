package com.hospital_care.hospitalcare.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrDateSDF {

    //String时间转Date转String
    public static String strToDate(String time) throws ParseException {
        Date d = null;
        SimpleDateFormat sdf = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            d = format.parse(time);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
        return sdf.format(d);
    }

    //下划线转驼峰
    public static String underlineToHump(String underlineName) {
        //截取下划线分成数组
        char[] charArray = underlineName.toCharArray();
        //判断上次循环的字符是否是"_"
        boolean underlineBefore = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0,l = charArray.length; i < l; i++) {
            //判断当前字符是否是"_",如果跳出本次循环
            if (charArray[i] == 95) {
                underlineBefore = true;
            } else if (underlineBefore) {
                //如果为true，代表上次的字符是"_",当前字符需要转成大写
                buffer.append(charArray[i] -= 32);
                underlineBefore = false;
            } else { //不是"_"后的字符就直接追加
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }
}
