package com.chuansongmen.worker.sendget;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.data.bean.Order;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendGetListAdapter extends RecyclerView.Adapter<SendGetListAdapter.SendGetViewHolder> {
    private ArrayList<Order> orders;
    private sendgetItemOnClickListener listener;

    public SendGetListAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public SendGetListAdapter(ArrayList<Order> orders,
                              sendgetItemOnClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    public void setListener(sendgetItemOnClickListener listener) {
        this.listener = listener;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public SendGetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                int i) {
        return new SendGetViewHolder(View.inflate(viewGroup.getContext(),
                R.layout.worker_send_get_item,
                viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull SendGetViewHolder sendGetViewHolder,
                                 final int i) {
        //todo:重新解析订单数据
        //sendGetViewHolder.sendgetAddress.setText(orders.get(i).getRecipientAddress());
        //sendGetViewHolder.sendgetArea.setText(orders.get(i).getRecipientAddress());
        sendGetViewHolder.sendgetName.setText(orders.get(i).getRecipientName());
        sendGetViewHolder.sendgetPhone.setText(orders.get(i).getRecipientPhone());
        sendGetViewHolder.sendgetCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v.getId(), i);
            }
        });
        sendGetViewHolder.sendgetDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v.getId(), i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    class SendGetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sendget_area)
        TextView sendgetArea;
        @BindView(R.id.sendget_address)
        TextView sendgetAddress;
        @BindView(R.id.sendget_name)
        TextView sendgetName;
        @BindView(R.id.sendget_phone)
        TextView sendgetPhone;
        @BindView(R.id.sendget_call)
        Button sendgetCall;
        @BindView(R.id.sendget_detail)
        Button sendgetDetail;

        public SendGetViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface sendgetItemOnClickListener {
        void onClick(@IdRes int id, int position);
    }
}
