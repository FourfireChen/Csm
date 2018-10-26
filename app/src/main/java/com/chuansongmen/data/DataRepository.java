package com.chuansongmen.data;

import androidx.lifecycle.LiveData;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

public class DataRepository implements IDataRepository {
    private static IDataRepository instance;
    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;

    private DataRepository() {
        remoteRepository = new RemoteRepository();
        localRepository = new LocalRepository();
    }

    public static IDataRepository getInstance() {
        if (instance == null)
            instance = new DataRepository();
        return instance;
    }


    @Override
    public void addOrder(Order order, Callback<Boolean> callback) {

    }

    @Override
    public void updateOrder(LiveData<Order> order) {

    }

    @Override
    public void queryOrderPos(Order order, Callback<Position> positionCallback) {

    }

    @Override
    public LiveData<List<Order>> getWorkerOrders(int workerId) {
        return null;
    }

    @Override
    public void uploadForPush(int workerId, String regId, Callback<Boolean> result) {

    }

    @Override
    public void uploadPos(int workerId, Position position, Callback<Boolean> result) {

    }

    @Override
    public void updateWorkerStatus(int workerId, int status, Callback<Boolean> updateStatusResult) {

    }

    @Override
    public LiveData<Worker> getWorkerInfo(int workerId) {
        return null;
    }
}
