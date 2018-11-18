package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRemoteData {

    /**
     * 更新订单数据
     *
     * @param demandOrderStr 能确定目标订单的信息，以;分割，如“status=1;now_worker=123213”
     * @param targetOrderStr 要修改的信息，以;分割，如“status=2;now_worker=2132131”
     * @return 是否修改成功
     */
    @PATCH("/order/")
    Call<Boolean> updateOrder(@Query("demand_order_str") String demandOrderStr,
                              @Query("target_order_str") String targetOrderStr);


    /**
     * 查找当前订单的位置
     *
     * @param orderId 要查询的订单号
     * @return 订单位置
     */
    @GET("/order/location")
    Call<Position> queryOrderPos(@Query("order_paper_id") int orderId);


    /**
     * 获取与某个员工绑定的订单
     *
     * @param workerId 要查找的员工的id
     * @return 返回所有与该员工有关的订单，如果是null，查询失败；如果list为空，数据库中没有结果
     */
    @GET("order/one")
    Call<List<Order>> getWorkerOrders(@Query("userId") int workerId);


    /**
     * 上传小米推送中生成的设备id
     *
     * @param workerId 要上传的员工id
     * @param regId    设备id
     * @return 是否上传成功
     */
    @PATCH("/worker/reg")
    Call<Boolean> uploadForPush(@Query("worker_id") int workerId, @Query("reg_id") String regId);


    /**
     * 上传员工当前位置
     *
     * @param workerId  要上传的员工id
     * @param longitude 当前经度
     * @param latitude  当前纬度
     * @return 是否上传成功
     */
    @PATCH("/worker/location")
    Call<Boolean> uploadPos(@Query("worker_id") int workerId,
                            @Query("longitude") double longitude,
                            @Query("latitude") double latitude);

    /**
     * 改变员工工作状态，就是上下班
     *
     * @param workerId 该要变的员工id
     * @param status   新的状态
     *                 0：表示下班
     *                 1：表示上班
     * @return 是否改变成功
     */
    @PATCH("worker/status")
    Call<ResponseBody> updateWorkerStatus(@Query("workerId") Integer workerId,
                                     @Query("status") Integer status);

    /**
     * 获取员工信息
     *
     * @param workerId 要获取的员工的Id/手机号码
     * @return 查询到的员工信息
     */
    @GET("/worker/one")
    Call<Worker> getWorkerInfo(@Query("worker_id") int workerId);


    /**
     * 获取所有的路径信息
     *
     * @return 获取到的所有路径
     */
    @GET("/route")
    Call<List<Route>> getAllRoutes();


    /**
     * 测试添加员工
     */
    // TODO: 2018/11/17 测试接口，记得删除
    @POST("worker")
    Call<ResponseBody> addMan(@Body RequestBody body);
}
