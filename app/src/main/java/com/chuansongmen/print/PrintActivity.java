package com.chuansongmen.print;

import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

import androidx.annotation.Nullable;

public class PrintActivity extends BaseActivity<PrintViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_activity);
    }
}
