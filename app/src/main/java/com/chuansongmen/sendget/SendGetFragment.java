package com.chuansongmen.sendget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.data.bean.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SendGetFragment extends BaseFragment<SendGetViewModel> {
    @BindView(R.id.sendget_lists)
    RecyclerView sendgetLists;
    @BindView(R.id.sendget_progress)
    ProgressBar sendgetProgress;
    private SendGetListAdapter adapter = new SendGetListAdapter();
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_get_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter.setOrders(getArguments().<Order>getParcelableArrayList(getString(R.string.ORDER)));
        sendgetLists.setAdapter(adapter);
        sendgetLists.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
