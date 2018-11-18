package com.chuansongmen.main;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class MainViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isWorked;

    public MainViewModel(
            @NonNull Application application) {
        super(application);
    }

    LiveData<Boolean> changeWorkState(int status) {
        if (isWorked == null) {
            isWorked = new MutableLiveData<>();
        }
        dataRepo.updateWorkerStatus(1, status, isWorked);
        return isWorked;
    }
}
