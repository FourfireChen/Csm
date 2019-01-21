package com.chuansongmen.transfer;

import android.widget.Button;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseActivity;
import com.chuansongmen.data.bean.Order;
import com.chuansongmen.data.bean.Worker;
import com.chuansongmen.util.ScanDelegate;

import androidx.lifecycle.Observer;
import butterknife.BindView;
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
    protected int getContentLayoutId() {
        return R.layout.transfer_activity;
    }

    protected void initBind() {
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
