package com.chuansongmen.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenOnReceiver extends BroadcastReceiver {
    private static final String TAG = "ScreenOnReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 解锁屏幕");
        Intent stopFore = new Intent(context, PositionServiceFore.class);
        context.stopService(stopFore);
    }
}
