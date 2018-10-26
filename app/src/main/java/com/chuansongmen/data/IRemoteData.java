package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface IRemoteData {
    /**
     * 添加订单
     *
     * @param order    要添加的订单
     */
    @POST
    Call<Boolean> addOrder(Order order);


    /**
     * 更新订单数据
     *
     * @param order          更新的订单，这里是根据主键来查找的原订单，所以主键不能变
     * @deprecated  回调接口，返回的结果是新的订单，如果是null，则失败。
     */
    Call<Order> updateOrder(Order order);


    /**
     * 查找当前订单的位置
     *
     * @param order            要查找的订单
     * @param positionCallback 回调接口，返回的是要查询的结果，如果是null，则失败
     */
    void queryOrderPos(Order order, Callback<Position> positionCallback);


    /**
     * 获取与某个员工绑定的订单
     *
     * @param workerId      要查找的员工的id
     * @param ordersCallbak 回调接口，返回所有与该员工有关的订单，如果是null，查询失败；如果list为空，数据库中没有结果
     */
    void getWorkerOrders(int workerId, Callback<List<Order>> ordersCallbak);


    /**
     * 上传小米推送中生成的设备id
     *
     * @param workerId 要上传的员工id
     * @param regId    设备id
     * @param result   上传结果
     */
    void uploadForPush(int workerId, String regId, Callback<Boolean> result);


    /**
     * 上传员工当前位置
     *
     * @param workerId 要上传的员工id
     * @param position 当前位置
     * @param result   上传结果
     */
    void uploadPos(int workerId, Position position, Callback<Boolean> result);

    /**
     * 改变员工工作状态，就是上下班
     *
     * @param workerId           该要变的员工id
     * @param status             新的状态
     *                           0：表示下班
     *                           1：表示上班
     * @param updateStatusResult 改变的结果
     */
    void updateWorkerStatus(int workerId, int status, Callback<Boolean> updateStatusResult);

    /**
     * 获取员工信息
     *
     * @param workerId       要获取的员工的Id
     * @param workerCallback 获取的员工的信息
     */
    void getWorkerInfo(int workerId, Callback<Worker> workerCallback);


    /**
     * 获取所有的路径信息
     * @param routeId
     * @param driverId
     * @param routeName
     * @param workerName
     */
    void getAllRoutes(String routeId, int driverId, String routeName, String workerName);
}
