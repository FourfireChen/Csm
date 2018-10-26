package com.chuansongmen.worker.receipt;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PayFragment extends BaseFragment {
    @BindView(R.id.pay_qrcode)
    ImageView payQrcode;
    @BindView(R.id.receipt_money_suggest)
    TextView receiptMoneySuggest;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.worker_receipt_pay_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void paySuccessfully(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
