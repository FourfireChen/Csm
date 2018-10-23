package com.chuansongmen.data.bean;

public class Task {
    private String clientName;
    private String area;
    private String specificAddress;
    private String time;
    private String phoneNumber;

    public Task(String clientName,
                String area,
                String specificAddress,
                String time,
                String phoneNumber) {
        this.clientName = clientName;
        this.area = area;
        this.specificAddress = specificAddress;
        this.time = time;
        this.phoneNumber = phoneNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
