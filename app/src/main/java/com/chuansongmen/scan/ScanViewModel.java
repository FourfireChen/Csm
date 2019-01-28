package com.chuansongmen.scan;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

class ScanViewModel extends BaseViewModel {
    private LiveData<Boolean> isUpdateSuccess = new MutableLiveData<>();

    ScanViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> isUpdateSuccess() {
        return isUpdateSuccess;
    }

    public void updateOrder(String orderId) {
        // TODO: 1/25/19 更新订单状态
    }
}
