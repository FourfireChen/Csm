package com.chuansongmen.common;

import android.widget.TextView;

public interface ProgressListener extends StartListener {
    void onStart();
    void onFinish();
}
