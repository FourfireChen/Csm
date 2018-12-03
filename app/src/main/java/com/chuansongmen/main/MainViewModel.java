package com.chuansongmen.main;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class MainViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isWorked = new MutableLiveData<>();;
    private static final int POINT_ORDER_PRICE = 10;
    private MutableLiveData<List<List<Order>>> orders = new MutableLiveData<>();

    public MainViewModel(
            @NonNull Application application) {
        super(application);
    }

    LiveData<Boolean> getWorkerStatus() {
        return isWorked;
    }

    void updateWorkerStatus(int status) {
        dataRepo.updateWorkerStatus(1, status, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean result) {
                isWorked.postValue(result);
            }
        });
    }

    MutableLiveData<List<List<Order>>> getOrders() {
        return orders;
    }

    void updateOrders() {
        dataRepo.getWorkerOrders(1, new Callback<List<Order>>() {
            @Override
            public void onResponse(List<Order> result) {
                orders.postValue(classifyOrders(result));
            }
        });
    }


    private List<List<Order>> classifyOrders(List<Order> allOrders) {
        List<List<Order>> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            result.add(new ArrayList<Order>());
        }
        for (Order order :
                allOrders) {
            int index = 0;
            switch (order.getStatus()) {
                case NON_PICK_UP:
                    if (order.isDelay()) {
                        index = 3;
                    } else if (order.getPrice() > POINT_ORDER_PRICE) {
                        index = 2;
                    } else {
                        index = 0;
                    }
                    break;
                case SENDING:
                    if (order.isDelay()) {
                        index = 7;
                    } else if (order.getPrice() > POINT_ORDER_PRICE) {
                        index = 6;
                    } else {
                        index = 4;
                    }
                    break;
                case HAS_PICKED_UP:
                    index = 1;
                    break;
                case HAS_SENDED:
                    index = 5;
                    break;
            }
            result.get(index).add(order);
        }
        return result;
    }
}
