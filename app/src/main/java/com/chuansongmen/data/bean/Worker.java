package com.chuansongmen.data.bean;

public class Worker {
    /**
     * 员工id/电话号码
     */
    private int id;

    private String name;

    private int sex;

    /**
     * 当前状态，是否在工作
     */
    private int status;

    /**
     * 小米推送中使用的设备id
     */
    private String regId;

    /**
     * 现在的经纬度
     */
    private Position now;

    /**
     * 员工分类
     * todo:说明有什么分类
     */
    private int category;

    /**
     * 该员工所属的站点名称
     */
    private String belongStation;

    /**
     * 收派员收单量、派单量
     */
    private int collectNum, sendNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Position getNow() {
        return now;
    }

    public void setNow(Position now) {
        this.now = now;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getBelongStation() {
        return belongStation;
    }

    public void setBelongStation(String belongStation) {
        this.belongStation = belongStation;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }
}
