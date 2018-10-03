package com.chuansongmen.worker.sendget;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.data.Task;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SendGetFragment extends BaseFragment<SendGetViewModel> {
    @BindView(R.id.sendget_lists)
    RecyclerView sendgetLists;
    private SendGetListAdapter adapter;
    Unbinder unbinder;

    public SendGetFragment() {
        super(SendGetViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.worker_send_get_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTasks();

        return view;
    }

    public void initTasks() {
        adapter = new SendGetListAdapter(viewModel.getTasks().getValue(),
                new SendGetListAdapter.sendgetItemOnClickListener() {
                    @Override
                    public void onClick(int id, int position) {
                        //todo:做响应
                    }
                });
        sendgetLists.setAdapter(adapter);
        viewModel.getTasks().observe(this, new Observer<ArrayList<Task>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Task> tasks) {
                adapter.setTasks(tasks);
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
