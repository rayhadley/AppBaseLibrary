package com.stardust.app.base.http.builder;

import com.stardust.app.base.http.OkHttpUtils;
import com.stardust.app.base.http.request.OtherRequest;
import com.stardust.app.base.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
