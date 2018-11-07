package com.chuansongmen.data;

import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Position;
import com.chuansongmen.data.bean.Route;
import com.chuansongmen.data.bean.Worker;

import java.util.List;

import androidx.lifecycle.LiveData;

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
    boolean updateOrder(String demandOrderStr,
                     String targetOrderStr);


    /**
     * 查找当前订单的位置
     *
     * @param order            要查找的订单
     * @return 订单位置
     */
    Position queryOrderPos(Order order);


    /**
     * 获取与某个员工绑定的订单
     *
     * @param workerId 要查找的员工的id
     * @return 返回的是获取的订单
     */
    List<Order> getWorkerOrders(int workerId);


    /**
     * 上传小米推送中生成的设备id
     *
     * @param workerId 要上传的员工id
     * @param regId    设备id
     * @return    上传结果
     */
    boolean uploadForPush(int workerId, String regId);


    /**
     * 上传员工当前位置
     *
     * @param workerId 要上传的员工id
     * @param position 当前位置
     * @return 上传结果
     */
    boolean uploadPos(int workerId, Position position);

    /**
     * 改变员工工作状态，就是上下班
     *
     * @param workerId           该要变的员工id
     * @param status             新的状态
     *                           0：表示下班
     *                           1：表示上班
     * @return  改变的结果
     */
    boolean updateWorkerStatus(int workerId, int status);

    /**
     * 获取员工信息
     *
     * @param workerId 要获取的员工的Id
     * @return 获取的员工的信息
     */
    Worker getWorkerInfo(int workerId);

    /**
     * 获取所有路线信息
     */
    List<Route> getAllRoute();

    /**
     * 停止所有正在进行的操作
     */
    void stopAll();
}
