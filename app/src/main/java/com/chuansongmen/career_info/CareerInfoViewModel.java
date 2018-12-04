package com.chuansongmen.career_info;

import android.app.Application;
import android.graphics.Bitmap;

import com.chuansongmen.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class CareerInfoViewModel extends BaseViewModel {
    private MutableLiveData<Bitmap> careerQRCode = new MutableLiveData<>();

    public CareerInfoViewModel(
            @NonNull Application application) {
        super(application);
    }

    MutableLiveData<Bitmap> getCareerQRCode() {
        return careerQRCode;
    }

    void createQRCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                careerQRCode.postValue(QRCodeEncoder.syncEncodeQRCode(String.valueOf(1), 120));
            }
        }).start();
    }
}
