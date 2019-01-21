package com.chuansongmen.receipt;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

import androidx.fragment.app.FragmentTransaction;

public class ReceiptActivity extends BaseActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.receipt_activity;
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.receipt_container, new InputFragment());
        transaction.commit();
    }
}
