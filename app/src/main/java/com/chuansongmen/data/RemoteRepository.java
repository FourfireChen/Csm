package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

public class RemoteRepository {

    public void addOrder(Order order, Callback<Boolean> callback) {
        
    }

    public void updateOrder(Order order, Callback<Order> resultCallback) {

    }

    public void queryOrderPos(Order order, Callback<Position> positionCallback) {

    }

    public void getWorkerOrders(int workerId, Callback<List<Order>> ordersCallbak) {

    }

    public void uploadForPush(int workerId, String regId, Callback<Boolean> result) {

    }

    public void uploadPos(int workerId, Position position, Callback<Boolean> result) {

    }

    public void updateWorkerStatus(int workerId, int status, Callback<Boolean> updateStatusResult) {

    }

    public void getWorkerInfo(int workerId, Callback<Worker> workerCallback) {

    }

    public void getAllRoutes(String routeId, int driverId, String routeName, String workerName) {

    }
}