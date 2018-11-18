package com.chuansongmen.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.service.PositionService;
import com.chuansongmen.util.Util;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainViewModel> {

    Unbinder unbinder;
    private MainFragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        testInitWorker();
        initView();
        initService();
    }

    /**
     * 这里做必须对员工信息做初始化，不然会nullpoint，但之后这里要向服务器请求员工信息。
     */
    private void testInitWorker() {
        Worker.WorkerBuilder.builder.setId(120).build();
    }

    @Override
    protected void initView() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, mainFragment)
                .addToBackStack(null)
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
        if (mainFragment.getMainDrawerLayout().isDrawerOpen(Gravity.START)) {
            mainFragment.getMainDrawerLayout().closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }


}
