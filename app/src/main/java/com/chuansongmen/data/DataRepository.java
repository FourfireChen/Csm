package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.data.retrofit_util.ConvertorFactory;
import com.chuansongmen.data.retrofit_util.CallAdatperFactory;

import java.util.List;

import retrofit2.Retrofit;

public class DataRepository implements IDataRepository {
    private static IDataRepository instance;
    private IRemoteData remoteData;
    //todo:补充URL
    private static final String URL = "127.0.0.1";

    private DataRepository() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(URL)
                        .addCallAdapterFactory(new CallAdatperFactory())
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
        return false;
    }

    @Override
    public Position queryOrderPos(Order order) {
        return null;
    }

    @Override
    public List<Order> getWorkerOrders(int workerId) {
        return null;
    }


    @Override
    public boolean uploadForPush(int workerId, String regId) {
        return false;
    }

    @Override
    public boolean uploadPos(int workerId, Position position) {
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
