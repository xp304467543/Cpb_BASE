package com.customer.component.download;

import android.widget.TextView;

/**
 * @Description: 下载进度回调
 */
public interface DownloadListener {

    void onStartDownload(TextView textView);

    void onProgress(long downloaded, long total, TextView textView);

    void onPauseDownload(TextView textView);

    void onCancelDownload(TextView textView);

    void onFinishDownload(String savedFile, TextView textView);

    void onFail(String errorInfo, TextView textView);
}
