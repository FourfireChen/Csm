package com.chuansongmen.sendget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.data.bean.Order;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SendGetListAdapter extends RecyclerView.Adapter<SendGetListAdapter.SendGetViewHolder> {

    private List<Order> orders;
    private ItemClickListener itemCallButtonListener;

    SendGetListAdapter() {
    }

    SendGetListAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    SendGetListAdapter(List<Order> orders,
                       ItemClickListener itemCallButtonListener) {
        this.orders = orders;
        this.itemCallButtonListener = itemCallButtonListener;
    }

    void setItemCallButtonListener(ItemClickListener itemCallButtonListener) {
        this.itemCallButtonListener = itemCallButtonListener;
    }

    void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public SendGetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                int i) {
        View view = View.inflate(viewGroup.getContext(),
                R.layout.send_get_item,
                null);
        return new SendGetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendGetViewHolder sendGetViewHolder,
                                 final int i) {
        Order order = orders.get(i);
        /*sendGetViewHolder.sendgetItemAddress.setText(order.getRecipientAddress());
        sendGetViewHolder.sendgetItemArea.setText(order.getRecipientAddress());
        sendGetViewHolder.sendgetItemName.setText(order.getRecipientName());
        sendGetViewHolder.sendgetItemTime.setText(order.getReceiveTime());
        sendGetViewHolder.sendgetItemPhonenumber.setText(order.getRecipientPhone());*/
        sendGetViewHolder.sendgetItemCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCallButtonListener.onClick(v.getId(), i);
            }
        });
        sendGetViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/11/18 跳转activity，订单详情
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    class SendGetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sendget_item_address)
        TextView sendgetItemAddress;
        @BindView(R.id.sendget_item_area)
        TextView sendgetItemArea;
        @BindView(R.id.sendget_item_time)
        TextView sendgetItemTime;
        @BindView(R.id.sendget_item_name)
        TextView sendgetItemName;
        @BindView(R.id.sendget_item_call_button)
        Button sendgetItemCallButton;
        @BindView(R.id.sendget_item_phonenumber)
        TextView sendgetItemPhonenumber;

        SendGetViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface ItemClickListener {
        void onClick(@IdRes int id, int position);
    }
}