package com.chuansongmen.main;

import android.app.Application;

import com.chuansongmen.base.BaseViewModel;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.exception.NotInitException;
import com.chuansongmen.exception.RepetitiveInitException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


class MainViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isWorked = new MutableLiveData<>();

    private MutableLiveData<List<List<Order>>> orders = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLogoutSuccess = new MutableLiveData<>();
    private LiveData<Worker> worker = new MutableLiveData<>();

    MainViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<Boolean> getWorkerStatus() {
        return isWorked;
    }

    LiveData<List<List<Order>>> getOrders() {
        return orders;
    }

    LiveData<Boolean> getIsLogoutSuccess() {
        return isLogoutSuccess;
    }

    LiveData<Worker> getWorker() {
        return worker;
    }

    /**
     * 这只是测试的时候用的，正常情况下的初始化不在这里
     * @param phone
     */
    // TODO: 2/18/19 测试结束记得删除
    void initGetWorker(String phone) {
        dataRepo.getWorkerInfo(phone, new Callback<Worker>() {
            @Override
            public void onResponse(Worker result) {
                try {
                    Worker.initWorker(result);
                    updateOrders();
                } catch (RepetitiveInitException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void updateWorkerStatus(int status) {
        try {
            dataRepo.changeWorkerStatus(Worker.getInstance().getId(),
                    status,
                    new Callback<Boolean>() {
                        @Override
                        public void onResponse(Boolean result) {
                            isWorked.postValue(result);
                        }
                    });
        } catch (NotInitException e) {
            e.printStackTrace();
        }
    }

    void updateOrders() {
        try {
            dataRepo.getWorkerOrders(Worker.getInstance().getId(), new Callback<List<Order>>() {
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
        } catch (NotInitException e) {
            e.printStackTrace();
        }
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
        try {
            dataRepo.logout(getApplication(),
                    Worker.getInstance().getId(),
                    new Callback<Boolean>() {
                        @Override
                        public void onResponse(Boolean result) {
                            isLogoutSuccess.postValue(result);
                        }
                    });
        } catch (NotInitException e) {
            e.printStackTrace();
        }
    }
}
