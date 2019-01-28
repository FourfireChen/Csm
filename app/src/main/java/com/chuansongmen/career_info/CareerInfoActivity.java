package com.chuansongmen.career_info;

import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;

import butterknife.BindView;

public class CareerInfoActivity extends BaseActivity<CareerInfoViewModel> {
    @BindView(R.id.careerinfo_name)
    TextView careerInfoName;
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
    protected void initBind() {
        super.initBind();
        viewModel.getCareerQRCode()
                .observe(this, bitmap -> careerinfoQrcode.setImageBitmap(bitmap));
        viewModel.getWorkerInfo().observe(this, worker -> {
            careerInfoName.setText(worker.getName());
            careerinfoCareer.setText(worker.getCategory().toString());
            careerinfoId.setText(worker.getId());
        });
        viewModel.init();
    }
}
