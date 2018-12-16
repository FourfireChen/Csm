package com.chuansongmen.transfer;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Worker;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

class TransferViewModel extends BaseViewModel {
    private MutableLiveData<Worker> targetWorker = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTransferSuccess = new MutableLiveData<>();

    TransferViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<Worker> getTargetWorker() {
        return targetWorker;
    }

    LiveData<Boolean> getIsTransferSuccess() {
        return isTransferSuccess;
    }

    /**
     * 查询二维码扫出来的职工的id
     * 查询结果post targetWorder
     *
     * @param workerId 二维码的员工id
     */
    void queryWorker(String workerId) {
        dataRepo.getWorkerInfo(workerId, new Callback<Worker>() {
            @Override
            public void onResponse(Worker result) {
                targetWorker.postValue(result);
            }
        });
    }

    /**
     * 移交订单
     * 移交结果则Post {@link this.isTransferSuccess}
     *
     * @param pagerId  要移交的订单号
     * @param workerId 要移交的目标职员
     */
    void transferOrder(String pagerId, String workerId) {
        dataRepo.transferOrder(pagerId, workerId, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean result) {
                isTransferSuccess.postValue(result);
            }
        });
    }
}
