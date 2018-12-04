package com.chuansongmen;

import android.os.Bundle;

import com.chuansongmen.base.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity<TestViewModel> {

//
//    @BindView(R.id.main_toolbar_me)
//    ImageView mainToolbarMe;
//    @BindView(R.id.main_toolbar_title)
//    TextView mainToolbarTitle;
//    @BindView(R.id.main_toolbar_info)
//    ImageView mainToolbarInfo;
//    @BindView(R.id.main_toolbar)
//    Toolbar mainToolbar;
//    @BindView(R.id.main_next_time)
//    TextView mainNextTime;
//    @BindView(R.id.next_time_layout)
//    LinearLayout nextTimeLayout;
//    @BindView(R.id.today_send_value)
//    TextView todaySendValue;
//    @BindView(R.id.today_get_layout)
//    LinearLayout todayGetLayout;
//    @BindView(R.id.today_get_value)
//    TextView todayGetValue;
//    @BindView(R.id.main_appbar_content)
//    RelativeLayout mainAppbarContent;
//    @BindView(R.id.main_fragment_appbar)
//    AppBarLayout mainFragmentAppbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
//        mainFragmentAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//                float height = appBarLayout.getHeight() - mainToolbar.getHeight();
//                float offset = Math.abs(i);
//                mainAppbarContent.setAlpha(contentToTranslate(height, offset));
//            }
//        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new TestFragment())
                .commit();
    }

    private float contentToTranslate(float height, float offset) {
        return 1 - offset / height;
    }

//    @OnClick({R.id.main_toolbar_me, R.id.main_toolbar_info})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.main_toolbar_me:
//
//                break;
//            case R.id.main_toolbar_info:
//
//                break;
//        }
//    }
}
