package com.chuansongmen.rider.receipt;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

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
