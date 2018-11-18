package com.chuansongmen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.view.SignView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity<TestViewModel> {
    @BindView(R.id.test_sign)
    SignView testSign;
    @BindView(R.id.add)
    Button clear;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.test_show)
    ImageView testShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.add, R.id.show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                viewModel.addTestWorker();
                break;
            case R.id.show:
                testShow.setImageBitmap(testSign.getBitmap());
                break;
        }
    }
}
