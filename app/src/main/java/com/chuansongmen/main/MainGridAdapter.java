package com.chuansongmen.main;

import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.common.MainItem;
import com.chuansongmen.util.Util;

import java.util.List;

import static android.widget.RelativeLayout.CENTER_VERTICAL;
import static android.widget.RelativeLayout.END_OF;
import static com.chuansongmen.common.MainItem.RECEIPT;

public class MainGridAdapter extends BaseAdapter {
    private List<MainItem> mainItems;
    private int gridviewHeight;
    private GridView gridView;
    private ItemOnClickListener<MainItem> listener;

    public MainGridAdapter(GridView gridView, List<MainItem> mainItems) {
        this.mainItems = mainItems;
        this.gridView = gridView;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //使GridView满屏
        convertView = fullScreen(convertView, parent, position, R.layout.main_item);
        //设置监听
        if (listener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(mainItems.get(position));
                }
            });
        }


        TextView title = convertView.findViewById(R.id.main_item_title);
        TextView value = convertView.findViewById(R.id.main_item_value);
        ImageView icon = convertView.findViewById(R.id.main_item_icon);
        final MainItem mainItem = mainItems.get(position);


        switch (mainItem) {
            case RECEIPT:
            case STAR: {
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
                break;
            }
            case KEY_GOOD: {
                title.setTextColor(ContextCompat.getColor(parent.getContext(),
                        R.color.colorWarning));
                value.setTextColor(ContextCompat.getColor(parent.getContext(),
                        R.color.colorWarning));
                break;
            }
            case DETAINED_GOOD: {
                title.setTextColor(ContextCompat.getColor(parent.getContext(),
                        R.color.colorAccent));
                value.setTextColor(ContextCompat.getColor(parent.getContext(),
                        R.color.colorAccent));
                break;
            }
            case RUNNING: {
                value.setTextSize(18);
                break;
            }
            case EMERGENCY_CONTACT: {
                RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(END_OF, R.id.main_item_icon);
                title.setLayoutParams(params);
                break;
            }
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

        return convertView;
    }

    private View fullScreen(View convertView,
                            ViewGroup parent,
                            final int position,
                            @LayoutRes int id) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.main_item, null);
        }
        //此处设置每个view的高度，让它铺满整个gridview
        if (gridviewHeight == 0) {
            gridviewHeight = gridView.getHeight();
        }
        AbsListView.LayoutParams layoutParams =
                new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        gridviewHeight / 3);
        convertView.setLayoutParams(layoutParams);

        return convertView;
    }

    public void updateValue(MainItem item, String newValue) {
        mainItems.get(mainItems.indexOf(item)).setValue(newValue);
        notifyDataSetChanged();
    }

    public void setListener(ItemOnClickListener<MainItem> listener) {
        this.listener = listener;
    }

    public interface ItemOnClickListener<T> {
        void onClick(T item);
    }
}
