package com.chuansongmen.login;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuansongmen.main.MainActivity;
import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.common.ProgressListener;
import com.chuansongmen.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyActivity extends BaseActivity {
    @BindView(R.id.verify_title)
    TextView verifyTitle;
    @BindView(R.id.verify_send)
    Button verifySend;
    @BindView(R.id.verify_confirm)
    Button verifyConfirm;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.verify_phonenumber)
    EditText verifyPhonenumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_activity);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        Util.setTypeface("fonts/type.ttf", getAssets(), verifyTitle);
    }


    @OnClick({R.id.verify_send, R.id.verify_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_send:
                //todo:发送验证码
                break;
            case R.id.verify_confirm:
                //todo:验证登录
                //这儿在测试
                Util.showProgress(this, 2000, new ProgressListener() {
                    @Override
                    public void onStart() {
                        verifySend.setVisibility(View.INVISIBLE);
                        verifyConfirm.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        VerifyActivity.this.finish();
                        startActivity(MainActivity.class, null);
                    }
                });
                break;
        }
    }
}
