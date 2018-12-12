package com.chuansongmen.transfer;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.bean.Worker;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

class TransferViewModel extends BaseViewModel {
    MutableLiveData<Worker> targetWorker = new MutableLiveData<>();
    MutableLiveData<Boolean> isTransferSuccess = new MutableLiveData<>();
    public TransferViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Worker> getTargetWorker() {
        return targetWorker;
    }

    public MutableLiveData<Boolean> getIsTransferSuccess() {
        return isTransferSuccess;
    }

    public void queryWorker(String workerId) {

    }

    public void transferOrder(String pagerId, String workerId) {

    }
}
