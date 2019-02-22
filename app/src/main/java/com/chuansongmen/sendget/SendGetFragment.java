package com.chuansongmen.sendget;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.detail.DetailActivity;
import com.chuansongmen.main.MainFragment;
import com.chuansongmen.util.CallUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SendGetFragment extends BaseFragment<SendGetViewModel> {
    @BindView(R.id.sendget_lists)
    RecyclerView sendGetLists;
    @BindView(R.id.sendget_progress)
    ProgressBar sendGetProgress;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private SendGetAdapter adapter = new SendGetAdapter(R.layout.send_get_item);
    private Unbinder unbinder;
    private Handler closeRefresh = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
                toast("刷新失败,请检查网络");
            }
            return true;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_get_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        sendGetLists.setAdapter(adapter);
        sendGetLists.setLayoutManager(new LinearLayoutManager(container.getContext()));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentByTag(getString(R.string.MAIN_TAG));
                if (mainFragment != null) {
                    // 这里要重新通知回mainFragment，因为数据都在那边，保证数据同步性
                    mainFragment.updateOrders();
                    closeRefresh.sendEmptyMessageDelayed(0, 5000);
                }
            }
        });
        return view;
    }

    public void showOrders(final List<Order> orders) {
        // 如果正在刷新，就关掉，刷新完成
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        adapter.setItemCallButtonListener(new SendGetAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                CallUtil.call(getContext().getApplicationContext(),
                        orders.get(position).getRecipientPhone());
            }
        });

        adapter.setItemClickListener(new SendGetAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle data = new Bundle();
                data.putParcelable(getString(R.string.order), orders.get(position));
                startActivity(DetailActivity.class, data);
            }
        });
        adapter.setData(orders);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
