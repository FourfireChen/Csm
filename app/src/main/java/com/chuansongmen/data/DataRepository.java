package com.chuansongmen.data;

import android.mtp.MtpConstants;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DataRepository implements IDataRepository {
    private static final String TAG = "DataRepository";
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
    public void getWorkerOrders(String workerId, final Callback<List<Order>> orders) {
//        Call<List<Order>> call = remoteData.getWorkerOrders(workerId);
//        call.enqueue(new retrofit2.Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                if (response.isSuccessful()) {
//                    orders.onResponse(response.body());
//                } else {
//                    try {
//                        Log.e(TAG, "onResponse: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    orders.onResponse(null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                Log.e(TAG, "onResponse: ", t);
//                orders.onResponse(null);
//            }
//        });
        orders.onResponse(testGenerateOrders());
    }

    private List<Order> testGenerateOrders() {
        List<Order> orders = new ArrayList<>();
        String[] name = {"张三", "里斯", "王五", "赵六", "陈七", "邓八"};
        String[] address = {"武汉", "洪山", "澄海", "汕头", "鄂尔多斯", "上海"};
        String[] phone = {"1324567891231",
                "3526412589745",
                "2356985471256",
                "1325489635124",
                "1324567894562",
                "5412365897452"};
        String[] remark = {"hhh", "aaa", "bbb", "ccc", "ddd", "eee"};
        String[] id = {"239481", "df234235", "df2345", "sdfsdf32", "345234", "235235"};
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setStatus(Order.Status.values()[Math.abs(random.nextInt()) % 6]);
            order.setRemark(remark[Math.abs(random.nextInt()) % 6]);
            order.setDelay(random.nextBoolean());
            order.setPrice(Math.abs(random.nextInt()));
            order.setRecipientAddress(address[Math.abs(random.nextInt()) % 6]);
            order.setRecipientPhone(phone[Math.abs(random.nextInt()) % 6]);
            order.setRecipientName(name[Math.abs(random.nextInt()) % 6]);
            order.setUserId(phone[Math.abs(random.nextInt()) % 6]);
            order.setId(id[Math.abs(random.nextInt()) % 6]);
            order.setPagerId(id[Math.abs(random.nextInt()) % 6]);
            orders.add(order);
        }
        return orders;
    }


    @Override
    public void uploadForPush(String workerId,
                              final String regId,
                              final Callback<Boolean> callback) {
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
    public void uploadPos(String workerId, Position position, final Callback<Boolean> callback) {
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
    public void updateWorkerStatus(String workerId,
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
    public void getWorkerInfo(String workerId, final Callback<Worker> callback) {
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
