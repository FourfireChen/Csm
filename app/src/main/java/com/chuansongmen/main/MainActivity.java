package com.chuansongmen.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Gravity;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.position.PositionService;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainViewModel> implements IMainActivity {

    private Unbinder unbinder;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.main_bottom_navigation)
    CardView mainBottomNavigation;
    private MainFragment mainFragment = new MainFragment();


    @Override
    protected int getContentLayoutId() {
        return R.layout.main_activity;
    }

    /**
     * 这里做必须对员工信息做初始化，不然会nullpoint，但之后这里要向服务器请求员工信息。
     */
    protected void initData() {

    }

    @Override
    protected void initView() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, mainFragment)
                .commit();
    }

    protected void initService() {
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
