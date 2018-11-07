package com.chuansongmen.sendget;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.data.bean.Order;

import java.util.ArrayList;

public class SendGetViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<Order>> order = new MutableLiveData<>();

    public SendGetViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Order>> getOrder() {
        //todo:获取数据更新，通知更新
        return order;
    }
}
