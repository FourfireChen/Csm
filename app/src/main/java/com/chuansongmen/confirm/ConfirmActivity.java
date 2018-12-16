package com.chuansongmen.confirm;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmActivity extends BaseActivity<ConfirmViewModel> {
    private static final String TAG = "ConfirmActivity";
    @BindView(R.id.confirm_warning_text)
    TextView confirmWarningText;
    @BindView(R.id.confirm_delay_remark)
    EditText confirmDelayRemark;
    @BindView(R.id.confirm_confirm_delay)
    Button confirm;
    Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_activity);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            order = data.getParcelable(getString(R.string.order));
        }
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        confirmWarningText.setText(R.string.confirm_warning);
    }

    private void initData() {
        viewModel.getChangeDelayResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null) {
                    // 成功
                    finish();
                    Log.i(TAG, "滞留成功");
                } else {
                    // 失败
                    Toast.makeText(ConfirmActivity.this, "滞留失败：" + s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.confirm_confirm_delay)
    public void onViewClicked() {
        viewModel.delay(order.getPagerId(), confirmDelayRemark.getText().toString());
    }
}
