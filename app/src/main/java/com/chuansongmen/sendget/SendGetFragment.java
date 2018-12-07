package com.chuansongmen.sendget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.detail.DetailActivity;
import com.chuansongmen.detail.DetailAdapter;

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
        sendgetLists.setAdapter(adapter);
        adapter.setItemCallButtonListener(new SendGetListAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "打电话按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });
        sendgetLists.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

    public void refreshList(final List<Order> orders) {
        adapter.setItemClickListener(new SendGetListAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle data = new Bundle();
                data.putParcelable(getString(R.string.ORDER), orders.get(position));
                startActivity(DetailActivity.class, data);
            }
        });
        adapter.setOrders(orders);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
