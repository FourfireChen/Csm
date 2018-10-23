package com.chuansongmen.common;

public interface ProgressListener extends StartListener {
    void onStart();

    void onFinish();
}
