package com.stardust.app.base.http;


import okhttp3.Call;

/**
 * Created by haohua on 2017/3/26.
 */

public interface ProgressInterface {
    public void progress(long curSize, long totalSize, Call call);
}
