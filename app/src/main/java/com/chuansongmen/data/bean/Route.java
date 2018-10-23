package com.chuansongmen.data.bean;

public class Route {
    private int id;

    /**
     * 司机Id
     */
    private int workerId;

    /**
     * 司机名
     */
    private String workerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
