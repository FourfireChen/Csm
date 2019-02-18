package com.chuansongmen.career_info;

import android.app.Application;
import android.graphics.Bitmap;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.exception.NotInitException;
import com.chuansongmen.util.ThreadUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

class CareerInfoViewModel extends BaseViewModel {
    private MutableLiveData<Bitmap> careerQRCode = new MutableLiveData<>();
    private MutableLiveData<Worker> workerInfo = new MutableLiveData<>();

    CareerInfoViewModel(
            @NonNull Application application) {
        super(application);
    }

    LiveData<Bitmap> getCareerQRCode() {
        return careerQRCode;
    }

    void init() {
        ThreadUtil.execute(() -> {
            try {
                careerQRCode.postValue(QRCodeEncoder.syncEncodeQRCode(String.valueOf(
                        Worker.getInstance().getId()), 120));
                workerInfo.postValue(Worker.getInstance());
            } catch (NotInitException e) {
                e.printStackTrace();
            }
        });

    }

    LiveData<Worker> getWorkerInfo() {
        return workerInfo;
    }
}
