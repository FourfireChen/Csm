package com.chuansongmen.sendget;

import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_get_activity);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            throw new NullPointerException("必须传入该Activity的类型，是派件页还是收件页");
        }
        String type = bundle.getString(getString(R.string.TYPE));
        if (type == null || type.isEmpty())
            throw new NullPointerException("必须传入该Activity的类型，是派件页还是收件页");

        //分别初始化
        if (type.equals(getString(R.string.GET)) || type.equals(getString(R.string.UNLOAD))) {
            initGet();
        } else if (type.equals(getString(R.string.SEND))) {
            initSend();
        }

        if (type.equals(getString(R.string.UNLOAD))) {
            //todo:翻到已收件页
        }

        //做一些相同的初始化工作
        initCommon();
    }

    @Override
    protected void initView() {

    }

    private void initCommon() {
        getsendViewpager.setAdapter(new SendGetViewPageAdapter(getSupportFragmentManager(),
                sendGetFragments,
                titles));
        getsendTabs.setupWithViewPager(getsendViewpager);
        //通过这个方法设置标题和小红点
        //getsendTabs.getTabAt(0).setCustomView()
    }

    private void initGet() {
        titles = new String[]{"待收件", "已收件", "重点件", "滞留收件"};
    }

    private void initSend() {
        titles = new String[]{"待派件", "已派件", "重点件", "滞留派件"};
    }

}
