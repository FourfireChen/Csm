package com.chuansongmen.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.main.MainActivity;
import com.chuansongmen.util.UIUtil;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chuansongmen.data.DataRepository.FAIL;
import static com.chuansongmen.data.DataRepository.SUCCESS;

public class LoginActivity extends BaseActivity<LoginViewModel> {
    @BindView(R.id.login_send_msg)
    Button msgSend;
    @BindView(R.id.login_confirm)
    Button confirm;
    @BindView(R.id.login_verify_code)
    EditText verifyCode;
    @BindView(R.id.login_phone)
    EditText phone;
    private static final String BAD_PHONE_TIP = "请填写正确的手机号码";
    private static final String BAD_VERIFY_CODE_TIP = "请填写4位数验证码";

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
            public void onChanged(String phoneNumber) {
                if (!phoneNumber.isEmpty()) {
                    viewModel.loginByCache(phoneNumber);
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
                            viewModel.clearVerifyEntry();
                        }
                    };
                    countDownTimer.start();
                } else {
                    toast("发送验证码失败，请检查网络");
                }
            }
        });

        viewModel.getIsLoginSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(SUCCESS)) {
                    toast(getString(R.string.login_success));
                    startActivity(MainActivity.class, null);
                    viewModel.cacheUserPhoneNumber(phone.getText().toString());
                    finish();
                } else if (!s.isEmpty()) {
                    toast(s);
                }
            }
        });
    }

    protected void initView() {
        UIUtil.setTypeface(getString(R.string.font), getAssets());
    }


    @OnClick({R.id.login_send_msg, R.id.login_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_send_msg:
                if (viewModel.checkPhoneNumberFormat(phone.getText().toString())) {
                    viewModel.sendVerifyCode(phone.getText().toString());
                } else {
                    toast(BAD_PHONE_TIP);
                }
                break;
            case R.id.login_confirm:
                if (!viewModel.checkPhoneNumberFormat(phone.getText().toString())) {
                    toast(BAD_PHONE_TIP);
                } else if (!viewModel.checkVerifyCodeFormat(verifyCode.getText().toString())) {
                    toast(BAD_VERIFY_CODE_TIP);
                } else {
                    viewModel.loginByVerify(phone.getText().toString(),
                            verifyCode.getText().toString());
                }
                break;
        }
    }
}
