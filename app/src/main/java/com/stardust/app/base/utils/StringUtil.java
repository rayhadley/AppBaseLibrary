package com.stardust.app.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * Created by haohua on 2016/12/8.
 */

public class StringUtil {
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * UTF-8编码
     * */
    public static String encodeString(String str) {
        try {

            str = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * UTF-8解码
     * */
    public static String decodeStringUTF8(String str) {
        String string = "";
        try {
            string = URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            string = "decode error";
        }
        return string;

    }


    public static String  formatDecimal(double value) {
        DecimalFormat df=new   DecimalFormat("#.##");
        return  df.format(value);
    }

    public static String  formatDecimal(String value) {
        if (value == null || value.trim().equals("")) return "0";
        double tmp = Double.parseDouble(value);
        DecimalFormat df=new   DecimalFormat("#.##");
        return  df.format(tmp);
    }

    /**
     * 对String按照一定的长度增加空格
     * @param length 增加空格的位置
     * @param content 内容
     * @return
     */
    public static String addSpaceToContent(int length, String content) {
        String tmp = "";
        int i = 0;
        int count = 1;
        while (i < content.length()) {
            tmp = tmp + content.charAt(i);
            if (count % length == 0) {
                tmp = tmp + "  ";
            }
            i++;
            count++;
        }
        System.out.println("tmp:" + tmp);
        return tmp;
    }
}
