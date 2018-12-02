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
                            final Callback<Boolean> isSuccess) {
        Call<ResponseBody> call = remoteData.updateOrder(demandOrderStr, targetOrderStr);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isSuccess.onResponse(true);
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isSuccess.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isSuccess.onResponse(false);
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void queryOrderPos(Order order, final Callback<Position> callback) {
        Call<Position> call = remoteData.queryOrderPos(order.getId());
        call.enqueue(new retrofit2.Callback<Position>() {
            @Override
            public void onResponse(Call<Position> call, Response<Position> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    callback.onResponse(null);
                }
            }

            @Override
            public void onFailure(Call<Position> call, Throwable t) {
                callback.onResponse(null);
                Log.e(TAG, "onFailure: ", t);
            }
        });
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
    public void uploadForPush(int workerId, final String regId, final Callback<Boolean> callback) {
        remoteData.uploadForPush(workerId, regId).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(true);
                } else {
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onResponse(false);
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void uploadPos(int workerId, Position position, final Callback<Boolean> callback) {
        remoteData.uploadPos(workerId, position.getLongitude(), position.getLatitude())
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            callback.onResponse(true);
                        } else {
                            callback.onResponse(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        callback.onResponse(false);
                        Log.e(TAG, "onFailure: ", t);
                    }
                });
    }

    @Override
    public void updateWorkerStatus(Integer workerId,
                                   Integer status,
                                   final Callback<Boolean> isSuccess) {
        Call call = remoteData.updateWorkerStatus(workerId, status);
        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    isSuccess.onResponse(true);
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isSuccess.onResponse(false);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                isSuccess.onResponse(false);
                Log.e(TAG, "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getWorkerInfo(int workerId, final Callback<Worker> callback) {
        remoteData.getWorkerInfo(workerId).enqueue(new retrofit2.Callback<Worker>() {
            @Override
            public void onResponse(Call<Worker> call, Response<Worker> response) {
                if (response.isSuccessful())
                    callback.onResponse(response.body());
                else
                    callback.onResponse(null);
            }

            @Override
            public void onFailure(Call<Worker> call, Throwable t) {
                callback.onResponse(null);
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getAllRoute(final Callback<List<Route>> callback) {
        remoteData.getAllRoutes().enqueue(new retrofit2.Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                if (response.isSuccessful())
                    callback.onResponse(response.body());
                else
                    callback.onResponse(null);
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                callback.onResponse(null);
                Log.e(TAG, "onFailure: ", t);
            }
        });
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
