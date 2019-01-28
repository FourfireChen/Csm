package com.chuansongmen.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.career_info.CareerInfoActivity;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.login.LoginActivity;
import com.chuansongmen.receipt.ReceiptActivity;
import com.chuansongmen.scan.ScanActivity;
import com.chuansongmen.sendget.SendGetFragment;
import com.chuansongmen.sendget.SendGetViewPageAdapter;
import com.chuansongmen.util.PermissionUtil;
import com.chuansongmen.util.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.Manifest.permission.CAMERA;
import static android.view.Gravity.START;
import static com.chuansongmen.common.Field.REQUEST_CAMERA;

public class MainFragment extends BaseFragment<MainViewModel> implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, IMainActivity.IMainView {
    private static final String[] TITLE = {"待收件", "已收件", "滞留件", "重点件", "待派件", "已派件", "滞留件", "重点件"};

    @BindView(R.id.main_toolbar_me)
    ImageView mainToolbarMe;
    @BindView(R.id.main_toolbar_title)
    TextView mainToolbarTitle;
    @BindView(R.id.main_toolbar_pay)
    ImageView mainToolbarPay;
    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.main_next_time)
    TextView mainNextTime;
    @BindView(R.id.next_time_layout)
    LinearLayout nextTimeLayout;
    @BindView(R.id.today_send_value)
    TextView todaySendValue;
    @BindView(R.id.today_get_layout)
    LinearLayout todayGetLayout;
    @BindView(R.id.today_get_value)
    TextView todayGetValue;
    @BindView(R.id.main_appbar_content)
    RelativeLayout mainAppbarContent;
    @BindView(R.id.main_fragment_appbar)
    AppBarLayout mainFragmentAppbar;
    @BindView(R.id.main_tabs)
    TabLayout mainTabs;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    private TextView mainBottomGet, mainBottomSend;
    private DrawerLayout mainDrawerLayout;
    private Switch workerStatus;

    private IMainActivity mainActivity;
    private Unbinder unbinder;

    private SendGetViewPageAdapter viewPageAdapter;
    private List<SendGetFragment> fragments = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_content, container, false);

        unbinder = ButterKnife.bind(this, view);

        initData();

        initView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.updateOrders();
    }

    private void initView() {
        for (int i = 0; i < 8; i++) {
            SendGetFragment fragment = new SendGetFragment();
            fragments.add(fragment);
        }
    }

    private void initData() {
        // TODO: 2018/12/4 这里要防止上班失败
        viewModel.getWorkerStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean != workerStatus.isChecked()) {
                    workerStatus.setChecked(aBoolean);
                }
            }
        });
        viewModel.getIsLogoutSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    startActivity(LoginActivity.class, null);
                    getActivity().finish();
                } else {
                    toast("注销账号失败");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (IMainActivity) getActivity();

        initActivityView();
    }

    /**
     * 初始化在Activity布局里面的控件，把UI控件全部移到碎片里面来处理
     */
    private void initActivityView() {
        mainDrawerLayout = mainActivity.getDrawerLayout();
        workerStatus = mainDrawerLayout.findViewById(R.id.main_drawer_workswitch);
        TextView workerSound = mainDrawerLayout.findViewById(R.id.main_drawer_careersound);
        TextView workerId = mainDrawerLayout.findViewById(R.id.main_drawer_id);
        Button logout = mainDrawerLayout.findViewById(R.id.main_logout);
        CardView mainBottom = mainActivity.getBottom();
        mainBottomGet = mainBottom.findViewById(R.id.main_bottom_left);
        mainBottomSend = mainBottom.findViewById(R.id.main_bottom_right);
        ImageView mainBottomCenter = mainBottom.findViewById(R.id.main_bottom_center);

        logout.setOnClickListener(this);
        workerStatus.setOnCheckedChangeListener(this);
        workerSound.setOnClickListener(this);
        workerId.setOnClickListener(this);

        mainBottomGet.setOnClickListener(this);
        mainBottomSend.setOnClickListener(this);
        mainBottomCenter.setOnClickListener(this);

        UIUtil.setTypeface(getString(R.string.font),
                getActivity().getAssets(),
                mainBottomGet,
                mainBottomSend,
                mainToolbarTitle,
                mainNextTime,
                todayGetValue,
                todaySendValue);

        mainFragmentAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float height = appBarLayout.getHeight() - mainToolbar.getHeight();
                float offset = Math.abs(i);
                mainAppbarContent.setAlpha(contentToTranslate(height, offset));
            }
        });

        initViewPager();
    }

    private void initViewPager() {
        viewPageAdapter = new SendGetViewPageAdapter(getActivity().getSupportFragmentManager());

        viewModel.getOrders().observe(this, new Observer<List<List<Order>>>() {
            @Override
            public void onChanged(List<List<Order>> lists) {
                if (!lists.isEmpty()) {
                    refreshList(lists);
                }
            }
        });

        mainViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            int lastPos;

            @Override
            public void onPageScrolled(int position,
                                       float positionOffset,
                                       int positionOffsetPixels) {
                if (lastPos <= 3 && position >= 4) {
                    mainToolbarTitle.setText("派 件");
                    mainBottomGet.setTextColor(getResources().getColor(R.color.textColor));
                    mainBottomSend.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else if (lastPos >= 4 && position <= 3) {
                    mainToolbarTitle.setText("收 件");
                    mainBottomSend.setTextColor(getResources().getColor(R.color.textColor));
                    mainBottomGet.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                lastPos = position;
            }
        });
        mainTabs.setupWithViewPager(mainViewpager);
        viewPageAdapter.setFragments(fragments);
        mainViewpager.setAdapter(viewPageAdapter);
        viewPageAdapter.setTitles(TITLE);
    }

    private float contentToTranslate(float height, float offset) {
        return 1 - offset / height;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.main_toolbar_me})
    public void onViewClicked() {
        mainDrawerLayout.openDrawer(START);
    }

    @OnClick({R.id.main_toolbar_pay})
    public void onPayClick() {
        startActivity(ReceiptActivity.class, null);
    }


    /**
     * 这里处理的是从Activity中获取的控件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_drawer_careersound:
                // TODO: 2018/12/17 职员之声
                break;
            case R.id.main_drawer_id:
                startActivity(CareerInfoActivity.class, null);
                break;
            case R.id.main_bottom_left:
                viewModel.updateOrders();
                mainViewpager.setCurrentItem(0, true);
                break;
            case R.id.main_bottom_right:
                viewModel.updateOrders();
                mainViewpager.setCurrentItem(4, true);
                break;
            case R.id.main_bottom_center:
                if (PermissionUtil.permissionsCheckAndRequest(this, REQUEST_CAMERA, CAMERA)) {
                    startActivity(ScanActivity.class, null);
                }
                break;
            case R.id.main_logout:
                viewModel.logout();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        viewModel.updateWorkerStatus(isChecked ? 1 : 0);
    }

    @Override
    public void changeWorkerStatus(boolean status) {
        workerStatus.setChecked(status);
    }

    @Override
    public void refreshList(List<List<Order>> data) {
        for (int i = 0; i < 8; i++) {
            fragments.get(i).refreshList(data.get(i));
        }
        viewPageAdapter.notifyDataSetChanged();
    }
}
