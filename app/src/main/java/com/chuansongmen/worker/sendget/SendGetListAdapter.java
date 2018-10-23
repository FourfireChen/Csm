package com.chuansongmen.worker.sendget;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.chuansongmen.R;
import com.chuansongmen.data.bean.Task;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SendGetListAdapter extends RecyclerView.Adapter<SendGetListAdapter.SendGetViewHolder> {
    private ArrayList<Task> tasks;
    private sendgetItemOnClickListener listener;

    public SendGetListAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public SendGetListAdapter(ArrayList<Task> tasks,
                              sendgetItemOnClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    public void setListener(sendgetItemOnClickListener listener) {
        this.listener = listener;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
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
        sendGetViewHolder.sendgetAddress.setText(tasks.get(i).getSpecificAddress());
        sendGetViewHolder.sendgetArea.setText(tasks.get(i).getArea());
        sendGetViewHolder.sendgetName.setText(tasks.get(i).getClientName());
        sendGetViewHolder.sendgetPhone.setText(tasks.get(i).getPhoneNumber());
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
        return tasks == null ? 0 : tasks.size();
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
