package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRemoteData {
    /**
     * 员工端需要用到的接口
     *
     * 收派员收件
     * 改变订单滞留状态
     * 收派员派件
     * 收派员托管某个快件
     * 获取与员工绑定的订单
     * 上传小米推送的设备ID
     * 上传定位
     * 改变员工工作状态
     * 获取员工信息
     * 托管员工工作
     */


    /**
     * 收派员收件
     *
     * @param receiveRequest order_id 流水号
     *                       order_paper_id 订单号
     *                       worker_id 收派员id
     */
    @POST("/order/changeOrder/receiveOrderFromUser")
    Call<ResponseBody> receiveOrderFromUser(@Body RequestBody receiveRequest);

    /**
     * 改变滞留状态
     *
     * @param orderDelayRequest order_paper_id 订单号
     *                          id_delay 是否滞留 1表示滞留 2表示解除滞留
     */
    @POST("/order/delay")
    Call<ResponseBody> changeDelay(@Body RequestBody orderDelayRequest);

    /**
     * 完成派件
     *
     * @param orderPagerId 订单号
     */
    @POST("/order/changeOrder/complete")
    Call<ResponseBody> completeOrder(@Body RequestBody orderPagerId);

    /**
     * 收派员将快件托管给另一个收派员
     *
     * @param packageHostRequest order_paper_id 订单号
     *                           previous_worker_id 发起托管的员工id
     *                           to_worker_id 接受托管的员工id
     */
    // TODO: 2/13/19 这里的URL要补
    @POST()
    Call<ResponseBody> hostOrder(@Body RequestBody packageHostRequest);


    /**
     * 获取与员工绑定的订单
     *
     * @param workerId 要查找的员工的id
     * @return 返回所有与该员工有关的订单，如果是null，查询失败；如果list为空，数据库中没有结果
     */
    @GET("/order")
    Call<List<Order>> getWorkerOrders(@Query("worker_id") String workerId);

    /**
     * 上传小米推送的设备ID
     *
     * @param workerId 要上传的员工id
     * @param regId    设备id
     * @return 是否上传成功
     */
    @PATCH("/worker/reg")
    Call<ResponseBody> uploadRegId(@Query("worker_id") String workerId,
                                   @Query("reg_id") String regId);

    /**
     * 上传定位
     *
     * @param workerId  要上传的员工id
     * @param longitude 当前经度
     * @param latitude  当前纬度
     * @return 是否上传成功
     */
    @PATCH("/worker/location")
    Call<ResponseBody> uploadPos(@Query("worker_id") String workerId,
                                 @Query("longitude") double longitude,
                                 @Query("latitude") double latitude);

    /**
     * 改变员工工作状态
     *
     * @param workerId 员工id
     * @param status   新的状态
     *                 0：表示下班
     *                 1：表示上班
     * @return 是否改变成功
     */
    @PATCH("/worker/status")
    Call<ResponseBody> changeWorkerStatus(@Query("worker_id") String workerId,
                                          @Query("status") Integer status);

    /**
     * 获取员工信息
     *
     * @param workerId 要获取的员工的Id/手机号码
     * @return 查询到的员工信息
     */
    @GET("/worker/one")
    Call<Worker> getWorkerInfo(@Query("worker_id") String workerId);


    /**
     * 托管员工工作
     *
     * @param fromWorkerId 需要离开的员工id
     * @param toWorkerId   接管工作的员工id
     */
    @PATCH("/worker/workstatus")
    Call<ResponseBody> hostWork(@Query("from_worker_id") String fromWorkerId,
                                @Query("to_worker_id") String toWorkerId);
}
