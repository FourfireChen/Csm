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
    private boolean isWorking;

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
     * 分类：0：站长
     * 1：司机
     * 2：收派员
     */
    private Category category;

    /**
     * 该员工所属的站点名称
     */
    private Station belongStation;

    /**
     * 收派员收单量、派单量
     */
    private int collectNum, sendNum;

    private static Worker worker;

    private Worker(int id,
                   String name,
                   int sex,
                   boolean isWorking,
                   String regId,
                   Position now,
                   Category category,
                   Station belongStation,
                   int collectNum,
                   int sendNum) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.isWorking = isWorking;
        this.regId = regId;
        this.now = now;
        this.category = category;
        this.belongStation = belongStation;
        this.collectNum = collectNum;
        this.sendNum = sendNum;
    }

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

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Station getBelongStation() {
        return belongStation;
    }

    public void setBelongStation(Station belongStation) {
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

    public static class WorkerBuilder {
        private int id;
        private String name;
        private int sex;
        private boolean status;
        private String regId;
        private Position now;
        private Category category;
        private Station belongStation;
        private int collectNum, sendNum;

        public WorkerBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public WorkerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public WorkerBuilder setSex(int sex) {
            this.sex = sex;
            return this;
        }

        public WorkerBuilder setWorked(boolean status) {
            this.status = status;
            return this;
        }

        public WorkerBuilder setRegId(String regId) {
            this.regId = regId;
            return this;
        }

        public WorkerBuilder setNow(Position now) {
            this.now = now;
            return this;
        }

        public WorkerBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public WorkerBuilder setBelongStation(Station belongStation) {
            this.belongStation = belongStation;
            return this;
        }

        public WorkerBuilder setCollectNum(int collectNum) {
            this.collectNum = collectNum;
            return this;
        }

        public WorkerBuilder setSendNum(int sendNum) {
            this.sendNum = sendNum;
            return this;
        }

        public Worker build() {
            if (worker == null)
                worker = new Worker(id,
                        name,
                        sex,
                        status,
                        regId,
                        now,
                        category,
                        belongStation,
                        collectNum,
                        sendNum);
            return worker;
        }
    }

    public enum Category {
        SATATION_MASTER, DRIVER, RIDER
    }
}
