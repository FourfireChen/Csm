package com.chuansongmen.sendget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.detail.DetailActivity;
import com.chuansongmen.util.CallUtil;

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
    RecyclerView sendGetLists;
    @BindView(R.id.sendget_progress)
    ProgressBar sendGetProgress;
    private SendGetListAdapter adapter = new SendGetListAdapter();
    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_get_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        sendGetLists.setAdapter(adapter);
        sendGetLists.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

    public void refreshList(final List<Order> orders) {

        adapter.setItemCallButtonListener(new SendGetListAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                CallUtil.call(getContext(), orders.get(position).getRecipientPhone());
            }
        });

        adapter.setItemClickListener(new SendGetListAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle data = new Bundle();
                data.putParcelable(getString(R.string.order), orders.get(position));
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
