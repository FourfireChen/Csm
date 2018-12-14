package com.chuansongmen.data;

import android.content.Context;
import android.util.Log;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.util.ConvertorFactory;
import com.chuansongmen.util.ThreadUtil;

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
    private LocalData localData;
    // TODO: 2018/11/7 这里的URL是暂时的
    private static final String URL = "http://178.128.184.142:8080/portal/";
    public static final String SUCCESS = "成功";
    public static final String FAIL = "失败";
    private final String product = "Dysmsapi";
    private final String domain = "dysmsapi.aliyuncs.com";
    private final String accessKeyId = "LTAIGwKgsjQoc4aR";
    private final String accessKeySecret = "n8iIp5mZEsjm3ei9rgvHfiXl6XLN8W";

    private DataRepository() {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(URL)
                        .addConverterFactory(new ConvertorFactory())
                        .build();
        remoteData = retrofit.create(IRemoteData.class);
        localData = new LocalData();
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
        Call<Position> call = remoteData.queryOrderPos(order.getOrderId());
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
            order.setUserPhone(phone[Math.abs(random.nextInt()) % 6]);
            order.setOrderId(id[Math.abs(random.nextInt()) % 6]);
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
    public void changeDelay(final String orderPagerId,
                            final int isDelay,
                            final Callback<String> result) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(orderPagerId, isDelay);
                    Call<ResponseBody> call = remoteData.changeDelay(mapToJson(jsonObject));
                    Response response = call.execute();
                    if (response != null) {
                        if (response.isSuccessful()) {
                            result.onResponse(SUCCESS);
                        } else {
                            result.onResponse(
                                    response.errorBody() != null ? response.errorBody().string() :
                                            FAIL);
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    result.onResponse(e.getMessage());
                }
            }
        });
    }

    @Override
    public void receiveOrderFromUser(final String orderSerialId,
                                     final String pagerId,
                                     final String workerId,
                                     final Callback<String> messageCallback) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("order_id", orderSerialId);
                    jsonObject.put("order_paper_id", pagerId);
                    jsonObject.put("worker_id", workerId);
                    Response body =
                            remoteData.receiveOrderFromUser(mapToJson(jsonObject)).execute();
                    if (body != null && body.isSuccessful()) {
                        messageCallback.onResponse(SUCCESS);
                    } else {
                        messageCallback.onResponse(body != null ?
                                body.errorBody() != null ? body.errorBody().string() : FAIL : FAIL);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    messageCallback.onResponse(e.getMessage());
                }
            }
        });
    }

    public void addTestWorker(final Callback<Boolean> callback) {
        ThreadUtil.execute(new Runnable() {
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
        });
    }

    @Override
    public void sendMessage(final String phone,
                            final String code,
                            final Callback<Boolean> resultCallback) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
                System.setProperty("sun.net.client.defaultReadTimeout", "10000");

                IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                        accessKeySecret);
                try {
                    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
                    IAcsClient acsClient = new DefaultAcsClient(profile);

                    SendSmsRequest request = new SendSmsRequest();
                    //使用post提交
                    request.setMethod(MethodType.POST);
                    //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
                    request.setPhoneNumbers(phone);
                    //必填:短信签名-可在短信控制台中找到
                    request.setSignName("点链科技");
                    //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
                    request.setTemplateCode("SMS_151233416");
                    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
                    //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
                    request.setTemplateParam("{\"code\":\"" + code + "\"}");
                    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
                    if (sendSmsResponse.getCode() != null &&
                            sendSmsResponse.getCode().equals("OK")) {
                        resultCallback.onResponse(true);
                    }
                } catch (ClientException e) {
                    e.printStackTrace();
                    resultCallback.onResponse(false);
                }
            }
        });
    }

    @Override
    public void cacheUserPhoneNumber(final Context context, final String phoneNumber) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                localData.cacheUserPhoneNumber(context, phoneNumber);
            }
        });
    }

    @Override
    public void getCacheUserPhoneNumber(final Context context, final Callback<String> callback) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(localData.getCacheUserPhoneNumber(context));
            }
        });
    }

    private RequestBody mapToJson(JSONObject jsonObject) {
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
