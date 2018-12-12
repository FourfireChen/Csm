package com.chuansongmen.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.main.MainActivity;
import com.chuansongmen.util.Util;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chuansongmen.data.DataRepository.FAIL;

public class VerifyActivity extends BaseActivity<VerifyViewModel> {
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
    private String rightVerifyCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_activity);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        viewModel.getSendMessageResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals(FAIL)) {
                    rightVerifyCode = s;
                    verifySend.setClickable(false);
                    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            verifySend.setText("剩余" + (millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            verifySend.setText("发送验证码");
                            verifySend.setClickable(true);
                        }
                    };
                    countDownTimer.start();
                } else {
                    toast("发送验证码失败，请检查网络");
                }
            }
        });
    }

    protected void initView() {
        Util.setTypeface("fonts/type.ttf", getAssets(), verifyTitle);
    }


    @OnClick({R.id.verify_send, R.id.verify_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_send:
                if (verifyPhonenumber.getText().toString().isEmpty()) {
                    toast("请填写手机号码");
                } else {
                    viewModel.sendVerifyCode(verifyPhonenumber.getText().toString());
                }
                break;
            case R.id.verify_confirm:
                if (rightVerifyCode.equals(verifyCode.getText().toString())) {
                    toast("登录成功");
                    startActivity(MainActivity.class, null);
                    finish();
                } else {
                    toast("验证码错误，请重新输入");
                }
                break;
        }
    }
}
