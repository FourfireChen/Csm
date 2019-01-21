package com.chuansongmen;

import com.chuansongmen.base.BaseActivity;

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
    protected int getContentLayoutId() {
        return R.layout.main_activity;
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
//        switch (view.getOrderId()) {
//            case R.id.main_toolbar_me:
//
//                break;
//            case R.id.main_toolbar_info:
//
//                break;
//        }
//    }
}
