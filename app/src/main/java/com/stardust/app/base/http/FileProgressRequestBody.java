package com.stardust.app.base.http;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.internal.Util;
import com.stardust.app.base.utils.L;

import java.io.File;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;
/**
 * 带进度 上传文件请求
 * */
public class FileProgressRequestBody extends RequestBody {

    private boolean mStop = false;

    public interface ProgressListener {
        void transferred( long size ,FileProgressRequestBody requestBody);
    }

    public static final int SEGMENT_SIZE = 2*1024; // okio.Segment.SIZE

    protected File file;
    protected ProgressListener listener;
    protected String contentType;


    public void setStop(boolean stop) {
        mStop = stop;
    }

    public FileProgressRequestBody(File file, String contentType, ProgressListener listener) {
        this.file = file;
        this.contentType = contentType;
        this.listener = listener;
    }

    protected FileProgressRequestBody() {}

    @Override
    public long contentLength() {
        return file.length();
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(file);
            long total = 0;
            long read;

            while ((read = source.read(sink.buffer(), SEGMENT_SIZE)) != -1) {
                L.show("上传文件中.......................................................................");
                if (mStop) {
                    L.show("=======================================实现了 停止===============================================");
                    break;
                }
                total += read;
                sink.flush();
                this.listener.transferred(total, this);


            }
        } finally {
            Util.closeQuietly(source);
        }
    }

}
