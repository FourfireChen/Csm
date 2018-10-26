package com.chuansongmen;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chuansongmen.view.SignView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.test_sign)
    SignView testSign;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.test_show)
    ImageView testShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.clear, R.id.show})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.clear:
                testSign.clear();
                break;
            case R.id.show:
                testShow.setImageBitmap(testSign.getBitmap());
                break;
        }
    }
}
