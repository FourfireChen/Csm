package com.chuansongmen.worker.sendget;

import androidx.lifecycle.Observer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.data.bean.Order;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SendGetFragment extends BaseFragment<SendGetViewModel> {
    @BindView(R.id.sendget_lists)
    RecyclerView sendgetLists;
    private SendGetListAdapter adapter;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.worker_send_get_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initOrder();

        return view;
    }

    public void initOrder() {
        adapter = new SendGetListAdapter(viewModel.getOrder().getValue(),
                new SendGetListAdapter.sendgetItemOnClickListener() {
                    @Override
                    public void onClick(int id, int position) {
                        //todo:做响应
                    }
                });
        sendgetLists.setAdapter(adapter);
        viewModel.getOrder().observe(this, new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Order> orders) {
                adapter.setOrders(orders);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
