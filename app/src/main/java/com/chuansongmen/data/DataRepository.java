package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.data.retrofit_util.ConvertorFactory;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

public class DataRepository implements IDataRepository {
    private static IDataRepository instance;
    private IRemoteData remoteData;
    //todo:补充URL
    private static final String URL = "127.0.0.1";

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
                return result.body();
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
                return response.body();
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
                    remoteData.uploadPos(workerId, position.getLongitude(), position.getLatitude() )
                            .execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateWorkerStatus(int workerId, int status) {
        return false;
    }

    @Override
    public Worker getWorkerInfo(int workerId) {
        return null;
    }

    @Override
    public List<Route> getAllRoute() {
        return null;
    }
}
