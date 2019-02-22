package com.chuansongmen.sendget;

import com.chuansongmen.R;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.base.BaseAdapter;
import com.chuansongmen.base.BaseViewHolder;

public class SendGetAdapter extends BaseAdapter<Order> {
    private ItemClickListener itemCallButtonListener;
    private ItemClickListener itemClickListener;

    public SendGetAdapter(int itemResId) {
        super(itemResId);
    }

    void setItemCallButtonListener(ItemClickListener itemCallButtonListener) {
        this.itemCallButtonListener = itemCallButtonListener;
    }

    void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int viewType, int position, Order item) {
        super.onBind(holder, viewType, position, item);
        holder.getTextView(R.id.sendget_item_address).setText(item.getRecipientAddress());
        holder.getTextView(R.id.sendget_item_name).setText(item.getRecipientName());
        holder.getTextView(R.id.sendget_item_phonenumber).setText(item.getRecipientPhone());
        holder.getButton(R.id.sendget_item_call_button).setOnClickListener(v -> {
            if (itemCallButtonListener != null)
                itemCallButtonListener.onClick(position);
        });
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onClick(position);
            }
        });
    }

    interface ItemClickListener {
        void onClick(int position);
    }
}
