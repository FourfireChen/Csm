package com.chuansongmen.data.bean;

public class Station {
    /**
     * 站点Id，也是名称
     */
    private String id;

    /**
     * 站点经纬度
     */
    private Position position;

    /**
     * 站点员工id
     */
    private int workerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
}
