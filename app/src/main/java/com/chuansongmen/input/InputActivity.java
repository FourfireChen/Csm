package com.chuansongmen.input;

import android.os.Bundle;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

import androidx.annotation.Nullable;

public class InputActivity extends BaseActivity<InputViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);
    }
}
