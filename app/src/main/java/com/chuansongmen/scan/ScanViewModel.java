package com.chuansongmen.scan;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ScanViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();

    ScanViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<Boolean> updateOrder() {
        return isSuccess;
    }

    boolean checkCodeFormate(String code) {
        return false;
    }
}
