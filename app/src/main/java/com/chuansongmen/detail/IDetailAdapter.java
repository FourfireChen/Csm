package com.chuansongmen.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;

interface IDetailAdapter {
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

        Customer() {
        }

        Customer(String name, String phone, String address) {
            this.name = name;
            this.phone = phone;
            this.address = address;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        String getPhone() {
            return phone;
        }

        void setPhone(String phone) {
            this.phone = phone;
        }

        String getAddress() {
            return address;
        }

        void setAddress(String address) {
            this.address = address;
        }
    }

    class ButtonStatus {
        private Drawable icon;
        private String text;
        private View.OnClickListener onClickListener;

        ButtonStatus() {
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
