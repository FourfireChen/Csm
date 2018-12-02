package com.chuansongmen.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenOffReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenOffReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 锁屏");
        Intent foreService = new Intent(context, PositionServiceFore.class);
        context.startService(foreService);
    }
}
