package com.stardust.app.base.utils;

import android.util.Log;

public class Debug {
    private static String TAG = "aaa";
    private static boolean showLog = true;
    public static void initDebug(String tag) {
        if (tag != null && !tag.trim().equals("")) {
            TAG = tag;
        }
    }
    public static void initDebug(String tag, boolean isShowLog) {
        if (tag != null && !tag.trim().equals("")) {
            TAG = tag;
        }
        showLog = isShowLog;
    }
	// 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制  
    // 所以这里使用自己分节的方式来输出足够长度的message  
    public static void show(String str) {
        if (!showLog) {
            return;
        }
//        str = str.trim();
//        int index = 0;
//        int maxLength = 4000;
//        String sub;
//        while (index < str.length()) {
//            // java的字符不允许指定超过总的长度end
//            if (str.length() <= index + maxLength) {
//                sub = str.substring(index);
//            } else {
//                sub = str.substring(index, index+maxLength);
//            }
//
//            index += maxLength;
//            Log.i(TAG, sub);
//        }
        int p = 2048;
        long length = str.length();
        if (length < p || length == p)
            Log.i(TAG, str);
        else {
            while (str.length() > p) {
                String logContent = str.substring(0, p);
                str = str.replace(logContent, "");
                Log.i(TAG, logContent);
            }
            Log.i(TAG,str);
        }
    }

    public static void e(String str) {
        if (!showLog) {
            return;
        }
//        str = str.trim();
//        int index = 0;
//        int maxLength = 4000;
//        String sub;
//        while (index < str.length()) {
//            // java的字符不允许指定超过总的长度end
//            if (str.length() <= index + maxLength) {
//                sub = str.substring(index);
//            } else {
//                sub = str.substring(index, index+maxLength);
//            }
//
//            index += maxLength;
//            Log.e(TAG, sub);
//        }
        int p = 2048;
        long length = str.length();
        if (length < p || length == p)
            Log.e(TAG, str);
        else {
            while (str.length() > p) {
                String logContent = str.substring(0, p);
                str = str.replace(logContent, "");
                Log.e(TAG, logContent);
            }
            Log.e(TAG,str);
        }
    }
}
