package com.chuansongmen.worker.main;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.data.MainItem;
import com.chuansongmen.util.Util;

import java.util.List;

import static android.widget.RelativeLayout.CENTER_VERTICAL;
import static android.widget.RelativeLayout.END_OF;
import static com.chuansongmen.data.MainItem.DETAINED_GOOD;
import static com.chuansongmen.data.MainItem.KEY_GOOD;
import static com.chuansongmen.data.MainItem.RECEIPT;
import static com.chuansongmen.data.MainItem.STAR;

public class MainGridAdapter extends BaseAdapter {
    private GridView gridView;
    private int gridviewHeight;
    private List<MainItem> mainItems;
    private WorkerPageItemsClickListener listener;

    MainGridAdapter(GridView gridView, List<MainItem> mainItem) {
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
        final MainItem mainItem = mainItems.get(position);

        //对评分和收款这两个选项单独处理,对重点件和滞留件做字体颜色改变
        if (mainItem == STAR || mainItem == RECEIPT) {
            //这是两个选项共性处理部分，将title隐藏掉
            title.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(CENTER_VERTICAL);
            params.addRule(END_OF, R.id.main_item_icon);

            //各自处理
            if (mainItem == RECEIPT) {
                params.setMarginStart(-15);
                mainItem.setValue("收款");
                value.setTextSize(25);
                RelativeLayout.LayoutParams paramsIcon =
                        new RelativeLayout.LayoutParams(icon.getLayoutParams());
                paramsIcon.setMarginStart(5);
                icon.setLayoutParams(paramsIcon);
            } else {
                value.setTextSize(30);
            }

            value.setLayoutParams(params);
        } else if (mainItem == KEY_GOOD) {
            title.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorWarning));
            value.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorWarning));
        } else if (mainItem == DETAINED_GOOD){
            title.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorAccent));
            value.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.colorAccent));
        }

        title.setText(mainItem.getTitle());

        if (mainItem.getValue() != null && !mainItem.getValue().isEmpty()) {
            value.setText(mainItem.getValue());
        }
        if (mainItem.getIcon() != null) {
            icon.setImageDrawable(mainItem.getIcon());
        }
        //改字体
        Util.setTypeface("fonts/type.ttf", parent.getContext().getAssets(), title, value);


        if (listener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(mainItem);
                }
            });
        }

        return convertView;
    }

    public void updateValue(MainItem item, String newValue) {
        mainItems.get(mainItems.indexOf(item)).setValue(newValue);
        notifyDataSetChanged();
    }

    interface WorkerPageItemsClickListener {
        void onClick(MainItem item);
    }

}
