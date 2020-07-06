package com.stardust.app.base.http;

import com.squareup.okhttp.Call;

/**
 * Created by haohua on 2017/3/26.
 */

public interface ProgressInterface {
    public void progress(long curSize, long totalSize, Call call);
}
