package com.chuansongmen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.util.Util;
import com.chuansongmen.worker.main.MainFragment;
import com.chuansongmen.worker.scan.ScanActivity;
import com.chuansongmen.worker.sendget.SendGetActivity;

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
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.main_bottom_navigation)
    CardView mainBottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initBottomNavigation();
        initFragment();
    }

    private void initBottomNavigation() {
        Util.setTypeface("fonts/type.ttf", getAssets(), mainBottomLeft, mainBottomRight, mainTitle);
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, new MainFragment())
                .commit();

    }

    @OnClick({R.id.main_bottom_left, R.id.main_bottom_center, R.id.main_bottom_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_bottom_left: {
                Bundle choice = new Bundle();
                choice.putString("type", "get");
                startActivity(SendGetActivity.class, choice);
                break;
            }
            case R.id.main_bottom_center: {
                startActivity(ScanActivity.class, null);
                break;
            }
            case R.id.main_bottom_right: {
                Bundle choice = new Bundle();
                choice.putString("type", "send");
                startActivity(SendGetActivity.class, choice);
                break;
            }
        }
    }
}
