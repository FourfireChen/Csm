package com.chuansongmen.receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.util.UIUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InputFragment extends BaseFragment {
    @BindView(R.id.receipt_title)
    TextView receiptTitle;
    @BindView(R.id.receipt_money)
    EditText receiptMoney;
    @BindView(R.id.receipt_confirm)
    Button receiptConfirm;
    Unbinder unbinder;
    @BindView(R.id.receipt_money_flag)
    TextView receiptMoneyFlag;
    @BindView(R.id.pay_qrcode)
    ImageView payQrcode;
    @BindView(R.id.receipt_money_show)
    TextView receiptMoneyShow;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receipt_input_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        UIUtil.setTypeface(getString(R.string.font),
                getContext().getAssets(),
                receiptTitle,
                receiptConfirm,
                receiptMoneyFlag,
                receiptMoneyShow);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.receipt_confirm)
    public void onViewClicked() {
        // TODO: 1/25/19 生成收款二维码，并监听是否收到付款
        receiptMoneyShow.setText(receiptMoney.getText());
    }
}
