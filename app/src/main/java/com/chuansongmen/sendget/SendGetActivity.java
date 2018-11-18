package com.chuansongmen.sendget;

import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个Activity复用，员工端点击下导航左右两个按钮都是打开这个Activity，通过传进来的判断是收件还是派件
 */

public class SendGetActivity extends BaseActivity<SendGetViewModel> {

    @BindView(R.id.sendget_tabs)
    TabLayout getsendTabs;
    @BindView(R.id.sendget_viewpager)
    ViewPager getsendViewpager;
    private List<SendGetFragment> sendGetFragments = new ArrayList<>();

    private String[] titles;

    private List<List<Order>> orders = new ArrayList<>();


    private SendGetViewPageAdapter adapter =
            new SendGetViewPageAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_get_activity);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        for (int i = 0; i < 4; i++) {
            orders.add(bundle.<Order>getParcelableArrayList(String.valueOf(i)));
        }
        titles = bundle.getStringArray(getString(R.string.TITLE));
    }


    @Override
    protected void initView() {
        super.initView();
        for (int i = 0; i < 4; i++) {
            SendGetFragment fragment = new SendGetFragment();
            Bundle data = new Bundle();
            data.putParcelableArrayList(getString(R.string.ORDER),
                    (ArrayList<Order>) orders.get(i));
            fragment.setArguments(data);
            sendGetFragments.add(fragment);
        }
        adapter.setTitles(titles);
        adapter.setFragments(sendGetFragments);
        getsendViewpager.setAdapter(adapter);
        getsendTabs.setupWithViewPager(getsendViewpager);
    }
}
