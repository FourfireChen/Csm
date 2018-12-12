package com.chuansongmen.input;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.DataRepository;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.util.ScanDelegate;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class InputActivity extends BaseActivity<InputViewModel> {
    @BindView(R.id.input_scan)
    ZBarView inputScan;
    @BindView(R.id.input_order_status)
    TextView inputOrderStatus;
    @BindView(R.id.input_order_pager_id)
    TextView inputOrderPagerId;
    @BindView(R.id.input_confirm)
    Button inputConfirm;

    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        inputScan.setDelegate(new ScanDelegate(inputScan, new ScanDelegate.ScanCallback() {
            @Override
            public void onSuccess(String result) {
                inputOrderPagerId.setText(result);
                viewModel.checkPageIdLegality(result);
            }

            @Override
            public void onFail() {
                Toast.makeText(InputActivity.this, "相机打开有问题，请检查相机权限", Toast.LENGTH_SHORT).show();
            }
        }, true));
    }

    private void initData() {
        order = getIntent().getExtras().getParcelable(getString(R.string.order));

        viewModel.getInputMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(DataRepository.SUCCESS)) {
                    Toast.makeText(InputActivity.this, "录入成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(InputActivity.this, s, Toast.LENGTH_SHORT).show();
                    inputScan.startSpot();
                }
            }
        });
        viewModel.getPageIdLegality().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                inputOrderStatus.setText(s);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        inputScan.showScanRect();
        inputScan.startCamera();
        inputScan.startSpot();
        inputScan.setType(BarcodeType.HIGH_FREQUENCY, null);
    }

    @Override
    protected void onDestroy() {
        inputScan.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        inputScan.stopCamera();
        super.onStop();
    }

    @OnClick(R.id.input_confirm)
    public void onViewClicked() {
        if (inputOrderStatus.getText().toString().equals("可使用")) {
            viewModel.input(order.getOrderId(), inputOrderPagerId.getText().toString());
        } else {
            toast("单号不可使用");
        }
    }
}
