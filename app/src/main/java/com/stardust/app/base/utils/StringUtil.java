package com.stardust.app.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by haohua on 2016/12/8.
 */

public class StringUtil {

    public static final long SIZE_KB = 1024L;
    public static final long SIZE_MB = 1024 * 1024L;
    public static final long SIZE_GB = 1024L * 1024L * 1024L;

    public static final long TIME_SIZE_SECOND = 1000;
    public static final long TIME_SIZE_MIN =  60 * 1000;
    public static final long TIME_SIZAE_HOUR = 60 * 60 * 1000;

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

    public static String getSizeSting(long size) {

        if (size < SIZE_KB) {
            return size + "B";
        }

        if (size < SIZE_MB) {
            return Math.round(size * 100.0 / SIZE_KB) / 100.0 + "KB";
        }

        if (size < SIZE_GB) {
            return Math.round(size * 100.0 / SIZE_MB) / 100.0 + "MB";
        }

        return Math.round(size * 100.0 / SIZE_GB) / 100.0 + "G";

    }

    public static String getDurationToString(long time) {
        String duration = null;

        if (time < TIME_SIZE_MIN) {
            return Math.round(time * 100.0 / TIME_SIZE_SECOND) / 100 + "秒";
        }

        if (time < TIME_SIZAE_HOUR) {
            duration = time / TIME_SIZE_MIN + "分 " + time % TIME_SIZE_MIN / TIME_SIZE_SECOND + "秒";
            return duration;
        }
        duration = time / TIME_SIZAE_HOUR + "小时 " + time % TIME_SIZAE_HOUR + "分 " + time % TIME_SIZE_MIN / TIME_SIZE_SECOND + "秒";
        return duration;

    }
    /**
     * 获取字符串后几位数
     * @param content 字符串
     * @param num 后几位
     * */
    public static String getStringLast(String content, int num) {
        String string = "";
        if (num > content.length()) return content;
        string = content.substring(content.length() - num, content.length());
        return string;
    }
    /**
     * 随机生成N个汉字的字符串
     * */
    public String getRandomChar(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

    /**
     * 随机生成汉字
     * */
    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }

    /*判断字符串中是否仅包含字母数字和汉字
     *各种字符的unicode编码的范围：
    * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
    * 数字：[0x30,0x39]（或十进制[48, 57]）
    *小写字母：[0x61,0x7a]（或十进制[97, 122]）
    * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
*/
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    /*判断字符串中是否仅包含字母数字和汉字
      *各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     *小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
*/
    public static boolean isLetterOrDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    /*判断字符串中是否包含字母数字和汉字
   *各种字符的unicode编码的范围：
  * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
  * 数字：[0x30,0x39]（或十进制[48, 57]）
  *小写字母：[0x61,0x7a]（或十进制[97, 122]）
  * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
*/
    public static boolean isLetterAndDigit(String str) {
        String regex1 = ".*[a-zA-Z]+.*";
        String regex2 = ".*[0-9]+.*";
        return str.matches(regex1)&&str.matches(regex2);
    }
}
