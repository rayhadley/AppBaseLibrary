package com.stardust.app.base.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.stardust.app.base.common.AppEnum;

import java.io.File;

/**
 * Created by bestw on 2017/10/23.
 * 手机媒体库 操作 工具类
 */

public class MediaDbUtils {

    private static Context mContext;
    private static MediaDbUtils instance;

    public static synchronized MediaDbUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new MediaDbUtils();
        }
        return instance;
    }

    private MediaDbUtils() {

    }

    /**
     * 媒体库 4.4以前的方法(包括 删除 更新 新增)
     * */
    public void updateMediaDBOld() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
        intent.setData(Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath()));
        mContext.sendBroadcast(intent); //发送一个广播，让系统更新媒体数据库，也适用于删除媒体文件；
    }

    /**
     * 添加到媒体库 4.4以后的方法
     * */
    public void addToMediaDB(String filePath) {
        MediaScannerConnection.scanFile(mContext, new String[] {filePath}, null,null);
    }
    /**
     * 添加到媒体库 4.4以后的方法
     * */
    public void addToMediaDB2(String filePath) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new File(filePath)));
        mContext.sendBroadcast(intent);
    }

    public void deleteFromMediaDB(String filePath) {
        switch (getMediaFileType(filePath)) {
            case IMAGE:
                deleteImageFromMediaDB(filePath);
                break;
            case AUDIO:
                deleteAudioFromMediaDB(filePath);
                break;
            case VIDEO:
                deleteVedioFromMediaDB(filePath);
                break;
        }
    }

    /**
     * 从媒体库删除音频 4.4以后的方法
     * */
    private void deleteAudioFromMediaDB(String filePath) {
        mContext.getContentResolver().delete(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Audio.Media.DATA+ " = '" + filePath + "'", null);
    }
    /**
     * 从媒体库删除tup 4.4以后的方法
     * */
    private void deleteImageFromMediaDB(String filePath) {
        mContext.getContentResolver().delete(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.Media.DATA+ " = '" + filePath + "'", null);
    }
    /**
     * 从媒体库删除视频 4.4以后的方法
     * */
    private void deleteVedioFromMediaDB(String filePath) {
        mContext.getContentResolver().delete(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Video.Media.DATA+ " = '" + filePath + "'", null);
    }


    private AppEnum.MediaType getMediaFileType(String filePath) {
        String fileName = filePath.toLowerCase();
        AppEnum.MediaType mediaType = AppEnum.MediaType.OTHER;
        if (fileName.endsWith("jpg") || fileName.endsWith("jpeg") || fileName.endsWith("png") || fileName.endsWith("gif")) {
            mediaType = AppEnum.MediaType.IMAGE;
        } else if (fileName.endsWith("wmv") || fileName.endsWith("avi") || fileName.endsWith("mp4")) {
            mediaType = AppEnum.MediaType.VIDEO;
        } else if (fileName.endsWith("mp3") || fileName.endsWith("ogg") || fileName.endsWith("amr") || fileName.endsWith("wav") || fileName.endsWith("aac") || fileName.endsWith("flac")) {
            mediaType = AppEnum.MediaType.AUDIO;
        }
        return mediaType;
    }

}
