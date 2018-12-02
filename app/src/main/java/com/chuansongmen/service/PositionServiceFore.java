package com.chuansongmen.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.DataRepository;
import com.chuansongmen.data.IDataRepository;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class PositionServiceFore extends Service {
    private static final String TAG = "PositionServiceFore";
    private ScreenOnReceiver screenOnReceiver = new ScreenOnReceiver();
    private Timer positionUploadTimer;
    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenOnReceiver();
        positionUploadTimer = new Timer();
        positionUploadTimer.schedule(new PositionTimeTask(), 1000, 3000);
    }

    private void registerScreenOnReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        Intent startMain = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, startMain, 0))
                .setWhen(System.currentTimeMillis());
        startForeground(1217, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        positionUploadTimer.cancel();
        unregisterReceiver(screenOnReceiver);
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class PositionTimeTask extends TimerTask {

        private IDataRepository dataRepository = DataRepository.getInstance();
        private AMapLocationClient client = null;

        PositionTimeTask() {
            initClient(getApplicationContext());
            client.setLocationListener(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    if (aMapLocation.getErrorCode() == 0) {
                        client.stopLocation();
                        final Worker worker = Worker.getInstance();
                        final Position position = new Position(aMapLocation.getLongitude(),
                                aMapLocation.getLatitude());
                        Log.i(TAG,
                                "onLocationChanged: 经度" +
                                        position.getLongitude() +
                                        "  纬度" +
                                        position.getLatitude());
                        worker.setNow(position);
                        dataRepository.uploadPos(worker.getId(), position, new Callback<Boolean>() {
                            @Override
                            public void onResponse(Boolean result) {
                                if (result) {
                                    Log.i(TAG, "onResponse: 定位上传成功");
                                } else {
                                    Log.e(TAG, "onResponse: 定位上传失败");
                                }
                            }
                        });
                    } else {
                        Log.e(TAG, "onLocationChanged: " + "第一次请求定位失败:" + aMapLocation.getErrorInfo());
                    }
                }
            });
        }

        private void initClient(Context context) {
            if (client == null) {
                client = new AMapLocationClient(context);
                AMapLocationClientOption option = new AMapLocationClientOption();
                option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                option.setOnceLocation(true);
                client.setLocationOption(option);
            }
        }

        @Override
        public void run() {
            client.startLocation();
        }
    }
}
