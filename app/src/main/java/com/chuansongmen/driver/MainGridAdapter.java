package com.chuansongmen.driver;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseGridAdapter;
import com.chuansongmen.data.MainItem;

import java.util.List;

public class MainGridAdapter extends BaseGridAdapter<MainItem> {
    private List<MainItem> mainItems;

    public MainGridAdapter(GridView gridView,
                           List<MainItem> items) {
        super(gridView, items);
        this.mainItems = items;
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
        convertView = fullScreen(convertView, parent, position, R.layout.driver_main_item);

        return convertView;
    }
}
