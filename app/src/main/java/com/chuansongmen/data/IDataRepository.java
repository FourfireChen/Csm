package com.chuansongmen.data;

import android.content.Context;

import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;

import java.util.List;


/**
 * 数据仓库接口，所有与数据有关的操作都经过它
 */
public interface IDataRepository {

    /**
     * 更新订单数据
     *
     * @param demandOrderStr 能确定目标订单的信息，以;分割，如“status=1;now_worker=123213”
     * @param targetOrderStr 要修改的信息，以;分割，如“status=2;now_worker=2132131”
     */
//    void updateOrder(String demandOrderStr,
//                     String targetOrderStr,
//                     MutableLiveData<Boolean> isSuccess);

    void updateOrder(String demandOrderStr,
                     String targetOrderStr,
                     Callback<Boolean> isSuccess);


    /**
     * 查找当前订单的位置
     *
     * @param order 要查找的订单
     * @return 订单位置
     */
    void queryOrderPos(Order order, Callback<Position> callback);


    /**
     * 获取与某个员工绑定的订单
     *
     * @param workerId 要查找的员工的id
     * @return 返回的是获取的订单
     */
    void getWorkerOrders(String workerId, Callback<List<Order>> orders);


    /**
     * 上传小米推送中生成的设备id
     *
     * @param workerId 要上传的员工id
     * @param regId    设备id
     * @return 上传结果
     */
    void uploadForPush(String workerId, String regId, Callback<Boolean> callback);


    /**
     * 上传员工当前位置
     *
     * @param workerId 要上传的员工id
     * @param position 当前位置
     * @return 上传结果
     */
    void uploadPos(String workerId, Position position, Callback<Boolean> callback);

    /**
     * 改变员工工作状态，就是上下班
     *
     * @param workerId 该要变的员工id
     * @param status   新的状态
     *                 0：表示下班
     *                 1：表示上班
     * @return 改变的结果
     */
//    void updateWorkerStatus(Integer workerId, Integer status, MutableLiveData<Boolean> isSuccess);
    void updateWorkerStatus(String workerId, Integer status, Callback<Boolean> isSuccess);

    /**
     * 获取员工信息
     *
     * @param workerId 要获取的员工的Id
     * @return 获取的员工的信息
     */
    void getWorkerInfo(String workerId, Callback<Worker> callback);

    /**
     * 获取所有路线信息
     */
    void getAllRoute(Callback<List<Route>> callback);

    /**
     * 停止所有正在进行的操作
     */
    void stopAll();

    /**
     * 改变滞留状态
     *
     * @param orderPagerId 订单号
     * @param isDelay      1为滞留，0为取消滞留
     */
    void changeDelay(String orderPagerId, int isDelay, Callback<String> result);


    void receiveOrderFromUser(String orderSerialId,
                              String pagerId,
                              String workerId,
                              Callback<String> messageCallback);

    /**
     * 短信验证
     * @param code 验证码
     */
    void sendMessage(String phone, String code, Callback<Boolean> resultCallback);

    void getCacheUserPhoneNumber(Context context, Callback<String> callback);

    void cacheUserPhoneNumber(Context context, String phoneNumber);

    void logout(Context context, String id, Callback<Boolean> callback);
}
