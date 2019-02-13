package com.chuansongmen.position;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PositionService extends Service {
    private static final String TAG = "PositionServiceBack";
    private PositionUploadReceiver positionUploadReceiver = new PositionUploadReceiver();
    private ScreenOffReceiver screenOffReceiver = new ScreenOffReceiver();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenReceiver();
        postPositionByReceiver();
    }

    /**
     * 这个方法是接收系统发送的每分钟修改时间的广播，来上传位置的
     * 但不好用，一旦锁屏了就没用了。。。
     */
    private void postPositionByReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(positionUploadReceiver, filter);
    }

    private void registerScreenReceiver() {
        IntentFilter screenOffFilter = new IntentFilter();
        screenOffFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenOffReceiver, screenOffFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(positionUploadReceiver);
        unregisterReceiver(screenOffReceiver);
    }
}
