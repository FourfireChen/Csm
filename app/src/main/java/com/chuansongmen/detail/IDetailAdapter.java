package com.chuansongmen.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;

public interface IDetailAdapter {
    Customer getFrom();

    Customer getTo();

    String getStatus();

    String getRemark();

    String getOrderId();

    String getSerialId();

    List<ButtonStatus> getButtonStatus(Context context);

    class Customer {
        private String name;
        private String phone;
        private String address;

        public Customer() {
        }

        public Customer(String name, String phone, String address) {
            this.name = name;
            this.phone = phone;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    class ButtonStatus {
        private Drawable icon;
        private String text;
        private View.OnClickListener onClickListener;

        public ButtonStatus() {
        }

        public ButtonStatus(Drawable icon, String text, View.OnClickListener onClickListener) {
            this.icon = icon;
            this.text = text;
            this.onClickListener = onClickListener;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public View.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }
    }
}
