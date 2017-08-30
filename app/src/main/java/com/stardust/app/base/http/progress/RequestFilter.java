package com.stardust.app.base.http.progress;

//import okhttp3.Request;

import com.squareup.okhttp.Request;

public interface RequestFilter {
    boolean listensFor(Request request);
}
