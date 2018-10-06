package com.chuansongmen.driver;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chuansongmen.data.MainItem;

import java.util.List;

public class MainGridAdapter extends BaseAdapter {
    private List<MainItem> mainItems;

    public MainGridAdapter(List<MainItem> mainItems) {
        this.mainItems = mainItems;
    }

    public List<MainItem> getMainItems() {
        return mainItems;
    }

    public void setMainItems(List<MainItem> mainItems) {
        this.mainItems = mainItems;
    }

    @Override
    public int getCount() {
        return mainItems == null ? 0 : mainItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mainItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}
