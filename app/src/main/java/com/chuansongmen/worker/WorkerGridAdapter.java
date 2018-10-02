package com.chuansongmen.worker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.MainItem;

import java.util.List;

public class WorkerGridAdapter extends BaseAdapter {
    private GridView gridView;
    private int gridviewHeight;
    private List<MainItem> mainItems;
    private WorkerPageItemsClickListener listener;

    public WorkerGridAdapter(GridView gridView, List<MainItem> mainItem) {
        this.gridView = gridView;
        this.mainItems = mainItem;
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

    public void setListener(WorkerPageItemsClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.main_item, null);
        }
        if (gridviewHeight == 0) {
            gridviewHeight = gridView.getHeight();
        }
        //此处设置每个view的高度，让它铺满整个gridview
        AbsListView.LayoutParams layoutParams =
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        gridviewHeight / 3);
        convertView.setLayoutParams(layoutParams);


        TextView title = convertView.findViewById(R.id.main_item_title);
        TextView value = convertView.findViewById(R.id.main_item_value);
        ImageView icon = convertView.findViewById(R.id.main_item_icon);
        MainItem mainItem = mainItems.get(position);

        title.setText(mainItem.getTitle());

        if (mainItem.getValue() != null && !mainItem.getValue().isEmpty()) {
            value.setText(mainItem.getValue());
        }

        if (mainItem.getIcon() != null) {
            icon.setImageDrawable(mainItem.getIcon());
        }

        if (listener != null) {
            listener.onClick(mainItem);
        }

        return convertView;
    }

    interface WorkerPageItemsClickListener {
        void onClick(MainItem item);
    }
}
