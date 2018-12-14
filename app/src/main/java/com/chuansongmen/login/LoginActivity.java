package com.chuansongmen.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginActivity extends BaseActivity<LoginViewModel> {
    @BindView(R.id.login_send_msg)
    Button msgSend;
    @BindView(R.id.login_confirm)
    Button confirm;
    @BindView(R.id.login_verify_code)
    EditText verifyCode;
    @BindView(R.id.login_phone)
    EditText phone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hadCache();
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void hadCache() {
        viewModel.hadUserPhoneNumberCache().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String phonenumber) {
                if (!phonenumber.isEmpty()) {
                    // TODO: 2018/12/14 要考虑把手机号码，即员工ID存下来
                    startActivity(MainActivity.class, null);
                    finish();
                }
            }
        });
    }

    private void initData() {
        viewModel.getSendMessageResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals(FAIL)) {
                    msgSend.setClickable(false);
                    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            msgSend.setText("剩余" + (millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            msgSend.setText("发送验证码");
                            msgSend.setClickable(true);
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
        Util.setTypeface("fonts/type.ttf", getAssets());
    }


    @OnClick({R.id.login_send_msg, R.id.login_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_send_msg:
                if (phone.getText().toString().isEmpty()) {
                    toast("请填写手机号码");
                } else {
                    viewModel.sendVerifyCode(phone.getText().toString());
                }
                break;
            case R.id.login_confirm:
                if (viewModel.checkVerifyCode(phone.getText().toString(),
                        verifyCode.getText().toString())) {
                    toast("登录成功");
                    startActivity(MainActivity.class, null);
                    viewModel.cacheUserPhoneNumber(phone.getText().toString());
                    finish();
                } else {
                    toast("验证码错误，请重新输入");
                }
                break;
        }
    }
}
