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

class SendGetListAdapter extends RecyclerView.Adapter<SendGetListAdapter.SendGetViewHolder> {

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
        sendGetViewHolder.sendGetItemAddress.setText(order.getRecipientAddress());
        sendGetViewHolder.sendGetItemName.setText(order.getRecipientName());
        sendGetViewHolder.sendGetItemPhoneNumber.setText(order.getRecipientPhone());
        sendGetViewHolder.sendGetItemCallButton.setOnClickListener(v -> {
            if (itemCallButtonListener != null)
                itemCallButtonListener.onClick(i);
        });
        sendGetViewHolder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    class SendGetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sendget_item_address)
        TextView sendGetItemAddress;
        @BindView(R.id.sendget_item_name)
        TextView sendGetItemName;
        @BindView(R.id.sendget_item_call_button)
        Button sendGetItemCallButton;
        @BindView(R.id.sendget_item_phonenumber)
        TextView sendGetItemPhoneNumber;

        SendGetViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    interface ItemClickListener {
        void onClick(int position);
    }
}
