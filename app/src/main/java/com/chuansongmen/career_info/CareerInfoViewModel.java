package com.chuansongmen.career_info;

import android.app.Application;
import android.graphics.Bitmap;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.util.ThreadUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

class CareerInfoViewModel extends BaseViewModel {
    private MutableLiveData<Bitmap> careerQRCode = new MutableLiveData<>();

    CareerInfoViewModel(
            @NonNull Application application) {
        super(application);
    }

    LiveData<Bitmap> getCareerQRCode() {
        return careerQRCode;
    }

    void createQRCode() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                careerQRCode.postValue(QRCodeEncoder.syncEncodeQRCode(String.valueOf(1), 120));
            }
        });
    }
}
