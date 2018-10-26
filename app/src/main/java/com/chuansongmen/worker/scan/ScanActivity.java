package com.chuansongmen.worker.scan;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.view.SignView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    @BindView(R.id.scan_zxingview)
    ZBarView scanScanView;
    @BindView(R.id.scan_edit)
    EditText scanEdit;
    @BindView(R.id.scan_confirm)
    Button scanConfirm;
    @BindView(R.id.scan_sign)
    SignView scanSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        ButterKnife.bind(this);
        scanScanView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scanScanView.showScanRect();
        scanScanView.startCamera();
        scanScanView.startSpot();
        scanScanView.setType(BarcodeType.HIGH_FREQUENCY, null);
    }

    @Override
    protected void onDestroy() {
        scanScanView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        scanScanView.stopCamera();
        super.onStop();
    }

    @OnClick(R.id.scan_confirm)
    public void onViewClicked() {
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, "扫描成功" + result, Toast.LENGTH_SHORT).show();
        scanScanView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
