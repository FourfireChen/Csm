package com.chuansongmen.confirm;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ConfirmViewModel extends BaseViewModel {
    private MutableLiveData<String> changeDelayResult = new MutableLiveData<>();

    public ConfirmViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<String> getChangeDelayResult() {
        return changeDelayResult;
    }

    void delay(String orderPagerId, String remark) {
        // TODO: 2018/12/8 改订单备注
        dataRepo.changeDelay(orderPagerId, 1, new Callback<String>() {
            @Override
            public void onResponse(String result) {
                changeDelayResult.postValue(result);
            }
        });
    }


}
