package com.chuansongmen.position;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

public class PositionUploadReceiver extends BroadcastReceiver {
    private static final String TAG = "PositionUploacReceiver";
    private IDataRepository dataRepository = DataRepository.getInstance();
    private AMapLocationClient client = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        initClient(context.getApplicationContext());
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
        client.startLocation();
        Log.i(TAG, "onReceive: 定位");
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

}
