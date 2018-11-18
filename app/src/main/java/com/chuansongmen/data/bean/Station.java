package com.chuansongmen.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable {
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

    public Station() {
    }

    public Station(String id) {
        this.id = id;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeParcelable(this.position, flags);
        dest.writeInt(this.workerId);
    }

    protected Station(Parcel in) {
        this.id = in.readString();
        this.position = in.readParcelable(Position.class.getClassLoader());
        this.workerId = in.readInt();
    }

    public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel source) {
            return new Station(source);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };
}
