package com.chuansongmen.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    private static final int IMPORTANT_ORDER_PRICE = 15;
    /**
     * 流水号
     */
    private String orderId;
    /**
     * 订单号
     */
    private String pagerId;
    /**
     * 发起订单的用户ID/电话号码
     */
    private String orderUserId;
    /**
     * 发起订单的用户的名字
     */
    private String userName;
    /**
     * 发起订单的用户地址
     */
    private String userAddress;
    /**
     * 收发件的经纬度
     */
    private Position from, to;
    /**
     * 是否在菜鸟
     */
    private boolean isInCainiao;
    /**
     * 当前员工工号
     */
    private String nowWorker;
    /**
     * 订单价格
     */
    private int price;
    /**
     * 订单状态
     * 类别：0：收派员未取件
     * 1：收派员已取件
     * 2：在仓储站
     * 3：正在运输
     * 4：收派员正在派送中
     * 5：收派员收派完毕
     */
    private Status status;
    /**
     * 是否滞留
     */
    private boolean isDelay;
    /**
     * 滞留事件
     */
    private String delayTime;
    /**
     * 是否是重点件
     */
    private boolean isImportant;
    /**
     * 收件人姓名、电话号码（用户Id)、收件人具体地址
     */
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    /**
     * 保价
     */
    private int priceProtection;
    /**
     * 寄件重量
     */
    private int weight;
    /**
     * 下单时间
     */
    private String startTime;
    /**
     * 订单完成时间
     */
    private String completeTime;
    /**
     * 寄件类别
     * 值：
     */
    private int category;
    /**
     * 途径的中转站点信息
     */
    private List<Station> stations;
    /**
     * 路线信息拼成的字符串，表示该商品要经过的所有路线
     * 也以;分割
     */
    private List<Route> routes;
    /**
     * 该商品的下条路线
     */
    private String nextRoute;
    /**
     * 优惠卷id
     */
    private String couponId;
    /**
     * 收派员给的备注
     */
    private String remark;
    /**
     * 快件到达每个站点的时间信息,每个时间以;隔开。
     * 前端部分根据这个字段和快件当前状态生成快递跟踪信息表
     * 总的信息(这个时间和station字段的站点对应)
     */
    private String arriveStationTime;
    /**
     * 收派员从用户手里拿取快件的时间(由服务器生成)
     */
    private String collectFromUserTime;
    /**
     * 收派员从站点拿取快件的时间(由服务器生成)
     */
    private String collectFromStationTime;
    /**
     * 所有的订单跟踪信息(每次改变订单状态修改此字符串)
     */
    private String messageStr;

    public Order() {
    }

    protected Order(Parcel in) {
        this.orderId = in.readString();
        this.pagerId = in.readString();
        this.orderUserId = in.readString();
        this.userName = in.readString();
        this.userAddress = in.readString();
        this.from = in.readParcelable(Position.class.getClassLoader());
        this.to = in.readParcelable(Position.class.getClassLoader());
        this.isInCainiao = in.readByte() != 0;
        this.nowWorker = in.readString();
        this.price = in.readInt();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
        this.isDelay = in.readByte() != 0;
        this.delayTime = in.readString();
        this.isImportant = in.readByte() != 0;
        this.recipientName = in.readString();
        this.recipientPhone = in.readString();
        this.recipientAddress = in.readString();
        this.priceProtection = in.readInt();
        this.weight = in.readInt();
        this.startTime = in.readString();
        this.completeTime = in.readString();
        this.category = in.readInt();
        this.stations = in.createTypedArrayList(Station.CREATOR);
        this.routes = in.createTypedArrayList(Route.CREATOR);
        this.nextRoute = in.readString();
        this.couponId = in.readString();
        this.remark = in.readString();
        this.arriveStationTime = in.readString();
        this.collectFromUserTime = in.readString();
        this.collectFromStationTime = in.readString();
        this.messageStr = in.readString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPagerId() {
        return pagerId;
    }

    public void setPagerId(String pagerId) {
        this.pagerId = pagerId;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public boolean isInCainiao() {
        return isInCainiao;
    }

    public void setInCainiao(boolean inCainiao) {
        isInCainiao = inCainiao;
    }

    public String getNowWorker() {
        return nowWorker;
    }

    public void setNowWorker(String nowWorker) {
        this.nowWorker = nowWorker;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price > IMPORTANT_ORDER_PRICE)
            setImportant(true);
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDelay() {
        return isDelay;
    }

    public void setDelay(boolean delay) {
        isDelay = delay;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public int getPriceProtection() {
        return priceProtection;
    }

    public void setPriceProtection(int priceProtection) {
        this.priceProtection = priceProtection;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getNextRoute() {
        return nextRoute;
    }

    public void setNextRoute(String nextRoute) {
        this.nextRoute = nextRoute;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArriveStationTime() {
        return arriveStationTime;
    }

    public void setArriveStationTime(String arriveStationTime) {
        this.arriveStationTime = arriveStationTime;
    }

    public String getCollectFromUserTime() {
        return collectFromUserTime;
    }

    public void setCollectFromUserTime(String collectFromUserTime) {
        this.collectFromUserTime = collectFromUserTime;
    }

    public String getCollectFromStationTime() {
        return collectFromStationTime;
    }

    public void setCollectFromStationTime(String collectFromStationTime) {
        this.collectFromStationTime = collectFromStationTime;
    }

    public String getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.pagerId);
        dest.writeString(this.orderUserId);
        dest.writeString(this.userName);
        dest.writeString(this.userAddress);
        dest.writeParcelable(this.from, flags);
        dest.writeParcelable(this.to, flags);
        dest.writeByte(this.isInCainiao ? (byte) 1 : (byte) 0);
        dest.writeString(this.nowWorker);
        dest.writeInt(this.price);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeByte(this.isDelay ? (byte) 1 : (byte) 0);
        dest.writeString(this.delayTime);
        dest.writeByte(this.isImportant ? (byte) 1 : (byte) 0);
        dest.writeString(this.recipientName);
        dest.writeString(this.recipientPhone);
        dest.writeString(this.recipientAddress);
        dest.writeInt(this.priceProtection);
        dest.writeInt(this.weight);
        dest.writeString(this.startTime);
        dest.writeString(this.completeTime);
        dest.writeInt(this.category);
        dest.writeTypedList(this.stations);
        dest.writeTypedList(this.routes);
        dest.writeString(this.nextRoute);
        dest.writeString(this.couponId);
        dest.writeString(this.remark);
        dest.writeString(this.arriveStationTime);
        dest.writeString(this.collectFromUserTime);
        dest.writeString(this.collectFromStationTime);
        dest.writeString(this.messageStr);
    }

    public enum Status {
        NON_PICK_UP("未收件"),
        HAS_PICKED_UP("已收件"),
        IN_STATION("在仓库"),
        TRANSPOTING("正在运输"),
        SENDING("正在派送"),
        HAS_SENDED("已派送");
        private String description;

        Status(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
