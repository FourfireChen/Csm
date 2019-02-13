package com.chuansongmen.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Worker implements Parcelable {
    public static final Parcelable.Creator<Worker> CREATOR = new Parcelable.Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel source) {
            return new Worker(source);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };
    private static Worker worker;
    /**
     * 员工id/电话号码
     */
    private String id;
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
    /**
     * 从菜鸟的派单量
     */
    private int collctRookieNum;

    public Worker() {
    }

    private Worker(String id,
                   String name,
                   int sex,
                   boolean isWorking,
                   String regId,
                   Position now,
                   Category category,
                   Station belongStation,
                   int collectNum,
                   int sendNum,
                   int collctRookieNum) {
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
        this.collctRookieNum = collctRookieNum;
    }

    protected Worker(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.sex = in.readInt();
        this.isWorking = in.readByte() != 0;
        this.regId = in.readString();
        this.now = in.readParcelable(Position.class.getClassLoader());
        int tmpCategory = in.readInt();
        this.category = tmpCategory == -1 ? null : Category.values()[tmpCategory];
        this.belongStation = in.readParcelable(Station.class.getClassLoader());
        this.collectNum = in.readInt();
        this.sendNum = in.readInt();
        this.collctRookieNum = in.readInt();
    }

    public static Worker getInstance() {
        if (worker == null)
            worker = new Worker();
        return worker;
    }

    public String getId() {
        return id;
    }

    public Worker setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Worker setName(String name) {
        this.name = name;
        return this;
    }

    public int getSex() {
        return sex;
    }

    public Worker setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public Worker setWorking(boolean working) {
        isWorking = working;
        return this;
    }

    public String getRegId() {
        return regId;
    }

    public Worker setRegId(String regId) {
        this.regId = regId;
        return this;
    }

    public Position getNow() {
        return now;
    }

    public Worker setNow(Position now) {
        this.now = now;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Worker setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Station getBelongStation() {
        return belongStation;
    }

    public Worker setBelongStation(Station belongStation) {
        this.belongStation = belongStation;
        return this;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public Worker setCollectNum(int collectNum) {
        this.collectNum = collectNum;
        return this;
    }

    public int getSendNum() {
        return sendNum;
    }

    public Worker setSendNum(int sendNum) {
        this.sendNum = sendNum;
        return this;
    }

    public int getCollctRookieNum() {
        return collctRookieNum;
    }

    public Worker setCollctRookieNum(int collctRookieNum) {
        this.collctRookieNum = collctRookieNum;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.sex);
        dest.writeByte(this.isWorking ? (byte) 1 : (byte) 0);
        dest.writeString(this.regId);
        dest.writeParcelable(this.now, flags);
        dest.writeInt(this.category == null ? -1 : this.category.ordinal());
        dest.writeParcelable(this.belongStation, flags);
        dest.writeInt(this.collectNum);
        dest.writeInt(this.sendNum);
        dest.writeInt(this.collctRookieNum);
    }

    public enum Category {
        STATION_MASTER("站长"), DRIVER("司机"), RIDER("收派员");
        private String name;

        Category(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
