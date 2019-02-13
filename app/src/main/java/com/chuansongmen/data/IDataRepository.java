package com.chuansongmen.data;

import android.content.Context;

import com.chuansongmen.common.Callback;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Worker;

import java.util.List;


/**
 * 数据仓库接口，所有与数据有关的操作都经过它
 */
public interface IDataRepository {

    /**
     * 收派员收件
     *
     * @param orderId      订单号
     * @param orderPaperId 流水号
     * @param workerId     员工号
     */
    void receiveOrderFromUser(String orderId,
                              String orderPaperId,
                              String workerId,
                              Callback<String> messageCallback);

    /**
     * 收派员派件，完成订单
     *
     * @param orderPagerId 订单号
     */
    void completeOrder(String orderPagerId, Callback<Boolean> isSuccess);


    /**
     * 获取与员工绑定的订单
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
    void uploadRegId(String workerId, String regId, Callback<Boolean> callback);


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
    void changeWorkerStatus(String workerId, Integer status, Callback<Boolean> callback);

    /**
     * 获取员工信息
     *
     * @param workerId 要获取的员工的Id
     * @return 获取的员工的信息
     */
    void getWorkerInfo(String workerId, Callback<Worker> callback);


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
    void changeDelay(String orderPagerId, int isDelay, Callback<String> callback);


    /**
     * 短信验证
     *
     * @param code 验证码
     */
    void sendMessage(String phoneNumber, String code, Callback<Boolean> resultCallback);

    /**
     * 获取记住的用户id，也就是用户名
     */
    void getCacheUserPhoneNumber(Context context, Callback<String> callback);

    /**
     * 缓存员工id
     */
    void cacheUserPhoneNumber(Context context, String phoneNumber);

    /**
     * 注销
     *
     * @param context
     * @param id       员工id
     * @param callback
     */
    void logout(Context context, String id, Callback<Boolean> callback);

    /**
     * 移交订单
     *
     * @param pagerId          订单
     * @param previousWorkerId 原本的员工id
     * @param toWorkerId       要移交给的员工id
     */
    void hostOrder(String pagerId,
                   String previousWorkerId,
                   String toWorkerId,
                   Callback<Boolean> callback);

    /**
     * 托管员工当日工作
     *
     * @param fromWorkerId 原员工id
     * @param toWorkerId   要移交的目标员工id
     */
    void hostWork(String fromWorkerId, String toWorkerId, Callback<Boolean> callback);
}
