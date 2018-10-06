package com.chuansongmen.worker.receipt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

public class ReceiptActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_receipt_activity);
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.receipt_container, new InputFragment());
        transaction.commit();
    }


}
