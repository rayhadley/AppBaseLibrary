package com.stardust.app.base.http.progress;

//import okhttp3.Request;

import com.squareup.okhttp.Request;
//
//import okhttp3.Request;

public interface RequestFilter {
    boolean listensFor(Request request);
}
