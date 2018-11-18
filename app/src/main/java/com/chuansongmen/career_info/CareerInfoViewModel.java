package com.chuansongmen.career_info;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.bean.Worker;

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
