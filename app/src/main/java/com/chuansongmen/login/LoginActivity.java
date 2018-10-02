package com.chuansongmen.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_text1)
    TextView loginText1;
    @BindView(R.id.login_text2)
    TextView loginText2;
    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.bottom_confirm)
    Button bottomConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Util.setTypeface("fonts/type.ttf", getAssets(), loginText1, loginText2);
    }

    @OnClick(R.id.bottom_confirm)
    public void onViewClicked() {
        //todo:跳出验证码，登录
        startActivity(VerifyActivity.class, null);
        finish();
    }
}
