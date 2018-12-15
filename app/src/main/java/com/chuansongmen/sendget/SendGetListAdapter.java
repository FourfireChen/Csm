package com.chuansongmen.sendget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.data.bean.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SendGetListAdapter extends RecyclerView.Adapter<SendGetListAdapter.SendGetViewHolder> {

    private List<Order> orders;
    private ItemClickListener itemCallButtonListener;
    private ItemClickListener itemClickListener;

    SendGetListAdapter() {
    }

    SendGetListAdapter(List<Order> orders) {
        this.orders = orders;
    }


    void setItemCallButtonListener(ItemClickListener itemCallButtonListener) {
        this.itemCallButtonListener = itemCallButtonListener;
    }

    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
        sendGetViewHolder.sendgetItemAddress.setText(order.getRecipientAddress());
        sendGetViewHolder.sendgetItemName.setText(order.getRecipientName());
        sendGetViewHolder.sendgetItemPhonenumber.setText(order.getRecipientPhone());
        sendGetViewHolder.sendgetItemCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCallButtonListener != null)
                    itemCallButtonListener.onClick(i);
            }
        });
        sendGetViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(i);
                }
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
        void onClick(int position);
    }
}
