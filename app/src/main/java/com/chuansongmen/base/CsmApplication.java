package com.chuansongmen.base;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class CsmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "c9725040bc", true);
    }
}
