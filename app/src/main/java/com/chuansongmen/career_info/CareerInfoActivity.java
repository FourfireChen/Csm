package com.chuansongmen.career_info;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

import androidx.lifecycle.Observer;
import butterknife.BindView;

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
    protected int getContentLayoutId() {
        return R.layout.careerinfo_activity;
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
