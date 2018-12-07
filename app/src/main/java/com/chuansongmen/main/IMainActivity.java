package com.chuansongmen.main;

import com.chuansongmen.data.bean.Order;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

interface IMainActivity {
    DrawerLayout getDrawerLayout();

    CardView getBottom();

    interface IMainView {
        void changeWorkderStatus(boolean status);

        void refreshList(List<List<Order>> data);
    }
}
