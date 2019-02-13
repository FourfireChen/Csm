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
    TextView careerInfoCareer;
    @BindView(R.id.careerinfo_entrytime)
    TextView careerInfoEntrytime;
    @BindView(R.id.careerinfo_rate)
    TextView careerInfoRate;
    @BindView(R.id.careerinfo_qrcode)
    ImageView careerInfoQrcode;
    @BindView(R.id.careerinfo_id)
    TextView careerInfoId;

    @Override
    protected int getContentLayoutId() {
        return R.layout.careerinfo_activity;
    }

    @Override
    protected void initBind() {
        super.initBind();
        viewModel.getCareerQRCode()
                .observe(this, bitmap -> careerInfoQrcode.setImageBitmap(bitmap));
        viewModel.getWorkerInfo().observe(this, worker -> {
            careerInfoName.setText(worker.getName());
            careerInfoCareer.setText(worker.getCategory().toString());
            careerInfoId.setText(worker.getId());
        });
        viewModel.init();
    }
}
