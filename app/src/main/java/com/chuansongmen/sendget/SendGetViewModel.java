package com.chuansongmen.sendget;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.bean.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SendGetViewModel extends BaseViewModel {
    private MutableLiveData<List<Order>> order = new MutableLiveData<>();

    public SendGetViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<List<Order>> getOrders() {
        dataRepo.getWorkerOrders(1, order);
        return order;
    }
}
