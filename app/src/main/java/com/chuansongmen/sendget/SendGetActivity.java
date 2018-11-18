package com.chuansongmen.sendget;

import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个Activity复用，员工端点击下导航左右两个按钮都是打开这个Activity，通过传进来的判断是收件还是派件
 */

public class SendGetActivity extends BaseActivity<SendGetViewModel> {
    private static final int POINT_ORDER_PRICE = 10;
    @BindView(R.id.sendget_tabs)
    TabLayout getsendTabs;
    @BindView(R.id.sendget_viewpager)
    ViewPager getsendViewpager;
    private List<SendGetFragment> sendGetFragments = new ArrayList<>();

    private String[] titles;

    private String type;

    private SendGetViewPageAdapter adapter =
            new SendGetViewPageAdapter(getSupportFragmentManager());

    //将所有订单拉下来之后，切成8组，4组收件，4组派件，这个是要确定是收件还是派件的。
    private int ordersBeginIndex, ordersEndIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_get_activity);
        ButterKnife.bind(this);
        type();
        initView();
    }

    private void type() {
        //做健壮判断
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("必须传入该Activity的类型，是派件页还是收件页");
        }
        type = bundle.getString(getString(R.string.TYPE));
        if (type == null || type.isEmpty())
            throw new NullPointerException("必须传入该Activity的类型，是派件页还是收件页");

        if (type.equals(getString(R.string.GET)) || type.equals(getString(R.string.UNLOAD))) {
            titles = new String[]{"待收件", "已收件", "重点件", "滞留收件"};
            ordersBeginIndex = 0;
            ordersEndIndex = 4;
        } else if (type.equals(getString(R.string.SEND))) {
            titles = new String[]{"待派件", "已派件", "重点件", "滞留派件"};
            ordersBeginIndex = 4;
            ordersEndIndex = 8;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 4; i++) {
            sendGetFragments.add(new SendGetFragment());
        }
        adapter.setTitles(titles);
        adapter.setFragments(sendGetFragments);
        getsendViewpager.setAdapter(adapter);
        getsendTabs.setupWithViewPager(getsendViewpager);

        viewModel.getOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if (orders != null) {

                    //通过这个方法设置标题和小红点
                    //getsendTabs.getTabAt(0).setCustomView()
                    adapter.notifyDataSetChanged();

                    showOrders(orders);

                    if (type.equals(getString(R.string.UNLOAD))) {
                        getsendViewpager.setCurrentItem(1);
                    }
                }
            }
        });

    }

    private void showOrders(List<Order> orders) {
        List<List<Order>> o = classifyOrders(orders);

        for (int i = ordersBeginIndex; i < ordersEndIndex; i++) {
            sendGetFragments.get(i).showOrders(o.get(i));
        }
    }

    private List<List<Order>> classifyOrders(List<Order> allOrders) {
        List<List<Order>> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(new ArrayList<Order>());
        }
        for (Order order :
                allOrders) {
            int index = 0;
            switch (order.getStatus()) {
                case NON_PICK_UP:
                case SENDING:
                    if (order.isDelay()) {
                        index = 3;
                    } else if (order.getPrice() > POINT_ORDER_PRICE) {
                        index = 2;
                    } else {
                        index = 0;
                    }
                    break;
                case HAS_PICKED_UP:
                case HAS_SENDED:
                    index = 1;
                    break;
            }
            result.get(index).add(order);
        }
        return result;
    }
}
