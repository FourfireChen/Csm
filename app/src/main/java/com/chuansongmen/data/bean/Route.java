package com.chuansongmen.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Route implements Parcelable {
    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
    private String id;
    /**
     * 司机Id
     */
    private int workerId;
    /**
     * 司机名
     */
    private String workerName;

    public Route() {
    }

    public Route(String id) {
        this.id = id;
    }

    protected Route(Parcel in) {
        this.id = in.readString();
        this.workerId = in.readInt();
        this.workerName = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.workerId);
        dest.writeString(this.workerName);
    }
}
