package com.stardust.app.base.http.progress;

public interface ProgressListener {
    void onProgress(int progress);
    void progressIndeterminate();
}
