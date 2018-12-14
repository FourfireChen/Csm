package com.chuansongmen.main;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Worker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class MainViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isWorked = new MutableLiveData<>();
    private static final int POINT_ORDER_PRICE = 10;
    private MutableLiveData<List<List<Order>>> orders = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLogoutSuccess = new MutableLiveData<>();

    public MainViewModel(
            @NonNull Application application) {
        super(application);
    }

    LiveData<Boolean> getWorkerStatus() {
        return isWorked;
    }

    MutableLiveData<List<List<Order>>> getOrders() {
        return orders;
    }

    MutableLiveData<Boolean> getIsLogoutSuccess() {
        return isLogoutSuccess;
    }

    void updateWorkerStatus(int status) {
        dataRepo.updateWorkerStatus("1", status, new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean result) {
                isWorked.postValue(result);
            }
        });
    }




    void updateOrders() {
        dataRepo.getWorkerOrders("1", new Callback<List<Order>>() {
            @Override
            public void onResponse(List<Order> result) {
                List<List<Order>> value;
                if (result == null) {
                    value = orders.getValue() == null ? new ArrayList<List<Order>>() :
                            orders.getValue();
                } else {
                    value = classifyOrders(result);
                }
                orders.postValue(value);
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
                        index = 2;
                    } else if (order.isImportant()) {
                        index = 3;
                    } else {
                        index = 0;
                    }
                    break;
                case SENDING:
                    if (order.isDelay()) {
                        index = 6;
                    } else if (order.isImportant()) {
                        index = 7;
                    } else {
                        index = 4;
                    }
                    break;
                case IN_STATION:
                case TRANSPOTING:
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


    void logout() {
        dataRepo.logout(Worker.getInstance().getId(), new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean result) {
                isLogoutSuccess.postValue(result);
            }
        });
    }
}
