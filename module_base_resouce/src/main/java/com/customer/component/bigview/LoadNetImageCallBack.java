package com.customer.component.bigview;

public interface LoadNetImageCallBack {
    void onStart();

    void onLoadSucceed();

    void onLoadFail(Exception e);

    void onLoadProgress(int progress);
}
