package com.chuansongmen.career_info;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CareerInfoActivity extends BaseActivity<CareerInfoViewModel> {
    @BindView(R.id.careerinfo_name)
    TextView careerinfoName;
    @BindView(R.id.careerinfo_career)
    TextView careerinfoCareer;
    @BindView(R.id.careerinfo_entrytime)
    TextView careerinfoEntrytime;
    @BindView(R.id.careerinfo_rate)
    TextView careerinfoRate;
    @BindView(R.id.careerinfo_qrcode)
    ImageView careerinfoQrcode;
    @BindView(R.id.careerinfo_id)
    TextView careerinfoId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.careerinfo_activity);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        viewModel.getCareerQRCode().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                careerinfoQrcode.setImageBitmap(bitmap);
            }
        });
        viewModel.createQRCode();
    }
}
