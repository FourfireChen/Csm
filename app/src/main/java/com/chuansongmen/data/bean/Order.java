package com.chuansongmen.data.bean;

public class Order {

    /**
     * 流水号
     */
    private int id;

    /**
     * 订单号
     */
    private String pagerId;
    /**
     * 发起订单的用户ID/电话号码
     */
    private int userId;

    /**
     * 收发件的经纬度
     */
    private Position from;
    private Position to;

    /**
     * 当前员工工号
     */
    private int nowWoker;

    /**
     * 订单价格
     */
    private int price;

    /**
     * 订单状态
     * 类别：0：收派员未区间
     * 1：收派员已区间
     * 2：在仓储站
     * 3：正在运输
     * 4：收派员正在派送中
     * 5：收派员收派完毕
     */
    private int status;

    /**
     * 是否滞留
     */
    private int isDelay;

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
     * 取件时间
     * todo:说明一下格式
     */
    private String receiveTime;

    /**
     * 寄件类别
     * 值：
     */
    private int category;

    /**
     * 途径的中转站点信息
     * 以;分割：A1;B2;C3
     */
    private String station;

    /**
     * 路线信息拼成的字符串，表示该商品要经过的所有路线
     * 也以;分割
     */
    private String route;

    /**
     * 该商品的下条路线
     */
    private String nextRoute;

    /**
     * 优惠卷id
     */
    private int couponId;

    /**
     * 收派员给的备注
     */
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPagerId() {
        return pagerId;
    }

    public void setPagerId(String pagerId) {
        this.pagerId = pagerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getNowWoker() {
        return nowWoker;
    }

    public void setNowWoker(int nowWoker) {
        this.nowWoker = nowWoker;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(int isDelay) {
        this.isDelay = isDelay;
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

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getNextRoute() {
        return nextRoute;
    }

    public void setNextRoute(String nextRoute) {
        this.nextRoute = nextRoute;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
