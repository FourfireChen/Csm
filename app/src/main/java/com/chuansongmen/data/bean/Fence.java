package com.chuansongmen.data.bean;

/**
 * 这个类描述的是高德地图中对一定区域内的地理围栏描述
 */
public class Fence {
    /**
     * 围栏名称
     */
    private String id;

    /**
     * 围栏全局id
     */
    private String gid;

    /**
     * 对应的员工id, 名
     */
    private int workerId;
    private String workerName;

    /**
     * 对应站点名
     */
    private String stationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
