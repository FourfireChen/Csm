package com.chuansongmen.util;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanDelegate implements QRCodeView.Delegate {
    private ZBarView zBarView;
    private ScanCallback scanCallback;
    private boolean isLoop;

    public ScanDelegate(ZBarView zBarView, ScanCallback scanCallback, boolean isLoop) {
        this.zBarView = zBarView;
        this.scanCallback = scanCallback;
        this.isLoop = isLoop;
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        scanCallback.onSuccess(result);
        if (isLoop)
            zBarView.startSpot();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        scanCallback.onFail();
    }

    public interface ScanCallback {
        void onSuccess(String result);

        void onFail();
    }
}
