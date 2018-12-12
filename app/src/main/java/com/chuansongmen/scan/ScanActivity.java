package com.chuansongmen.scan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.util.ScanDelegate;
import com.chuansongmen.view.SignView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanActivity extends BaseActivity<ScanViewModel>{
    @BindView(R.id.scan_zxingview)
    ZBarView scanScanView;
    @BindView(R.id.scan_edit)
    EditText scanEdit;
    @BindView(R.id.scan_confirm)
    Button scanConfirm;
    @BindView(R.id.scan_sign)
    SignView scanSign;
    @BindView(R.id.scan_sign_clear)
    Button scanSignClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        scanScanView.setDelegate(new ScanDelegate(scanScanView, new ScanDelegate.ScanCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(ScanActivity.this, "扫描成功" + result, Toast.LENGTH_SHORT).show();
                scanEdit.setText(result);
            }

            @Override
            public void onFail() {
                Toast.makeText(ScanActivity.this, "相机打开有问题，请检查相机权限", Toast.LENGTH_SHORT).show();
            }
        }, true));
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

    /**
     * 检查扫描到的单号是否符合格式
     *
     * @param code
     * @return 如果不符合，返回""空字符串，如果符合，照常返回单号
     */
    private String codeFormatCheck(String code) {
        return "";
    }



    @OnClick({R.id.scan_confirm, R.id.scan_sign_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scan_confirm:
                // TODO: 2018/11/18 这里应该调用单号格式检查
//                codeFormatCheck(scanEdit.getText().toString());
                break;
            case R.id.scan_sign_clear:
                scanSign.clear();
                break;
        }
    }
}
