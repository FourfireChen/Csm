package com.chuansongmen.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.chuansongmen.R;

import java.util.List;

public abstract class BaseGridAdapter<E> extends BaseAdapter {
    private int gridviewHeight;
    private GridView gridView;
    private ItemOnClickListener<E> listener;
    protected List<E> items;

    public BaseGridAdapter(GridView gridView, List<E> items) {
        this.gridView = gridView;
        this.items = items;
    }

    public void setListener(ItemOnClickListener<E> listener) {
        this.listener = listener;
    }

    protected View fullScreen(View convertView,
                              ViewGroup parent,
                              final int position,
                              @LayoutRes int id) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.worker_main_item, null);
        }
        //此处设置每个view的高度，让它铺满整个gridview
        if (gridviewHeight == 0){
            gridviewHeight = gridView.getHeight();
        }
        AbsListView.LayoutParams layoutParams =
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        gridviewHeight / 3);
        convertView.setLayoutParams(layoutParams);

        if (listener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(items.get(position));
                }
            });
        }

        return convertView;
    }

    public interface ItemOnClickListener<T> {
        void onClick(T item);
    }
}
