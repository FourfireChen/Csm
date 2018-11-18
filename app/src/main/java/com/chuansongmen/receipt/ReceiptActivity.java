package com.chuansongmen.receipt;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

public class ReceiptActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_activity);
        initView();
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.receipt_container, new InputFragment());
        transaction.commit();
    }


}