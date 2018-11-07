package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.util.ConvertorFactory;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

public class DataRepository implements IDataRepository {
    private static IDataRepository instance;
    private IRemoteData remoteData;
    // TODO: 2018/11/7 这里的URL是暂时的
    private static final String URL = "http://178.128.184.142:8080/portal/";

    private DataRepository() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(URL)
                        .addConverterFactory(new ConvertorFactory())
                        .build();
        remoteData = retrofit.create(IRemoteData.class);
    }

    public static IDataRepository getInstance() {
        if (instance == null)
            instance = new DataRepository();
        return instance;
    }


    @Override
    public boolean updateOrder(String demandOrderStr, String targetOrderStr) {
        try {
            Response<Boolean> result =
                    remoteData.updateOrder(demandOrderStr, targetOrderStr).execute();
            if (result.isSuccessful()) {
                return result.body() == null ? false : result.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Position queryOrderPos(Order order) {
        try {
            Response<Position> result = remoteData.queryOrderPos(order.getId()).execute();
            if (result.isSuccessful()) {
                return result.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getWorkerOrders(int workerId) {
        try {
            Response<List<Order>> response = remoteData.getWorkerOrders(workerId).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean uploadForPush(int workerId, String regId) {
        try {
            Response<Boolean> response = remoteData.uploadForPush(workerId, regId).execute();
            if (response.isSuccessful()) {
                return response.body() == null ? false : response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean uploadPos(int workerId, Position position) {
        try {
            Response<Boolean> response =
                    remoteData.uploadPos(workerId, position.getLongitude(), position.getLatitude())
                            .execute();
            if (response.isSuccessful()) {
                return response.body() == null ? false : response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateWorkerStatus(int workerId, int status) {
        try {
            Response<Boolean> response = remoteData.updateWorkerStatus(workerId, status).execute();
            if (response.isSuccessful()) {
                return response.body() == null ? false : response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Worker getWorkerInfo(int workerId) {
        try {
            Response<Worker> response = remoteData.getWorkerInfo(workerId).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Route> getAllRoute() {
        try {
            Response<List<Route>> response = remoteData.getAllRoutes().execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void stopAll() {
        // TODO: 2018/11/7 停止所有正在进行的操作
    }
}
