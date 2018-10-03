package com.chuansongmen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.util.Util;
import com.chuansongmen.worker.scan.ScanActivity;
import com.chuansongmen.worker.main.WorkerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_bottom_left)
    TextView mainBottomLeft;
    @BindView(R.id.main_bottom_center)
    ImageView mainBottomCenter;
    @BindView(R.id.main_bottom_right)
    TextView mainBottomRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        initBottomNavigation();
        initFragment();
    }

    private void initBottomNavigation() {
        Util.setTypeface("fonts/type.ttf", getAssets(), mainBottomLeft, mainBottomRight);
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_fragment_container, new WorkerFragment()).commit();

    }

    @OnClick({R.id.main_bottom_left, R.id.main_bottom_center, R.id.main_bottom_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_bottom_left:
                //todo:打开收件页
                break;
            case R.id.main_bottom_center:
                //todo:打开通用相机
                startActivity(ScanActivity.class, null);
                break;
            case R.id.main_bottom_right:
                //todo:打开派件页
                break;
        }
    }
}
