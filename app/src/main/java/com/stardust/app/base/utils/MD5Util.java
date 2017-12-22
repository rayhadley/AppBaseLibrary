package com.stardust.app.base.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by haohua on 2017/2/24.
 */

public class MD5Util {
    /**
     * 获取文件MD5值
     * */
    public static String getFileMd5(String filePath) {
        String fileMd5 = "";
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            BigInteger bigInt = new BigInteger(1, md.digest());
            fileMd5 = bigInt.toString(16);
            System.out.println("文件md5值：" + fileMd5);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileMd5;
    }

    /**
     * Encodes a string
     *
     * @param str
     *            String to encode
     * @return Encoded String
     * @throws Exception
     */
    public static String encodeString(String str) {
        try {
            Log.d("MD5:", str);
            MessageDigest messageDigest = null;
            String ensign = str;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(ensign.getBytes("UTF-8"));

            byte[] digest = messageDigest.digest();
            StringBuffer md5 = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                md5.append(Character.forDigit((digest[i] & 0xF0) >> 4, 16));
                md5.append(Character.forDigit((digest[i] & 0xF), 16));
            }
            return md5.toString().toLowerCase();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
