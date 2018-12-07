package com.chuansongmen.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.service.PositionService;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainViewModel> implements IMainActivity {

    Unbinder unbinder;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.main_bottom_navigation)
    CardView mainBottomNavigation;
    private MainFragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        initData();
        initView();
        initService();
    }

    /**
     * 这里做必须对员工信息做初始化，不然会nullpoint，但之后这里要向服务器请求员工信息。
     */
    private void initData() {
        // TODO: 2018/12/4 向服务器请求员工信息
        Worker.WorkerBuilder.builder.setId("120").build();
    }

    @Override
    protected void initView() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, mainFragment)
                .commit();

    }

    private void initService() {
        Intent intent = new Intent(this, PositionService.class);
        startService(intent);
    }

    /**
     * 拦截返回键，如果Drawer是打开的，把它关回去
     */
    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(Gravity.START)) {
            mainDrawerLayout.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public DrawerLayout getDrawerLayout() {
        return mainDrawerLayout;
    }

    @Override
    public CardView getBottom() {
        return mainBottomNavigation;
    }
}
