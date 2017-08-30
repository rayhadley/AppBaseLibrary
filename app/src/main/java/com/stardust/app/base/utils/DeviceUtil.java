package com.stardust.app.base.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by haohua on 2016/12/12.
 */

public class DeviceUtil {
    /**
     * 得到本机ip地址
     * */
    public static String getLocalHostIp()
    {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }

    /**
     * 得到本机Mac地址
     * */
    public static String getLocalMac(Context context)
    {
        String mac = "";
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        mac = "本机的mac地址是：" + wifiInfor.getMacAddress();
        return wifiInfor.getMacAddress();
    }

    /**
     * 得到本机IMEI
     * */
    public static String getIMEI(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 复制内容到剪贴板
     * */
    public static void copyText(Context context, String content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("content", content);
        clipboard.setPrimaryClip(clip);
    }
    /**
     * 从剪贴板获取内容
     * */
    public static String getCopyText(Context context) {
        String resultString = "";
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (!clipboard.hasPrimaryClip()) {
            Toast.makeText(context,
                    "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            ClipData clipData = clipboard.getPrimaryClip();
            int count = clipData.getItemCount();
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(context);
                resultString += str;
            }

        }
        return resultString;
    }

    /**
     * 获取手机品牌（Meizu）
     *
     * */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号（m1 note）
     *
     * */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取android系统版本（Android 7.0）
     *
     * */
    public static String getPhoneVerson() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取android系统 定制商版本号（Flyme 5.1.8.0A）
     *
     * */
    public static String getPhoneRomVerson() {
        return "Android " + Build.DISPLAY;
    }

    public static PowerManager.WakeLock wakeLock;
    /**
     * 取消屏保
     * */
    public static void acquireWakeLock(Context context) {
        if (wakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, context.getClass().getCanonicalName());
            wakeLock.acquire();
        }

    }

    /**
     * 释放屏保锁定
     * */
    public static void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }

    }

    /**
     * 调用系统相机拍照 指定存储路径
     * */
    public static void takePic(Context context, int action, File saveFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(imagePath, Utils.getCurrentDate().replaceAll("[-:]", "").replace(" ", "") + ".jpg");
//        imageName = file.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
//        context.startActivityForResult(intent, action);
        ((Activity)context).startActivityForResult(intent, action);
    }

    /**
     * 调用系统相机拍照(连拍) 指定存储路径
     * */
    public static void takePicContinue(Context context, int action) {
        Intent intentPic = new Intent();
        intentPic.setAction("android.media.action.STILL_IMAGE_CAMERA");
        ((Activity)context).startActivityForResult(intentPic, action);
    }

    /**
     * 调用系统相机录视频 指定存储路径
     * */
    public static void takeVideo(Context context, int action, File saveFile) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        File file = new File(imagePath, Utils.getCurrentDate().replaceAll("[-:]", "").replace(" ", "") + ".jpg");
//        imageName = file.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);            //设置拍摄的质量
//        context.startActivityForResult(intent, action);
        ((Activity)context).startActivityForResult(intent, action);
    }
    /**
     * 调用系统录音 指定存储路径
     * */
    public static void takeAudio(Context context, int action, File saveFile) {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
        ((Activity)context).startActivityForResult(intent, action);
    }

    /***
     * 拨号
     * */
    public static void phoneCall(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
