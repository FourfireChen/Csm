package com.chuansongmen.data;

import android.util.Log;

import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.util.ConvertorFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataRepository implements IDataRepository {
    private static final String TAG = "datarepository";
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
    public void updateOrder(String demandOrderStr,
                            String targetOrderStr,
                            final MutableLiveData<Boolean> isSuccess) {
        Call<ResponseBody> call = remoteData.updateOrder(demandOrderStr, targetOrderStr);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isSuccess.postValue(true);
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isSuccess.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isSuccess.postValue(false);
                Log.e(TAG, "onFailure: ", t);
            }
        });
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
    public void getWorkerOrders(int workerId, final Callback<List<Order>> orders) {
        Call<List<Order>> call = remoteData.getWorkerOrders(workerId);
        call.enqueue(new retrofit2.Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    orders.onResponse(response.body());
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e(TAG, "onResponse: ", t);
            }
        });
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
    public void updateWorkerStatus(Integer workerId,
                                   Integer status,
                                   final MutableLiveData<Boolean> isSuccess) {
        Call call = remoteData.updateWorkerStatus(workerId, status);
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    isSuccess.postValue(true);
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isSuccess.postValue(false);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                isSuccess.postValue(false);
                Log.e(TAG, "onFailure: ", t);
                t.printStackTrace();
            }
        });
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

    @Override
    public void addTestWorker(final Callback<Boolean> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("worker_id", 2);
                    jsonObject.put("worker_name", "test2");
                    jsonObject.put("worker_sex", 1);
                    jsonObject.put("category", 3);
                    jsonObject.put("belong_station", "test2");
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                            jsonObject.toString());
                    Call call = remoteData.addMan(body);
                    Log.i(TAG, "run: " + call.request().body().contentType().toString());
                    Response response = call.execute();

                    if (response.isSuccessful()) {
                        callback.onResponse(true);
                        Log.i(TAG, "addTestWorker: 成功");
                    } else {
                        callback.onResponse(false);
                        Log.i(TAG, "addTestWorker: 失败" + response.errorBody().string());
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
