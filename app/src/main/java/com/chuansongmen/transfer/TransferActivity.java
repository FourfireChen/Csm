package com.chuansongmen.transfer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.util.ScanDelegate;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class TransferActivity extends BaseActivity<TransferViewModel> {
    @BindView(R.id.transfer_scanview)
    ZBarView transferScanview;
    @BindView(R.id.transfer_name)
    TextView transferName;
    @BindView(R.id.transfer_id)
    TextView transferId;
    @BindView(R.id.transfer_confirm)
    Button transferConfirm;
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_activity);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        viewModel.getTargetWorker().observe(this, new Observer<Worker>() {
            @Override
            public void onChanged(Worker worker) {
                transferName.setText(worker.getName());
                transferId.setText(worker.getId());
            }
        });
        viewModel.getIsTransferSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    toast("移交成功");
                    finish();
                } else {
                    toast("移交失败");
                }
            }
        });
        order = getIntent().getExtras().getParcelable(getString(R.string.order));
    }

    @Override
    protected void initView() {
        super.initView();
        transferScanview.setDelegate(new ScanDelegate(transferScanview,
                new ScanDelegate.ScanCallback() {
                    @Override
                    public void onSuccess(String result) {
                        viewModel.queryWorker(result);
                    }

                    @Override
                    public void onFail() {
                        toast("ID错误，请确认");
                    }
                }, true));
    }

    @OnClick(R.id.transfer_confirm)
    public void onViewClicked() {
        viewModel.transferOrder(order.getPagerId(), transferId.getText().toString());
    }
}
