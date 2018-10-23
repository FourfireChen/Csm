package com.chuansongmen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.common.Career;
import com.chuansongmen.driver.DriverMainFragemnt;
import com.chuansongmen.util.Util;
import com.chuansongmen.worker.main.WorkerMainFragment;
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
    @BindView(R.id.main_drawer_head_image)
    ImageView mainDrawerHeadImage;
    @BindView(R.id.main_drawer_head_name)
    TextView mainDrawerHeadName;
    @BindView(R.id.main_drawer_head_career)
    TextView mainDrawerHeadCareer;
    @BindView(R.id.main_drawer_id)
    LinearLayout mainDrawerId;
    @BindView(R.id.main_drawer_workswitch)
    Switch mainDrawerWorkswitch;
    @BindView(R.id.main_drawer_careersound)
    LinearLayout mainDrawerCareersound;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.main_toolbar_me)
    ImageView mainToolbarMe;
    @BindView(R.id.main_toolbar_info)
    ImageView mainToolbarInfo;

    private Career career;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setBottomNavigationTypeface();

        judge();
        initWorker();
        /*
        switch (career) {
            case DRIVER:
                initDriver();
                break;
            case WORKER:
                initWorker();
                break;
            default:
                initWorker();
                break;
        }*/
    }

    private void setBottomNavigationTypeface() {
        Util.setTypeface("fonts/type.ttf", getAssets(), mainBottomLeft, mainBottomRight, mainTitle);
    }

    private void judge() {
        //todo:判断后修改career的值
    }

    private void initWorker() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, new WorkerMainFragment())
                .commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void initDriver() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, new DriverMainFragemnt())
                .commit();
        mainBottomLeft.setText("历史记录");
        mainBottomRight.setText("路线信息");
    }

    @OnClick({R.id.main_bottom_left,
            R.id.main_bottom_center,
            R.id.main_bottom_right,
            R.id.main_toolbar_me,
            R.id.main_toolbar_info})
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
            case R.id.main_toolbar_me: {
                mainDrawerLayout.openDrawer(Gravity.START);
                break;
            }

            case R.id.main_toolbar_info: {
                break;
            }

        }
    }

    /**
     * 拦截返回键，如果Drawer是打开的，把它关回去
     */
    @Override
    public void onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(Gravity.START)) {
            mainDrawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }
}
