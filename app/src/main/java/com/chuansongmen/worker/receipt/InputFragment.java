package com.chuansongmen.worker.receipt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuansongmen.R;
import com.chuansongmen.base.BaseFragment;
import com.chuansongmen.util.Util;

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
    @BindView(R.id.receipt_money_title)
    TextView receiptMoneyTitle;
    @BindView(R.id.receipt_money_suggest)
    TextView receiptMoneySuggest;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.worker_receipt_input_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initTextTypeface();
        return view;
    }

    public void initTextTypeface() {
        Util.setTypeface("fonts/type.ttf",
                getContext().getAssets(),
                receiptTitle,
                receiptConfirm,
                receiptMoneyTitle,
                receiptMoneySuggest);
    }

    @OnClick(R.id.receipt_confirm)
    public void onViewClicked() {
        //todo:弹出收款二维码
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.receipt_container, new PayFragment())
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
