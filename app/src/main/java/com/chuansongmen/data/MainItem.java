package com.chuansongmen.data;

import android.graphics.drawable.Drawable;

public enum MainItem {
    SEND_TODAY("今日收件"),
    GET_TODAY("今日派件"),
    KEY_GOOD("重点件"),
    DETAINED_GOOD("滞留件"),
    TO_SEND("本轮待派"),
    TO_GET("本轮待收"),
    TO_UNLOAD("回站待卸"),
    NEXT_ARRIVAL_TIME("下个到站时间"),
    NEXT_SITE("下一个站点"),
    EMERGENCY_CONTACT("紧急联系"),
    NEXT_DEPATRUE_TIME("下个发车时间"),
    NEXT_HANDOVER("下个交接员"),
    RUNNING("运行过程中"),
    RECEIPT("收款"),
    STAR("评分");

    private String title;
    private String value;
    private Drawable icon;

    MainItem(String title) {
        this.title = title;
        this.value = "0";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
