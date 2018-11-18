package com.chuansongmen.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.scan.ScanActivity;
import com.chuansongmen.sendget.SendGetActivity;
import com.chuansongmen.util.Util;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainViewModel> implements CompoundButton.OnCheckedChangeListener {
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
    Switch mainDrawerWorkSwitch;
    @BindView(R.id.main_drawer_careersound)
    LinearLayout mainDrawerCareersound;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.main_toolbar_me)
    ImageView mainToolbarMe;
    @BindView(R.id.main_toolbar_info)
    ImageView mainToolbarInfo;
    //这里是为了上班状态切换失败的时候，把按钮调回来
    private volatile boolean isSwitchError = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);

        setBottomNavigationTypeface();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.main_fragment_container, new RiderMainFragment())
                .commit();

        mainDrawerWorkSwitch.setOnCheckedChangeListener(this);
    }

    private void setBottomNavigationTypeface() {
        Util.setTypeface(getString(R.string.FONT),
                getAssets(),
                mainBottomLeft,
                mainBottomRight,
                mainTitle);
    }


    @SuppressLint("WrongConstant")
    @OnClick({R.id.main_bottom_left,
            R.id.main_bottom_center,
            R.id.main_bottom_right,
            R.id.main_toolbar_me,
            R.id.main_toolbar_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_bottom_left: {
                Bundle choice = new Bundle();
                choice.putString(getString(R.string.TYPE), getString(R.string.GET));
                startActivity(SendGetActivity.class, choice);
                break;
            }
            case R.id.main_bottom_center: {
                startActivity(ScanActivity.class, null);
                break;
            }
            case R.id.main_bottom_right: {
                Bundle choice = new Bundle();
                choice.putString(getString(R.string.TYPE), getString(R.string.SEND));
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
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        /**
         * 改变职工是否上班状态
         */
        // TODO: 2018/11/17 这个地方要考虑健壮性，如果按钮点击失败怎么办
        viewModel.changeWorkState(isChecked ? 1 : 0).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    Toast.makeText(MainActivity.this, "改变状态失败，请关闭并重试", Toast.LENGTH_SHORT).show();
                } else if (isChecked) {
                    Toast.makeText(MainActivity.this, "上班了", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "下班了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
